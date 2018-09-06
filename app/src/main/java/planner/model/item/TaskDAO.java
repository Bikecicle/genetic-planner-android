package planner.model.item;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

/**
 * Created by Griffin Page on 9/6/2018
 * griffinpage9@gmail.com
 */

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM tasks")
    List<Task> loadAll();

}
