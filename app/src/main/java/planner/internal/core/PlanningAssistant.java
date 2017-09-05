package planner.internal.core;

import java.util.Calendar;
import java.util.List;
import evolution.core.EvolutionManager;
import evolution.core.Population;
import planner.internal.file.FileManager;
import planner.internal.file.WinFileSys;
import planner.internal.item.Agenda;
import planner.internal.item.Event;
import planner.internal.item.Item;
import planner.internal.item.Schedule;
import planner.internal.item.Tab;
import planner.internal.item.Task;

public class PlanningAssistant {

	private FileManager fileManager;
	private Agenda agenda;
	private ScheduleGenome currentGenome;
	private Schedule schedule;

	public PlanningAssistant(FileManager fileManager) {
		this.fileManager = fileManager;
		agenda = fileManager.loadAgenda();
		if (agenda != null) {
			currentGenome = fileManager.loadScheduleGenome();
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
		fileManager.saveAgenda(agenda);
		fileManager.saveScheduleGenome(currentGenome);
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

	public List<Tab> getAll() {
		return schedule.getAll();
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

	public Tab getById(int id) {
		return schedule.getById(id);
	}

	public void deleteItem(Item parent) {
		parent.clean();
		agenda.remove(parent);
	}
}
