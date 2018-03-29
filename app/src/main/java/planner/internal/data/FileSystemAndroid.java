package planner.internal.data;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import planner.internal.item.Agenda;

public class FileSystemAndroid extends FileSystem {

    private final String agendaFile = "agenda";

    private Context context;

    public FileSystemAndroid(Context context) {
        this.context = context;
    }

    @Override
    public void load() {
        if (isExternalStorageReadable()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(getExternalFile(agendaFile)));
                agenda = (Agenda) in.readObject();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        schedule = agenda.extractSchedule();
    }

    @Override
    public void save() {
        if (isExternalStorageWritable()) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getExternalFile(agendaFile)));
                out.writeObject(agenda);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
