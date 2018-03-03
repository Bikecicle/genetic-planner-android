package planner.external.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import planner.internal.core.C;
import planner.internal.item.Tab;

/**
 * Created by Griffin on 3/1/2018.
 */

public class TabAdapter extends ArrayAdapter<Tab> {

    private String dtFormat;

    public TabAdapter(Context context, List<Tab> data, String dtFormat) {
        super(context, 0, data);
        this.dtFormat = dtFormat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, parent, false);

        Tab tab = getItem(position);

        TextView tvStart = convertView.findViewById(R.id.tvStart);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvDetails = convertView.findViewById(R.id.tvDetails);

        tvStart.setText(new SimpleDateFormat(dtFormat).format(tab.getStartDate().getTime()));
        tvTitle.setText(tab.getTitle());
        tvDetails.setText(tab.getDetails());

        return convertView;
    }
}
