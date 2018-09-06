package planner.model.item;

import android.arch.persistence.room.Entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import planner.model.core.C;
import planner.model.recurrence.Recurrence;

@Entity
public class Event implements Item, Comparable<Event> {

	public static final long serialVersionUID = 2185845103699193125L;

	public int itemId;
	public ItemType type;
	public String title;
	public String details;

	public long start;
	public long duration;
	public boolean complete;

	public Event(int itemId, String title, String details, long start, long duration) {
		this.start = start;
		this.duration = duration;
		complete = false;
	}

	@Override
	public ArrayList<Note> generateNotes(long[] gene) {
		ArrayList<Note> newNotes = new ArrayList<Note>();
        int noteId = (int) (Math.random() * Integer.MAX_VALUE);
		if (!complete) {
			newNotes.add(new Note(noteId, title, details, start, duration, itemId));
		}
		return newNotes;
	}

	@Override
	public void complete(Note note) {
		complete = true;
	}

	@Override
	public void clean() {
		// TODO
	}

	@Override
	public int compareTo(Event other) {
		return (int) Math.signum(start - other.start);
	}

	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat(C.DATE_TIME_FORMAT);
		String str = "[" + itemId + "] " +  f.format(getStartDate().getTime()) + " - " + f.format(getEndDate().getTime()) + ": " + title;
		if (details != null && !details.equals(""))
			str += " - " + details;
		return str;
	}

	public Calendar getStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(start);
		return cal;
	}

	public Calendar getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(start + duration);
		return cal;
	}
}
