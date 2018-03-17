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

    public PlanningAssistant(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public static PlanningAssistant getInstance(DataManager dataManager) {
        if (planningAssistant == null)
            planningAssistant = new PlanningAssistant(dataManager);
        return planningAssistant;
    }

    public void load() {
        dataManager.load();
    }

    public void save() {
        dataManager.save();
    }

    public void planSchedule() {
        dataManager.cleanAgenda();
        Population initialPopulation = new Population();
        for (int i = 0; i < C.POPULATION_SIZE; i++) {
            initialPopulation.add(new ScheduleGenome(dataManager.getAgenda(), C.MUTATION_RATE));
        }
        EvolutionManager evolutionManager = new EvolutionManager(initialPopulation, C.SELECTOR, true);
        evolutionManager.runGenerations(C.GENERATION_COUNT);
        evolutionManager.saveLog(C.logFile);
        dataManager.setSchedule(((ScheduleGenome) evolutionManager.getResult()).generateSchedule());
    }

    public Note getFirst() {
        return dataManager.getFirst();
    }

    public List<Note> getDay(Calendar day) {
        return dataManager.getDay(day);
    }

    public List<Note> getWeek(Calendar week) {
        return dataManager.getWeek(week);
    }

    public List<Note> getMonth(Calendar month) {
        return dataManager.getMonth(month);
    }

    public List<Note> getAll() {
        return dataManager.getAll();
    }

    public void addEvent(Event newEvent) {
        dataManager.addEvent(newEvent);
    }

    public void addTask(Task newTask) {
        dataManager.addTask(newTask);
    }

    public void removeNote(Note note) {
        dataManager.removeNote(note);
    }

    public void clean() {
        dataManager.clean();
    }

    public Agenda getAgenda() {
        return dataManager.getAgenda();
    }

    public Schedule getSchedule() {
        return dataManager.getSchedule();
    }

    public Note getNoteById(int id) {
        return dataManager.getNoteById(id);
    }

    public void deleteItem(Item parent) {
        parent.clean();
        dataManager.removeItem(parent);
    }
}
