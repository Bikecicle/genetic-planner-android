package planner.internal.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import planner.internal.item.Agenda;
import planner.internal.item.Item;
import planner.internal.item.Note;
import planner.internal.item.Schedule;

/**
 * Created by Griffin Page on 3/14/2018
 * griffinpage9@gmail.com
 */

public class RoomDBAndroid implements DataManager {

    private static final String TAG = "RoomDataManager";

    private PlannerDatabase plannerDatabase;
    private AgendaDAO agendaDAO;
    private ScheduleDAO scheduleDAO;

    public RoomDBAndroid(Context context, boolean inMemory) {
        if (!inMemory)
            plannerDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    PlannerDatabase.class, "planner_database").allowMainThreadQueries().build();
        else
            plannerDatabase = Room.inMemoryDatabaseBuilder(context, PlannerDatabase.class).build();
        agendaDAO = plannerDatabase.agendaDAO();
        scheduleDAO = plannerDatabase.scheduleDAO();
    }

    public void close() {
        plannerDatabase.close();
    }

    @Override
    public void load() {
    }

    @Override
    public void save() {
    }

    @Override
    public void clean() {
        agendaDAO.clear();
        scheduleDAO.clear();
        Log.d(TAG, "Tables cleared");
    }

    @Override
    public Agenda getAgenda() {
        Agenda agenda = new Agenda();
        for (ItemEntity entity : agendaDAO.loadAll()) {
            agenda.add(Item.fromEntity(entity));
        }
        Log.d(TAG, "Agenda loaded: " + agenda);
        return agenda;
    }

    @Override
    public void cleanAgenda() {
        // TODO: Wipe all note relations
    }

    @Override
    public void addItem(Item item) {
        agendaDAO.insertItem(Item.toEntity(item));
        Log.d(TAG, "Item inserted: " + item);
    }

    @Override
    public void removeItem(Item item) {
        agendaDAO.deleteItem(Item.toEntity(item));
        Log.d(TAG, "Item removed: " + item);
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule = new Schedule();
        for (NoteEntity entity : scheduleDAO.loadAll()) {
            schedule.add(linkParent(entity));
        }
        Log.d(TAG, "Schedule loaded: " + schedule);
        return schedule;
    }

    @Override
    public void setSchedule(Schedule schedule) {
        List<NoteEntity> entities = new ArrayList<>();
        for (Note note : schedule) {
            entities.add(Note.toEntity(note));
        }
        scheduleDAO.clear();
        scheduleDAO.insertNotes(entities);
        Log.d(TAG, "Schedule set as: " + schedule);
    }

    @Override
    public Note getFirst() {
        Note note = linkParent(scheduleDAO.loadFirst());
        Log.d(TAG, "First note loaded: " + note);
        return note;
    }

    @Override
    public List<Note> getDay(Calendar target) {
        List<Note> notes = new ArrayList<>();
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(0);
        day.set(Calendar.YEAR, target.get(Calendar.YEAR));
        day.set(Calendar.MONTH, target.get(Calendar.MONTH));
        day.set(Calendar.DAY_OF_MONTH, target.get(Calendar.DAY_OF_MONTH));
        Log.d(TAG, "Loading notes for the day of " + day.getTime());
        long tMin = day.getTimeInMillis();
        target.add(Calendar.DAY_OF_MONTH, 1);
        long tMax = day.getTimeInMillis();
        for (NoteEntity entity : scheduleDAO.loadRange(tMin, tMax)) {
            notes.add(linkParent(entity));
        }
        Log.d(TAG, "Notes loaded: " + notes);
        return notes;
    }

    @Override
    public List<Note> getWeek(Calendar target) {
        List<Note> notes = new ArrayList<>();
        Calendar week = Calendar.getInstance();
        week.setTimeInMillis(0);
        week.set(Calendar.YEAR, target.get(Calendar.YEAR));
        week.set(Calendar.WEEK_OF_YEAR, target.get(Calendar.WEEK_OF_YEAR));
        Log.d(TAG, "Loading notes for the week of " + week.getTime());
        long tMin = week.getTimeInMillis();
        week.add(Calendar.WEEK_OF_YEAR, 1);
        long tMax = week.getTimeInMillis();
        for (NoteEntity entity : scheduleDAO.loadRange(tMin, tMax)) {
            notes.add(linkParent(entity));
        }

        return notes;
    }

    @Override
    public List<Note> getMonth(Calendar target) {
        List<Note> notes = new ArrayList<>();
        Calendar month = Calendar.getInstance();
        month.setTimeInMillis(0);
        month.set(Calendar.YEAR, target.get(Calendar.YEAR));
        month.set(Calendar.MONTH, target.get(Calendar.MONTH));
        Log.d(TAG, "Loading notes for the month of " + month.getTime());
        long tMin = month.getTimeInMillis();
        month.add(Calendar.MONTH, 1);
        long tMax = month.getTimeInMillis();
        for (NoteEntity entity : scheduleDAO.loadRange(tMin, tMax)) {
            notes.add(linkParent(entity));
        }
        return notes;

    }

    @Override
    public List<Note> getYear(Calendar target) {
        List<Note> notes = new ArrayList<>();
        Calendar year = Calendar.getInstance();
        year.setTimeInMillis(0);
        year.set(Calendar.YEAR, target.get(Calendar.YEAR));
        Log.d(TAG, "Loading notes for the year of " + year.getTime());
        long tMin = year.getTimeInMillis();
        year.add(Calendar.YEAR, 1);
        long tMax = year.getTimeInMillis();
        for (NoteEntity entity : scheduleDAO.loadRange(tMin, tMax)) {
            notes.add(linkParent(entity));
        }
        return notes;
    }

    @Override
    public List<Note> getAll() {
        List<Note> notes = new ArrayList<>();
        for (NoteEntity entity: scheduleDAO.loadAll()) {
           notes.add(linkParent(entity));
        }
        Log.d(TAG, "All notes loaded: " + notes);
        return notes;
    }

    @Override
    public void removeNote(Note note) {
        scheduleDAO.deleteNote(Note.toEntity(note));
    }

    @Override
    public Note getNoteById(int id) {
        return linkParent(scheduleDAO.loadNote(id));
    }

    private Note linkParent(NoteEntity entity) {
        Item parent = Item.fromEntity(agendaDAO.loadItem(entity.parentId));
        Note note = Note.fromEntity(entity, parent);
        assert parent != null;
        parent.notes.add(note);
        return note;
    }
}
