package planner.internal.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Schedule implements Iterable<Note>, Serializable {

	private static final long serialVersionUID = -3734613331222933635L;

	private Node head;
	private int size;

	public Schedule() {
		// Head is a dummy node
		head = new Node(null, null);
		size = 0;
	}

	public boolean add(Note newNote) {
		// Check if schedule is empty
		if (head.next == null) {
			head.next = new Node(newNote, null);
			size++;
			return true;
		}
		// Check if new note is the earliest on the schedule
		if (head.next.note.compareTo(newNote) > 0) {
			head.next.note.compareTo(newNote);
			head.next = new Node(newNote, head.next);
			size++;
			return true;
		}
		Node temp = head.next;
		// Move forward to proper time or the end of the schedule
		while (temp.next != null && temp.next.note.compareTo(newNote) < 0)
			temp = temp.next;
		// Check if the note overlaps with the previous
		if (temp.note.getEnd() > newNote.getStart())
			return false;
		// Add to end
		if (temp.next == null) {
			temp.next = new Node(newNote, null);
			size++;
			return true;
		}
		// Check if the new note overlaps with the next one
		if (newNote.getEnd() > temp.next.note.getStart())
			return false;
		// Add the new note at the proper time
		temp.next = new Node(newNote, temp.next);
		size++;
		return true;
	}
	
	public int addAll(List<Note> newNotes) {
		int added = 0;
		for (Note newNote : newNotes) {
			if (add(newNote))
				added++;
		}
		return added;
	}
	
	public List<Note> getDay(Calendar day) {
		List<Note> notes = new ArrayList<Note>();
		for (Note note : this) {
			Calendar start = note.getStartDate();
			if (day.get(Calendar.YEAR) == start.get(Calendar.YEAR)
					&& day.get(Calendar.MONTH) == start.get(Calendar.MONTH)
					&& day.get(Calendar.WEEK_OF_MONTH) == start.get(Calendar.WEEK_OF_MONTH)
					&& day.get(Calendar.DAY_OF_WEEK) == start.get(Calendar.DAY_OF_WEEK)) {
				notes.add(note);
			} else if (start.after(day)) {
				break;
			}
		}
		return notes;
	}

	public List<Note> getWeek(Calendar week) {
		List<Note> notes = new ArrayList<Note>();
		for (Note note : this) {
			Calendar start = note.getStartDate();
			if (week.get(Calendar.YEAR) == start.get(Calendar.YEAR)
					&& week.get(Calendar.WEEK_OF_YEAR) == start.get(Calendar.WEEK_OF_YEAR)) {
				notes.add(note);
			} else if (start.after(week)) {
				break;
			}
		}
		return notes;
	}

	public List<Note> getMonth(Calendar month) {
		List<Note> notes = new ArrayList<Note>();
		for (Note note : this) {
			Calendar start = note.getStartDate();
			if (month.get(Calendar.YEAR) == start.get(Calendar.YEAR)
					&& month.get(Calendar.MONTH) == start.get(Calendar.MONTH)) {
				notes.add(note);
			} else if (start.after(month)) {
				break;
			}
		}
		return notes;
	}
	
	public List<Note> getYear(Calendar year) {
		List<Note> notes = new ArrayList<Note>();
		for (Note note : this) {
			Calendar start = note.getStartDate();
			if (year.get(Calendar.YEAR) == start.get(Calendar.YEAR)) {
				notes.add(note);
			} else if (start.after(year)) {
				break;
			}
		}
		return notes;
	}
	
	public List<Note> getAll() {
		List<Note> notes = new ArrayList<Note>();
		for (Note note : this) {
			notes.add(note);
		}
		return notes;
	}
	
	public boolean remove(Note note) {
		Node temp = head;
		while (temp.next != null) {
			if (temp.next.note == note) {
				temp.next = temp.next.next;
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	
	public Note removeFirst() {
		if (!isEmpty()) { 
			Note note = head.next.note;
			head.next = head.next.next;
			return note;
		}
		return null;
	}

	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return head.next == null;
	}


	@Override
	public Iterator<Note> iterator() {
		return new Iterator<Note>() {
			
			public Node current = head;
			
			@Override
			public boolean hasNext() {
				return current.next != null;
			}

			@Override
			public Note next() {
				if (!hasNext())
					return null;
				current = current.next;
				return current.note;
			}
		};
	}
	
	private class Node implements Serializable{

		private static final long serialVersionUID = 4757061294462156453L;
		public Note note;
		public Node next;

		public Node(Note note, Node next) {
			this.next = next;
			this.note = note;
		}

	}
	
	@Override
	public String toString() {
		String str = "";
		for (Note note : this) {
			str += note + "\n";
		}
		return str;
	}

	public Note getById(int id) {
		for (Note note : this) {
			if (note.hashCode() == id)
				return note;
		}
		return null;
	}
	
	
}
