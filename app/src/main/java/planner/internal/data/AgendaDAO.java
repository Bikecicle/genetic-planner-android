package planner.internal.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import planner.internal.item.Event;
import planner.internal.item.Task;

/**
 * Created by Griffin Page on 3/14/2018
 * griffinpage9@gmail.com
 */

@Dao
public interface AgendaDAO {

    @Insert
    void insertItem(ItemEntity item);

    @Query("SELECT * FROM agenda WHERE itemId = :itemId")
    ItemEntity loadItem(int itemId);

    @Query("SELECT * FROM agenda")
    ItemEntity[] loadAll();

    @Delete
    void deleteItem(ItemEntity item);

    @Query("DELETE FROM agenda")
    void clear();
}
