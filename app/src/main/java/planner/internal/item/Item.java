package planner.internal.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import planner.internal.data.EventEntity;
import planner.internal.data.ItemEntity;
import planner.internal.data.TaskEntity;

public abstract class Item implements Serializable {
	
	private static final long serialVersionUID = -7943195019888260373L;

	public int id;
	public ItemType type;
	public String title;
	public String details;

	public ArrayList<Note> notes;

	public Item(ItemType type, String title, String details) {
		this.type = type;
		this.title = title;
		this.details = details;
		id = hashCode();
		this.notes = new ArrayList<Note>();
	}
	
	public abstract ArrayList<Note> generateNotes(long[] gene);
	
	public abstract void complete(Note note);
	
	public void clean() {
		notes.clear();
	}

	public static Item fromEntity(ItemEntity entity) {
		if (entity.type == ItemType.event.name) {
			return new Event(entity.title, entity.details,((EventEntity) entity).start,
					((EventEntity) entity).duration);
		} else if (entity.type == ItemType.task.name) {
			return new Task(entity.title, entity.details, ((TaskEntity) entity).deadline,
					((TaskEntity) entity).duration);
		}
		return null;
	}

	public static ItemEntity toEntity(Item item) {
		ItemEntity entity;
		if (item.type == ItemType.event) {
			entity = new EventEntity();
			((EventEntity) entity).start = ((Event) item).start;
			((EventEntity) entity).duration = ((Event) item).duration;
		} else if (item.type == ItemType.task) {
			entity = new TaskEntity();
			((TaskEntity) entity).deadline = ((Task) item).deadline;
			((TaskEntity) entity).duration = ((Task) item).duration;
		} else {
			return null;
		}
		entity.type = item.type.name;
		entity.id = item.id;
		entity.title = item.title;
		entity.details = item.details;
		return entity;
	}
}
