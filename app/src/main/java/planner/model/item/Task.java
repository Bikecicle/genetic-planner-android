package planner.model.item;

import android.arch.persistence.room.Entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import planner.model.core.C;

@Entity(tableName = "tasks")
public class Task implements Item, Comparable<Task> {

	private static final long serialVersionUID = 2422355433614515809L;

	public int itemId;
	public ItemType type;
	public String title;
	public String details;

	public long deadline;
	public long duration;
	public long complete;
	public long planned;

	public Task(int itemId, String title, String details, long deadline, long duration) {
		this.deadline = deadline;
		this.duration = duration;
		this.complete = 0;
		this.planned = 0;
	}

	@Override
	public ArrayList<Note> generateNotes(long[] genome) {
		ArrayList<Note> newNotes = new ArrayList<>();
		int noteId = (int) (Math.random() * Integer.MAX_VALUE);
		for (int i = 0; i < genome.length; i++) {
			long tabDuration = 0;
			if (getRemaining() > C.HOUR) {
				tabDuration = C.HOUR;
				planned += C.HOUR;
			} else {
				tabDuration = getRemaining();
				planned += getRemaining();
			}
			newNotes.add(new Note(noteId, title, details, genome[i], tabDuration, itemId));
		}
		return newNotes;
	}

	@Override
	public void complete(Note note) {
		planned -= note.duration;
		complete += note.duration;
	}

	@Override
	public void clean() {
		planned = 0;
		// TODO
	}

	@Override
	public int compareTo(Task other) {
		return (int) Math.signum(deadline - other.deadline);
	}

	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat(C.DATE_TIME_FORMAT);
		return "[" + itemId + "] " + f.format(getDeadlineDate().getTime()) + ": " + title + " - " + (duration / C.MINUTE) + " min - "
				+ details;
	}

	public Calendar getDeadlineDate() {
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(deadline);
		return cal;
	}

	public long getRemaining() {
		return duration - complete - planned;
	}
}
