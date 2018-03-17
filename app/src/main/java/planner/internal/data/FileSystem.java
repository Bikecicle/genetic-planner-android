package planner.internal.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.List;

import planner.internal.item.Agenda;
import planner.internal.item.Item;
import planner.internal.item.Note;
import planner.internal.item.Schedule;

public class FileSystem implements DataManager {
	
	public final String agendaFile = "data/agenda";

	protected Agenda agenda;
	protected Schedule schedule;

	@Override
	public boolean load() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(agendaFile));
			agenda = (Agenda) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		this.schedule = agenda.extractSchedule();
		return true;
	}

	@Override
	public boolean save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(agendaFile));
			out.writeObject(agenda);
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void clean() {
		agenda = new Agenda();
		schedule = new Schedule();
	}

	@Override
	public Agenda getAgenda() {
		return agenda;
	}

	@Override
	public void cleanAgenda() {
		agenda.clean();
	}

	@Override
	public void addItem(Item item) {
		agenda.add(item);
	}

	@Override
	public void removeItem(Item item) {
		agenda.remove(item);
	}

	@Override
	public Schedule getSchedule() {
		return schedule;
	}

	@Override
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@Override
	public Note getFirst() {
		return null;
	}

	@Override
	public List<Note> getDay(Calendar day) {
		return schedule.getDay(day);
	}

	@Override
	public List<Note> getWeek(Calendar week) {
		return schedule.getWeek(week);
	}

	@Override
	public List<Note> getMonth(Calendar month) {
		return schedule.getMonth(month);
	}

	@Override
	public List<Note> getAll() {
		return schedule.getAll();
	}

	@Override
	public void removeNote(Note note) {
		schedule.remove(note);
	}

	@Override
	public Note getNoteById(int id) {
		return schedule.getById(id);
	}
}
