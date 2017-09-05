package planner.internal.data;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import planner.internal.core.ScheduleGenome;
import planner.internal.item.Agenda;

/**
 * Created by Griffin on 8/11/2017.
 */

public class FileSystemAndroid implements DataManager {

    public final String agendaFile = "agenda";
    public final String genomeFile = "scheduleGenome";

    @Override
    public boolean saveAgenda(Agenda agenda) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(openFileOutput(agendaFile, Context.MODE_PRIVATE));
            out.writeObject(agenda);
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Agenda loadAgenda() {
        return null;
    }

    @Override
    public boolean saveScheduleGenome(ScheduleGenome scheduleGenome) {
        return false;
    }

    @Override
    public ScheduleGenome loadScheduleGenome() {
        return null;
    }
}
