package planner.internal.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import planner.internal.item.Agenda;
import planner.internal.item.Schedule;

public class FileManager {
	
	public final String agendaFile = "data/agenda";
	public final String scheduleFile = "data/schedule";
	public final String genomeFile = "data/scheduleGenome";
	
	public boolean saveAgenda(Agenda agenda) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(agendaFile));
			out.writeObject(agenda);
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Agenda loadAgenda() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(agendaFile));
			Agenda agenda = (Agenda) in.readObject();
			in.close();
			return agenda;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean saveSchedule(Schedule schedule) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(scheduleFile));
			out.writeObject(schedule);
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Schedule loadSchedule() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(scheduleFile));
			Schedule schedule = (Schedule) in.readObject();
			in.close();
			return schedule;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean saveScheduleGenome(ScheduleGenome scheduleGenome) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(genomeFile));
			out.writeObject(scheduleGenome);
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public ScheduleGenome loadScheduleGenome() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(genomeFile));
			ScheduleGenome scheduleGenome = (ScheduleGenome) in.readObject();
			in.close();
			return scheduleGenome;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
