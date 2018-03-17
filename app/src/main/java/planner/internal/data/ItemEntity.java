package planner.internal.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import planner.internal.item.Item;

/**
 * Created by Griffin on 3/14/2018.
 */

@Entity
public class ItemEntity {

    @PrimaryKey
    int id;

    public ItemEntity(Item item) {

    }

    public Item toItem() {
        return null;
    }
}
