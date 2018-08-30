package planner.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import planner.model.item.Item;
import planner.model.item.Note;

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

    public static Note fromEntity(NoteEntity entity) {
        return new Note(entity.noteId, entity.title, entity.details, entity.start, entity.duration,
                null);
    }

    public static  NoteEntity toEntity(Note note) {
        NoteEntity entity = new NoteEntity();
        entity.noteId = note.noteId;
        entity.title = note.title;
        entity.details = note.details;
        entity.start = note.start;
        entity.duration = note.duration;
        entity.parentId = note.parent.itemId;
        return entity;
    }
}
