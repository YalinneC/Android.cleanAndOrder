package com.example.yalinne.cleanandorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class RoomListAdapter extends ArrayAdapter<Room> {
    private List<Room> items;

    public RoomListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public RoomListAdapter(Context context, int resource, List<Room> items) {
        super(context, resource, items);

        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.roomlistrow, null);
        }

        Room p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);
            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            ImageView iv = (ImageView) v.findViewById(R.id.imageView);
            if (iv != null) {
                iv.setTag(p.getId());
            }
        }
        return v;
    }
}
