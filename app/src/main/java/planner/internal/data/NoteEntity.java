package planner.internal.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import planner.internal.item.Item;
import planner.internal.item.Note;

/**
 * Created by Griffin on 3/14/2018.
 */

@Entity(tableName = "schedule")
public class NoteEntity {

    @PrimaryKey
    public int id;
    public String title;
    public String details;
    public long start;
    public long duration;
    public int parentId;

    public NoteEntity() {}
}
