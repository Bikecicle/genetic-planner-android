package planner.view.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import planner.model.item.Note;

/**
 * Created by Griffin Page on 3/1/2018
 * griffinpage9@gmail.com
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    private String dtFormat;

    public NoteAdapter(Context context, List<Note> data, String dtFormat) {
        super(context, 0, data);
        this.dtFormat = dtFormat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note, parent, false);

        Note note = getItem(position);

        TextView tvStart = convertView.findViewById(R.id.note_start);
        TextView tvTitle = convertView.findViewById(R.id.note_title);
        TextView tvDetails = convertView.findViewById(R.id.note_details);

        tvStart.setText(new SimpleDateFormat(dtFormat).format(note.getStartDate().getTime()));
        tvTitle.setText(note.title);
        tvDetails.setText(note.details);

        return convertView;
    }
}
