package planner.internal.data;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import planner.internal.core.ScheduleGenome;
import planner.internal.item.Agenda;

public class FileSystemAndroid implements DataManager {

    private final String agendaFile = "agenda";
    private final String genomeFile = "scheduleGenome";

    private Context context;

    public FileSystemAndroid(Context context) {
        this.context = context;
    }

    @Override
    public boolean saveAgenda(Agenda agenda) {
        if (isExternalStorageWritable()) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getExternalFile(agendaFile)));
                out.writeObject(agenda);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Agenda loadAgenda() {
        if (isExternalStorageReadable()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(getExternalFile(agendaFile)));
                return (Agenda) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean saveScheduleGenome(ScheduleGenome scheduleGenome) {
        if (isExternalStorageWritable()) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getExternalFile(genomeFile)));
                out.writeObject(scheduleGenome);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public ScheduleGenome loadScheduleGenome() {
        if (isExternalStorageReadable()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(getExternalFile(genomeFile)));
                return (ScheduleGenome) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private File getExternalFile(String filename) {
        File path = context.getExternalFilesDir(null);
        return new File(path, filename);
    }
}
