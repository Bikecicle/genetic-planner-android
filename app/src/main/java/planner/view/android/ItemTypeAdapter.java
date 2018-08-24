package planner.view.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import planner.model.item.ItemType;

/**
 * Created by Griffin on 3/6/2018.
 */

public class ItemTypeAdapter extends ArrayAdapter<ItemType> {

    public ItemTypeAdapter(Context context) {
        super(context, 0, ItemType.values());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_type, parent, false);

        ItemType type = getItem(position);

        TextView name = convertView.findViewById(R.id.item_name);

        name.setText(type.name());

        return convertView;
    }
}
