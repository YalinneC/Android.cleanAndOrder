package com.example.yalinne.cleanandorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class NewRoomActivity  extends AppCompatActivity {

    private
    SqlHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newroom);
        db = new SqlHelper(this);
    }

    public void onNextClick(View view) {
        switch (view.getId()) {
            case R.id.nextButton: {
                EditText newRoomEdit = (EditText) findViewById(R.id.editText);
                Room room = new Room(newRoomEdit.getText().toString());
                db.addRoom(room);
                startActivity(new Intent(this, RoomsActivity.class));
            }
        }
    }
}
