package planner.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Griffin on 3/14/2018.
 */

@Entity(tableName = "agenda")
public class ItemEntity {

    @PrimaryKey
    public int itemId;
    public String type;
    public String title;
    public String details;

    // Event fields
    public long start;
    public long duration;

    // Task fields
    public long deadline;

    public ItemEntity() {}
}
