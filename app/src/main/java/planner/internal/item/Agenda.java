package planner.internal.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Agenda implements Serializable {

	private static final long serialVersionUID = 8095770957990266696L;
	// These lists will remain sorted chronologically (not that it matters)
	public List<Event> events;
	public List<Task> tasks;

	public Agenda() {
		events = new ArrayList<Event>();
		tasks = new ArrayList<Task>();
	}

	public void addEvent(Event event) {
		events.add(event);
		events.sort(null);
	}

	public void addTask(Task task) {
		tasks.add(task);
		tasks.sort(null);
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

	public boolean isEmpty() {
		return events.isEmpty() && tasks.isEmpty();
	}
}
