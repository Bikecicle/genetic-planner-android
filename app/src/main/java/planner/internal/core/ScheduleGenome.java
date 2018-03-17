package planner.internal.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import evolution.core.Genome;
import planner.internal.item.Agenda;
import planner.internal.item.Event;
import planner.internal.item.Schedule;
import planner.internal.item.Note;
import planner.internal.item.Task;

public class ScheduleGenome implements Genome {

	private static final long serialVersionUID = 1452338816637835089L;

	// A 2d array of longs where x is the task index and y is the session index
	// The value represents the start time of that task session in milliseconds
	private long[][] taskGenome;
	private double mutationRate;
	private double score;
	private Agenda agenda;

	// Constructor for initial population
	public ScheduleGenome(Agenda agenda, double mutationRate) {
		this.agenda = agenda;
		this.mutationRate = mutationRate;
		taskGenome = new long[agenda.tasks.size()][];

		Task task = null;
		for (int i = 0; i < agenda.tasks.size(); i++) {
			task = agenda.tasks.get(i);
			taskGenome[i] = new long[(int) (task.getRemaining() / C.HOUR)];
		}
		randomize();
		simulate();
	}

	// Constructor for breeding
	public ScheduleGenome(Agenda agenda, long[][] taskGenome, double mutationRate) {
		this.agenda = agenda;
		this.taskGenome = taskGenome;
		this.mutationRate = mutationRate;
		simulate();
	}

	private void randomize() {
		for (int i = 0; i < taskGenome.length; i++) {
			Task task = agenda.tasks.get(i);
			for (int j = 0; j < taskGenome[i].length; j++) {
				taskGenome[i][j] = randomStart(task.getDeadline() - task.getDuration());
			}
		}
	}

	private void simulate() {
		Schedule schedule = new Schedule();

		// Add in events
		for (Event event : agenda.events) {
			schedule.addAll(event.generateNotes(null));
		}

		// Conflicts
		int pass = 0;
		for (int i = 0; i < taskGenome.length; i++) {
			// Higher score for more successful additions
			pass += schedule.addAll(agenda.tasks.get(i).generateNotes(taskGenome[i]));
		}
		score += pass * C.CONFLICT_CF;

		// Initiative
		for (int i = 0; i < taskGenome.length; i++) {
			long deadline = agenda.tasks.get(i).getDeadline();
			for (int j = 0; j < taskGenome[i].length; j++)
				// Higher score the further from the deadline something is
				// scheduled
				score += (deadline - taskGenome[i][j]) * C.INITIATIVE_CF;
		}

		// Separation
		long prevStart = 0;
		for (Note note : schedule) {
			// Lower score the closer together things are scheduled (within a
			// day)
			long delta = note.getStart() - prevStart;
			if (delta < C.DAY)
				score -= (C.DAY - delta) * C.SEPARATION_CF;
		}

		// This means everything sucks... let's not break the normalizer
		if (score < 0)
			score = 0;

		// Clean up after simulation
		agenda.clean();
	}

	private long randomStart(long bound) {
		// Generate a random start time between now and the deadline
		long now = Calendar.getInstance().getTimeInMillis();
		if (bound < now)
			bound = now + C.DAY;
		return ((long) (Math.random() * (bound - now)) + now) / C.RESOLUTION * C.RESOLUTION;
	}

	private long[][] splice(long[][] a, long[][] b) {
		double p = 0.5; // Probability of inheriting from a
		long[][] c = new long[a.length][];
		for (int i = 0; i < a.length; i++) {
			c[i] = new long[a[i].length];
			for (int j = 0; j < a[i].length; j++) {
				if (Math.random() < p)
					c[i][j] = a[i][j];
				else
					c[i][j] = b[i][j];
			}
		}
		return c;
	}

	private void mutate(long[][] genes) {
		for (int i = 0; i < genes.length; i++) {
			for (int j = 0; j < genes[i].length; j++) {
				double roll = Math.random();
				if (roll < mutationRate) {
					genes[i][j] = randomStart(agenda.tasks.get(i).getDeadline() - agenda.tasks.get(i).getDuration());
				}
			}
		}
	}

	public Schedule generateSchedule() {
		Schedule schedule = new Schedule();
		for (Event event : agenda.events) {
			schedule.addAll(event.generateNotes(null));
		}
		for (int i = 0; i < taskGenome.length; i++) {
			schedule.addAll(agenda.tasks.get(i).generateNotes(taskGenome[i]));
		}
		return schedule;
	}
	
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	@Override
	public Genome breed(Genome other) {
		long[][] taskGenome = splice(this.taskGenome, ((ScheduleGenome) other).taskGenome);
		mutate(taskGenome);
		return new ScheduleGenome(agenda, taskGenome, mutationRate);
	}

	@Override
	public double getScore() {
		return score;
	}

	@Override
	public int getId() {
		return this.hashCode();
	}

	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat(C.DATE_TIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		String str = "Score: " + score + "\n";
		for (int i = 0; i < taskGenome.length; i++) {
			str += agenda.tasks.get(i).toString() + "\n";
			for (int j = 0; j < taskGenome[i].length; j++) {
				cal.setTimeInMillis(taskGenome[i][j]);
				str += "  - " + f.format(cal.getTime()) + "\n";
			}
		}
		return str;
	}

}
