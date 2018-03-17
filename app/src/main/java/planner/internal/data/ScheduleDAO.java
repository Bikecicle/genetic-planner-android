package planner.internal.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Griffin on 3/13/2018.
 */

@Dao
public interface ScheduleDAO {

    @Insert
    void insertNote(NoteEntity note);

    @Insert
    void insertNotes(List<NoteEntity> notes);

    @Query("")
    NoteEntity loadNote(int id);

    @Query("")
    NoteEntity loadFirst();

    @Query("SELECT * FROM schedule WHERE start BETWEEN :tMin AND :tMax")
    NoteEntity[] loadRange(long tMin, long tMax);

    @Query("SELECT * FROM schedule")
    NoteEntity[] loadAll();

    @Delete
    void deleteNote(int id);

    @Delete
    void clear();
}
