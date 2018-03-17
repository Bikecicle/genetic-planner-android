package planner.internal.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import planner.internal.item.Agenda;
import planner.internal.item.Item;
import planner.internal.item.Note;
import planner.internal.item.Schedule;

/**
 * Created by Griffin on 3/14/2018.
 */

public class RoomDBAndroid implements DataManager {

    private AgendaDAO agendaDAO;
    private ScheduleDAO scheduleDAO;

    @Override
    public boolean load() {
        return false;
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public void clean() {
        agendaDAO.clear();
        scheduleDAO.clear();
    }

    @Override
    public Agenda getAgenda() {
        Agenda agenda = new Agenda();
        for (ItemEntity entity : agendaDAO.loadAll()) {
            agenda.add(entity.toItem());
        }
        return agenda;
    }

    @Override
    public void cleanAgenda() {
        agendaDAO.clear();
    }

    @Override
    public void addItem(Item item) {
        agendaDAO.insertItem(new ItemEntity(item));
    }

    @Override
    public void removeItem(Item item) {
        agendaDAO.deleteItem(item.getId());
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule = new Schedule();
        for (NoteEntity entity : scheduleDAO.loadAll()) {
            schedule.add(entity.toNote());
        }
        return schedule;
    }

    @Override
    public void setSchedule(Schedule schedule) {
        List<NoteEntity> entities = new ArrayList<>();
        for (Note note : schedule.getAll()) {
            entities.add(new NoteEntity(note));
        }
        scheduleDAO.clear();
        scheduleDAO.insertNotes(entities);
    }

    @Override
    public Note getFirst() {
        return scheduleDAO.loadFirst().toNote();
    }

    @Override
    public List<Note> getDay(Calendar day) {
        List<Note> notes = new ArrayList<>();
        long tMin = 0;
        long tMax = 0;
        for (NoteEntity entity : scheduleDAO.loadRange(tMin, tMax)) {
            notes.add(entity.toNote());
        }
        return notes;
    }

    @Override
    public List<Note> getWeek(Calendar week) {
        return null;

    }

    @Override
    public List<Note> getMonth(Calendar month) {
        return null;

    }

    @Override
    public List<Note> getAll() {
        return null;
    }

    @Override
    public void removeNote(Note note) {
        scheduleDAO.deleteNote(note.getId());
    }

    @Override
    public Note getNoteById(int id) {
        return scheduleDAO.loadNote(id).toNote();
    }
}
