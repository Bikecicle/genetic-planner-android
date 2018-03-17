package planner.internal.item;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import planner.internal.core.C;

public class Note implements Comparable<Note>, Serializable {

	private static final long serialVersionUID = 5710812068584817767L;

	public String title;
	public String details;
	public long start;
	public long duration;

	public Item parent;

	public Note(String title, String details, long start, long duration, Item parent) {
		this.title = title;
		this.details = details;
		this.start = start;
		this.duration = duration;
		this.parent = parent;
	}

	public Note(long start, long duration) {
		title = null;
		details = null;
		this.start = start;
		this.duration = duration;
		parent = null;
	}

	public void complete() {
		parent.complete(this);
	}

	@Override
	public int compareTo(Note other) {
		return (int) Math.signum(this.start - other.start);
	}

	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat(C.DATE_TIME_FORMAT);
		String str = f.format(getStartDate().getTime()) + " - " + f.format(getEndDate().getTime()) + ": " + title;
		if (details != null && !details.equals(""))
			str += " - " + details;
		str += " [" + getId() + "]";
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

	public long getEnd() {
		return start + duration;
	}

	public int getId() {
		return hashCode();
	}
}
