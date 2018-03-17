package planner.internal.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import planner.internal.item.Item;
import planner.internal.item.Note;

/**
 * Created by Griffin on 3/14/2018.
 */

@Entity
public class NoteEntity {

    @PrimaryKey
    int id;
    String title;
    String details;
    long start;
    long duration;
    int parentId;

    public NoteEntity(Note note) {
        this.id = note.getId();
        this.title = note.title;
    }

    public Note toNote(Item parent) {
        return new Note(title, details, start, duration, parent);
    }

    public Note toNote() {
        return toNote(null);
    }
}
