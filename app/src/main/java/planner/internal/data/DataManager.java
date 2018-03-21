package planner.internal.data;

import java.util.Calendar;
import java.util.List;

import planner.internal.core.ScheduleGenome;
import planner.internal.item.Agenda;
import planner.internal.item.Event;
import planner.internal.item.Item;
import planner.internal.item.Note;
import planner.internal.item.Schedule;
import planner.internal.item.Task;

/**
 * Created by Griffin on 8/11/2017.
 */

public interface DataManager {

    boolean load();

    boolean save();

    void clean();

    Agenda getAgenda();

    void cleanAgenda();

    void addItem(Item item);

    void removeItem(Item item);

    Schedule getSchedule();

    void setSchedule(Schedule schedule);

    Note getFirst();

    List<Note> getDay(Calendar target);

    List<Note> getWeek(Calendar target);

    List<Note> getMonth(Calendar target);

    List<Note> getAll();

    void removeNote(Note note);

    Note getNoteById(int id);
}
