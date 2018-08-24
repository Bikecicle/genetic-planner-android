package planner.model.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
