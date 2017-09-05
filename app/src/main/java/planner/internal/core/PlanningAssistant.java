package planner.internal.core;

import java.util.Calendar;
import java.util.List;
import evolution.core.EvolutionManager;
import evolution.core.Population;
import planner.internal.data.DataManager;
import planner.internal.data.FileSystem;
import planner.internal.item.Agenda;
import planner.internal.item.Event;
import planner.internal.item.Schedule;
import planner.internal.item.Tab;
import planner.internal.item.Task;

public class PlanningAssistant {

	private static PlanningAssistant planningAssistant;

	private DataManager dataManager;
	private Agenda agenda;
	private ScheduleGenome currentGenome;
	private Schedule schedule;

	public PlanningAssistant() {
		dataManager = new FileSystem();
		agenda = dataManager.loadAgenda();
		if (agenda != null) {
			currentGenome = dataManager.loadScheduleGenome();
			if (currentGenome != null) {
				currentGenome.setAgenda(agenda);
				schedule = currentGenome.generateSchedule();
			} else {
				schedule = new Schedule();
			}

		} else {
			agenda = new Agenda();
			schedule = new Schedule();
		}
	}

	public void planSchedule() {
		agenda.clean();
		Population initialPopulation = new Population();
		for (int i = 0; i < C.POPULATION_SIZE; i++) {
			initialPopulation.add(new ScheduleGenome(agenda, C.MUTATION_RATE));
		}
		EvolutionManager evolutionManager = new EvolutionManager(initialPopulation, C.SELECTOR, true);
		evolutionManager.runGenerations(C.GENERATION_COUNT);
		evolutionManager.saveLog(C.logFile);
		currentGenome = (ScheduleGenome) evolutionManager.getResult();
		schedule = currentGenome.generateSchedule();
	}

	public void save() {
		agenda.clean();
		dataManager.saveAgenda(agenda);
		dataManager.saveScheduleGenome(currentGenome);
	}

	public Tab getFirst() {
		return schedule.iterator().next();
	}

	public List<Tab> getDay(Calendar day) {
		return schedule.getDay(day);
	}

	public List<Tab> getWeek(Calendar week) {
		return schedule.getWeek(week);
	}

	public List<Tab> getMonth(Calendar month) {
		return schedule.getMonth(month);
	}

	public void addEvent(Event newEvent) {
		agenda.addEvent(newEvent);
	}

	public void addTask(Task newTask) {
		agenda.addTask(newTask);
	}

	public void removeTab(Tab tab) {
		schedule.remove(tab);
	}

	public void clean() {
		agenda = new Agenda();
		schedule = new Schedule();
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public static PlanningAssistant getInstance() {
		if (planningAssistant == null)
			planningAssistant = new PlanningAssistant();
		return planningAssistant;
	}
}
