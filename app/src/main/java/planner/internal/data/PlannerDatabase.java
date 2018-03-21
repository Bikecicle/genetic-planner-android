package planner.internal.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Griffin Page on 3/21/2018
 * griffinpage9@gmail.com
 */

@Database(entities = {ItemEntity.class, NoteEntity.class}, version = 1)
abstract class PlannerDatabase extends RoomDatabase{
    abstract AgendaDAO agendaDAO();
    abstract ScheduleDAO scheduleDAO();
}
