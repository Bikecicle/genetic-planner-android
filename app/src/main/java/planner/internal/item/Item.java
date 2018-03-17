package planner.internal.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Serializable {
	
	private static final long serialVersionUID = -7943195019888260373L;

	public ItemType type;
	public String title;
	public String details;

	public ArrayList<Note> notes;

	public Item(ItemType type, String title, String details) {
		this.type = type;
		this.title = title;
		this.details = details;
		this.notes = new ArrayList<Note>();
	}
	
	public abstract ArrayList<Note> generateNotes(long[] gene);
	
	public abstract void complete(Note note);
	
	public void clean() {
		notes.clear();
	}

	public int getId() { return hashCode(); }
}
