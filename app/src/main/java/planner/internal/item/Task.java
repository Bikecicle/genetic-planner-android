package planner.internal.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ArrayList;

import planner.internal.core.C;

public class Task extends Item implements Comparable<Task> {

	private static final long serialVersionUID = 2422355433614515809L;
	private long deadline;
	private long duration;
	private long complete;
	private long planned;

	public Task(String title, String details, long deadline, long duration) {
		super(ItemType.task, title, details);
		this.deadline = deadline;
		this.duration = duration;
		this.complete = 0;
		this.planned = 0;
	}

	public Task(String title, String details, String deadlineDate, int minutes) {
		super(ItemType.task, title, details);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat(C.DATE_TIME_FORMAT).parse(deadlineDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deadline = c.getTimeInMillis();
		duration = minutes * C.MINUTE;
		complete = 0;
		planned = 0;
	}

	@Override
	public ArrayList<Note> generateNotes(long[] genome) {
		ArrayList<Note> newNotes = new ArrayList<Note>();
		for (int i = 0; i < genome.length; i++) {
			long tabDuration = 0;
			if (getRemaining() > C.HOUR) {
				tabDuration = C.HOUR;
				planned += C.HOUR;
			} else {
				tabDuration = getRemaining();
				planned += getRemaining();
			}
			newNotes.add(new Note(title, details, genome[i], tabDuration, this));
		}
		notes.addAll(newNotes);
		return newNotes;
	}

	@Override
	public void complete(Note note) {
		planned -= note.getDuration();
		complete += note.getDuration();
		notes.remove(note);
	}

	@Override
	public void clean() {
		planned = 0;
		super.clean();
	}

	@Override
	public int compareTo(Task other) {
		return (int) Math.signum(deadline - other.deadline);
	}

	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat(C.DATE_TIME_FORMAT);
		return f.format(getDeadlineDate().getTime()) + ": " + title + " - " + (duration / C.MINUTE) + " min - "
				+ details;
	}

	public Calendar getDeadlineDate() {
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(deadline);
		return cal;
	}

	public long getRemaining() {
		return duration - complete - planned;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getComplete() {
		return complete;
	}

	public void setComplete(long complete) {
		this.complete = complete;
	}
}
