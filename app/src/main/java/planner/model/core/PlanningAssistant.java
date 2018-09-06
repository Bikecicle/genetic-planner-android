package planner.model.core;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import evolution.core.EvolutionManager;
import evolution.core.Population;
import planner.model.data.DataManager;
import planner.model.item.Item;
import planner.model.item.Note;

public class PlanningAssistant {

    private final String TAG = "Planning";
    private final boolean loggingEnabled = false;

    PlannerDatabase database;

    public PlanningAssistant(Context context, boolean inMemory) {
        if (!inMemory)
            database = Room.databaseBuilder(context.getApplicationContext(),
                    PlannerDatabase.class, "planner_database").allowMainThreadQueries().build();
        else
            database = Room.inMemoryDatabaseBuilder(context, PlannerDatabase.class).build();
    }

    public void planSchedule() {
        agenda.clean();
        Population initialPopulation = new Population();
        for (int i = 0; i < C.POPULATION_SIZE; i++) {
            initialPopulation.add(new ScheduleGenome(agenda, C.MUTATION_RATE));
        }
        EvolutionManager evolutionManager = new EvolutionManager(initialPopulation, C.SELECTOR, loggingEnabled);
        evolutionManager.runGenerations(C.GENERATION_COUNT);
        if (loggingEnabled)
            evolutionManager.saveLog(C.logFile);
        Log.d("TAG", "Schedule evolved: " + evolutionManager.getResult());
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

    public List<Note> getYear(Calendar year) { return dataManager.getYear(year); }

    public List<Note> getAll() {
        return dataManager.getAll();
    }

    public void addItem(Item item) {
        dataManager.addItem(item);
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

    DataManager getDataManager() {
        return dataManager;
    }
}
