package planner.external.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import planner.internal.item.Note;

/**
 * Created by Griffin on 3/1/2018.
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);

        Note note = getItem(position);

        TextView tvStart = convertView.findViewById(R.id.tvStart);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvDetails = convertView.findViewById(R.id.tvDetails);

        tvStart.setText(new SimpleDateFormat(dtFormat).format(note.getStartDate().getTime()));
        tvTitle.setText(note.getTitle());
        tvDetails.setText(note.getDetails());

        return convertView;
    }
}
