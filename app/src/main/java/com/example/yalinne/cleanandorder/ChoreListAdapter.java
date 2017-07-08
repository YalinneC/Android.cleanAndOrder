package com.example.yalinne.cleanandorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class ChoreListAdapter extends ArrayAdapter<Chore> implements AdapterView.OnItemSelectedListener {

    private
    List<Chore> items;
    SqlHelper db;

    public ChoreListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public ChoreListAdapter(Context context, int resource, List<Chore> items) {
        super(context, resource, items);

        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        db = new SqlHelper(getContext());

        List<User> users = new ArrayList<User>();
        users = db.getAllUsers();
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(getContext(), android.R.layout.simple_spinner_item, users);

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.chorelistrow, null);
        }

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        Chore chore = getItem(position);

        if (chore != null) {
            spinner.setTag(chore.getId());
            spinner.setSelection(chore.getUserId());

            EditText editView = (EditText) v.findViewById(R.id.editText);
            if (editView != null) {
                editView.setText(chore.getName());
            }

            ImageView iv = (ImageView) v.findViewById(R.id.imageView);
            if (iv != null) {
                iv.setTag(chore.getId());
            }
        }
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        User user = (User)parent.getItemAtPosition(position);
        Integer choreId = (Integer)parent.getTag();
        db.updateChoreUser(choreId, user.getId());
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
