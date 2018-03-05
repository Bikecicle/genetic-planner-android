package planner.internal.item;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import planner.internal.core.C;

public class Note implements Comparable<Note>, Serializable {

	private static final long serialVersionUID = 5710812068584817767L;
	private String title;
	private String details;
	private long start;
	private long duration;
	private Item parent;

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
		return (int) Math.signum(this.start - other.getStart());
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

	public String getTitle() {
		return title;
	}

	public String getDetails() {
		return details;
	}

	public long getStart() {
		return start;
	}

	public long getDuration() {
		return duration;
	}

	public long getEnd() {
		return start + duration;
	}

	public Item getParent() {
		return parent;
	}
	
	public int getId() {
		return hashCode();
	}
}
