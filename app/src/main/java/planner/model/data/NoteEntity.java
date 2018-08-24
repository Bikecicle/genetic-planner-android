package planner.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Griffin Page on 3/14/2018
 * griffinpage9@gmail.com
 */

@Entity(tableName = "schedule",
        indices = @Index("parentId"),
        foreignKeys = @ForeignKey(entity = ItemEntity.class,
                parentColumns = "itemId",
                childColumns = "parentId"))
public class NoteEntity {

    @PrimaryKey
    public int noteId;
    public String title;
    public String details;
    public long start;
    public long duration;
    public int parentId;

    public NoteEntity() {
    }
}
