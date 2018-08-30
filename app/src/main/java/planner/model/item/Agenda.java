package planner.model.item;

import android.arch.persistence.room.Dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

@Dao
public interface Agenda {

	private static final long serialVersionUID = 8095770957990266696L;

	public ArrayList<Event> events;
	public ArrayList<Task> tasks;

	public Agenda() {
		events = new ArrayList<>();
		tasks = new ArrayList<>();
	}

	public void add(Item item) {
		if (item.type == ItemType.event) {
			events.add((Event) item);
			Collections.sort(events);
		} else if (item.type == ItemType.task) {
			tasks.add((Task) item);
			Collections.sort(tasks);
		}
	}

	public void remove(Item item) {
		events.remove(item);
		tasks.remove(item);
	}

	public void clean() {
		for (Event event : events) {
			event.clean();
		}
		for (Task task : tasks) {
			task.clean();
		}
	}

	public Schedule extractSchedule() {
		Schedule schedule = new Schedule();
		for (Event event : events) {
			schedule.addAll(event.notes);
		}
		return schedule;
	}

	public String toString() {
		String s = "";
		s += "Events:\n";
		if (!events.isEmpty()) {
			for (Event event : events) {
				s += event.toString() + "\n";
			}
		} else {
			s += "none\n";
		}
		s += "\nTasks:\n";
		if (!tasks.isEmpty()) {
			for (Task task : tasks) {
				s += task.toString() + "\n";
			}
		} else {
			s += "none\n";
		}
		return s;
	}

	public boolean hasItem(Item item) {
		if (item.type == ItemType.event) {
			for (Event event : events) {
				if (event.itemId == item.itemId) {
					return true;
				}
			}
		}
		else if (item.type == ItemType.task) {
			for (Task task : tasks) {
				if (task.itemId == item.itemId) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return events.isEmpty() && tasks.isEmpty();
	}

    public int size() {
		return events.size() + tasks.size();
    }
}
