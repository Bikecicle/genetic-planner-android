package planner.internal.item;

import java.io.Serializable;
import java.util.ArrayList;

import planner.internal.data.ItemEntity;

public abstract class Item implements Serializable {
	
	private static final long serialVersionUID = -7943195019888260373L;

	int itemId;
	public ItemType type;
	public String title;
	public String details;

	public ArrayList<Note> notes;

	public Item(ItemType type, String title, String details) {
		this.type = type;
		this.title = title;
		this.details = details;
		itemId = hashCode();
		this.notes = new ArrayList<>();
	}
	
	public abstract ArrayList<Note> generateNotes(long[] gene);
	
	public abstract void complete(Note note);
	
	public void clean() {
		notes.clear();
	}

	public static Item fromEntity(ItemEntity entity) {
		if (entity.type.equals(ItemType.event.name)) {
			return new Event(entity.title, entity.details, entity.start, entity.duration);
		} else if (entity.type.equals(ItemType.task.name)) {
			return new Task(entity.title, entity.details, entity.deadline, entity.duration);
		}
		return null;
	}

	public static ItemEntity toEntity(Item item) {
		ItemEntity entity = new ItemEntity();
		if (item.type == ItemType.event) {
			entity.start = ((Event) item).start;
			entity.duration = ((Event) item).duration;
		} else if (item.type == ItemType.task) {
			entity.deadline = ((Task) item).deadline;
			entity.duration = ((Task) item).duration;
		} else {
			return null;
		}
		entity.type = item.type.name;
		entity.itemId = item.itemId;
		entity.title = item.title;
		entity.details = item.details;
		return entity;
	}
}
