package planner.internal.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import planner.internal.item.Event;
import planner.internal.item.Task;

/**
 * Created by Griffin on 3/14/2018.
 */

@Dao
public interface AgendaDAO {

    @Insert
    void insertItem(ItemEntity item);

    @Query("SELECT * FROM agenda WHERE id = :id")
    ItemEntity loadItem(int id);

    @Query("SELECT * FROM agenda")
    ItemEntity[] loadAll();

    @Delete
    void deleteItem(int id);

    @Delete
    void clear();
}
