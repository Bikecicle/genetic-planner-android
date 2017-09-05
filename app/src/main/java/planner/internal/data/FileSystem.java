package planner.internal.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import planner.internal.core.ScheduleGenome;
import planner.internal.item.Agenda;
import planner.internal.item.Schedule;

public class FileSystem implements DataManager{
	
	public final String agendaFile = "data/agenda";
	public final String genomeFile = "data/scheduleGenome";

	@Override
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

	@Override
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

	@Override
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

	@Override
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
