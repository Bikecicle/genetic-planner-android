package planner.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import planner.model.item.Event;
import planner.model.item.Item;
import planner.model.item.ItemType;
import planner.model.item.Task;

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


    public static Item fromEntity(ItemEntity entity) {
        if (entity.type.equals(ItemType.event.name)) {
            return new Event(entity.itemId, entity.title, entity.details, entity.start, entity.duration);
        } else if (entity.type.equals(ItemType.task.name)) {
            return new Task(entity.itemId, entity.title, entity.details, entity.deadline, entity.duration);
        }
        return null;
    }

    public static ItemEntity toEntity(Item item) {
        ItemEntity entity = new ItemEntity();
        if (item.type == ItemType.event) {
            entity.start = ((Event) item).start;
            entity.duration = ((Event) item).duration;
        } else if (item.type == ItemType.task) {
            entity.deadline = ((Task) item).deadline;
            entity.duration = ((Task) item).duration;
        } else {
            return null;
        }
        entity.type = item.type.name;
        entity.itemId = item.itemId;
        entity.title = item.title;
        entity.details = item.details;
        return entity;
    }
}
