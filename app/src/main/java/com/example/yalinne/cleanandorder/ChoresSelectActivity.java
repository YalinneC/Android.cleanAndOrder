package com.example.yalinne.cleanandorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class ChoresSelectActivity extends AppCompatActivity {

    private
    SqlHelper db;
    Integer theId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores);
        theId = -1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            theId = extras.getInt("id");
        }

        db = new SqlHelper(this);

        List<Chore> chores = new ArrayList<Chore>();
        chores=db.getChores(theId); // list the chores for the selected room
        ListView listContent = (ListView) findViewById(R.id.listView);

        ListAdapter customAdapter = new ChoreListAdapter(this, R.layout.chorelistrow, chores);
        listContent.setAdapter(customAdapter);
    }

    public void onNextClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this, RoomsActivity.class));
        }
    }

    public void onNewChoreClick(View view) {
        switch (view.getId()) {
            case R.id.button3: {

                Intent intent = new Intent(this, NewChoreActivity.class);
                intent.putExtra("id", theId);
                startActivity(intent);
            }
        }
    }

    public void onDeleteClick(View view) {
        if (theId != -1) {
            Room room = new Room();
            room.setId(theId);
            db.deleteRoom(room);
            startActivity(new Intent(this, RoomsActivity.class));
        }
    }

    public void onDeleteChoreClick(View view) {
        switch (view.getId()) {
            case R.id.imageView: {
                ImageView iv = (ImageView) view.findViewById(R.id.imageView);
                Integer theChoreId = (Integer)iv.getTag();
                db.deleteChore(theChoreId);

                Intent intent = new Intent(this, ChoresSelectActivity.class);
                intent.putExtra("id", theId);
                startActivity(intent);
            }
        }
    }
}
