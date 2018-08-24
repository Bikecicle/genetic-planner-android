package planner.model.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.List;

import planner.model.item.Agenda;
import planner.model.item.Item;
import planner.model.item.Note;
import planner.model.item.Schedule;

public class FileSystem implements DataManager {
	
	private String agendaFile = "data/agenda";

	protected Agenda agenda;
	protected Schedule schedule;

	@Override
	public void load() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(agendaFile));
			agenda = (Agenda) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		this.schedule = agenda.extractSchedule();
	}

	@Override
	public void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(agendaFile));
			out.writeObject(agenda);
			out.close();
		} catch (IOException e) {
		    e.printStackTrace();
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
	public List<Note> getDay(Calendar target) {
		return schedule.getDay(target);
	}

	@Override
	public List<Note> getWeek(Calendar target) {
		return schedule.getWeek(target);
	}

	@Override
	public List<Note> getMonth(Calendar target) {
		return schedule.getMonth(target);
	}

	@Override
    public List<Note> getYear(Calendar target) { return schedule.getYear(target); }

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
