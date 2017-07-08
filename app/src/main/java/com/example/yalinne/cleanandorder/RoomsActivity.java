package com.example.yalinne.cleanandorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class RoomsActivity extends AppCompatActivity {

    private
    SqlHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SqlHelper(this);
        List<Room> rooms = new ArrayList<Room>();
        rooms=db.getAllRooms();
        ListView listContent = (ListView) findViewById(R.id.listView);

        ListAdapter customAdapter = new RoomListAdapter(this, R.layout.roomlistrow, rooms);
        listContent.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onNextClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this, SummaryActivity.class));
        }
    }

    public void onNewRoomClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                startActivity(new Intent(this, NewRoomActivity.class));
        }
    }

    public void onEditRoom(View view) {
        ImageView iv = (ImageView)view;

        Integer theId = (Integer)iv.getTag();

        Intent intent = new Intent(this, ChoresSelectActivity.class);
        intent.putExtra("id", theId);
        startActivity(intent);
    }
}
