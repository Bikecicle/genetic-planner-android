package planner.internal.item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import planner.internal.core.C;
import planner.internal.recurrence.Recurrence;

public class Event extends Item implements Comparable<Event> {

	private static final long serialVersionUID = 2185845103699193125L;
	private long start;
	private long end; // For recurring events
	private long duration;
	private boolean complete;
	private boolean recurring;
	private Recurrence recurrence;

	public Event(String title, String details, long start, long duration) {
		super(title, details);
		this.start = start;
		this.duration = duration;
		complete = false;
		recurring = false;
		recurrence = null;
	}

	public Event(String title, String details, long start, long duration, Recurrence recurrence, long end) {
		super(title, details);
		this.start = start;
		this.duration = duration;
		if (recurrence != null) {
			this.recurrence = recurrence;
			this.end = end;
			recurring = true;
		}
	}

	@Override
	public List<Note> generateTabs(long[] gene) {
		ArrayList<Note> newNotes = new ArrayList<Note>();
		if (!complete) {
			if (!recurring) {
				newNotes.add(new Note(title, details, start, duration, this));
			} else {
				List<Long> instances = recurrence.getInstances(start, end);
				for (long instance : instances) {
					newNotes.add(new Note(title, details, instance, duration, this));
				}
			}
			notes.addAll(newNotes);
		}
		return newNotes;
	}

	@Override
	public void complete(Note note) {
		if (recurring) {
			List<Long> instances = recurrence.getInstances(start, end);
			if (instances.size() == 1) {
				complete = true;
			} else {
				start = instances.get(1).longValue();
			}
		} else {
			complete = true;
		}
		notes.remove(note);
	}

	@Override
	public int compareTo(Event other) {
		return (int) Math.signum(start - other.start);
	}

	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat(C.DATE_TIME_FORMAT);
		String str = f.format(getStartDate().getTime()) + " - " + f.format(getEndDate().getTime()) + ": " + title;
		if (details != null && !details.equals(""))
			str += " - " + details;
		if (recurring)
			str += " - " + recurrence + " until " + f.format(getRecurrenceEnd().getTime());
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
	
	public Calendar getRecurrenceEnd() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(end);
		return cal;
	}
}
