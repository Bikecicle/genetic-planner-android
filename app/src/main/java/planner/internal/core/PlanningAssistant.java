package planner.internal.core;

import java.util.Calendar;
import java.util.List;

import evolution.core.EvolutionManager;
import evolution.core.Population;
import planner.internal.data.DataManager;
import planner.internal.item.Agenda;
import planner.internal.item.Event;
import planner.internal.item.Item;
import planner.internal.item.Note;
import planner.internal.item.Schedule;
import planner.internal.item.Task;

public class PlanningAssistant {

    private static PlanningAssistant planningAssistant;

    private DataManager dataManager;
    private Agenda agenda;
    private ScheduleGenome currentGenome;
    private Schedule schedule;

    public PlanningAssistant() {
        agenda = new Agenda();
        schedule = new Schedule();
    }

    public static PlanningAssistant getInstance() {
        if (planningAssistant == null)
            planningAssistant = new PlanningAssistant();
        return planningAssistant;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void load() {
        if (dataManager != null) {
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
    }

    public void save() {
        if (dataManager != null) {
            agenda.clean();
            dataManager.saveAgenda(agenda);
            dataManager.saveScheduleGenome(currentGenome);
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

    public Note getFirst() {
        return schedule.iterator().next();
    }

    public List<Note> getDay(Calendar day) {
        return schedule.getDay(day);
    }

    public List<Note> getWeek(Calendar week) {
        return schedule.getWeek(week);
    }

    public List<Note> getMonth(Calendar month) {
        return schedule.getMonth(month);
    }

    public List<Note> getAll() {
        return schedule.getAll();
    }

    public void addEvent(Event newEvent) {
        agenda.addEvent(newEvent);
    }

    public void addTask(Task newTask) {
        agenda.addTask(newTask);
    }

    public void removeTab(Note note) {
        schedule.remove(note);
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

    public Note getById(int id) {
        return schedule.getById(id);
    }

    public void deleteItem(Item parent) {
        parent.clean();
        agenda.remove(parent);
    }
}
