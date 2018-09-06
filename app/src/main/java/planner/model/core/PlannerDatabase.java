package planner.model.core;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import planner.model.item.EventDAO;
import planner.model.item.Item;
import planner.model.item.Note;
import planner.model.item.NoteDAO;
import planner.model.item.TaskDAO;

/**
 * Created by Griffin Page on 8/31/2018
 * griffinpage9@gmail.com
 */

@Database(entities = {Item.class, Note.class}, version = 1, exportSchema = false)
public abstract class PlannerDatabase extends RoomDatabase{
    public abstract EventDAO eventDAO();
    public abstract TaskDAO taskDAO();
    public abstract NoteDAO noteDAO();
}
