package planner.internal.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import planner.internal.item.Item;
import planner.internal.item.ItemType;

/**
 * Created by Griffin on 3/14/2018.
 */

@Entity(tableName = "agenda")
public class ItemEntity {

    @PrimaryKey
    public int id;
    public String type;
    public String title;
    public String details;
    public int[] children;

    public ItemEntity() {}
}
