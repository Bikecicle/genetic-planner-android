package planner.internal.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Serializable {
	
	private static final long serialVersionUID = -7943195019888260373L;
	protected String title;
	protected String details;

	protected List<Note> notes;

	public Item(String title, String details) {
		this.title = title;
		this.details = details;
		this.notes = new ArrayList<Note>();
	}
	
	public abstract List<Note> generateTabs(long[] gene);
	
	public abstract void complete(Note note);

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	public void clean() {
		notes.clear();
	}

}
