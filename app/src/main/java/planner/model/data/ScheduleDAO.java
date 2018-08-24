package planner.model.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Griffin Page on 3/13/2018
 * griffinpage9@gmail.com
 */

@Dao
public interface ScheduleDAO {

    @Insert
    void insertNote(NoteEntity note);

    @Insert
    void insertNotes(List<NoteEntity> notes);

    @Query("SELECT * FROM schedule WHERE noteId = :noteId")
    NoteEntity loadNote(int noteId);

    @Query("SELECT * FROM schedule WHERE start = (SELECT MIN(start) FROM schedule)")
    NoteEntity loadFirst();

    @Query("SELECT * FROM schedule WHERE start BETWEEN :tMin AND :tMax")
    NoteEntity[] loadRange(long tMin, long tMax);

    @Query("SELECT * FROM schedule")
    NoteEntity[] loadAll();

    @Delete
    void deleteNote(NoteEntity note);

    @Query("DELETE FROM schedule")
    void clear();
}
