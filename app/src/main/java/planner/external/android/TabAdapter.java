package planner.external.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import planner.internal.item.Tab;

/**
 * Created by Griffin on 3/1/2018.
 */

public class TabAdapter extends ArrayAdapter<Tab> {

    public TabAdapter(Context context, List<Tab> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, parent, false)
        }

        User user = getItem(position);

        // TODO: Define fields to show

        // TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        // tvName.setText(user.getName());

        return convertView;
    }

}
