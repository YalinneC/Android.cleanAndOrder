package com.example.yalinne.cleanandorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class WelcomeActivity extends AppCompatActivity {

    private
    SqlHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        db = new SqlHelper(this);
        List<User> users = db.getAllUsers();
        EditText memberEdit;

        memberEdit = (EditText) findViewById(R.id.editText1);
        memberEdit.setText(users.get(0).getName());
        memberEdit = (EditText) findViewById(R.id.editText2);
        memberEdit.setText(users.get(1).getName());
        memberEdit = (EditText) findViewById(R.id.editText3);
        memberEdit.setText(users.get(2).getName());
        memberEdit = (EditText) findViewById(R.id.editText4);
        memberEdit.setText(users.get(3).getName());


        EditText groupEdit = (EditText) findViewById(R.id.editTextGroup);
        String groupName = db.getGroup();
        groupEdit.setText(groupName);

        // comment after initial insert
//        db.addRoom(new Room("kitchen"));
//        db.addRoom(new Room("bedroom"));
//        db.addRoom(new Room("bathroom"));
//        db.addRoom(new Room("patio"));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                EditText groupEdit = (EditText) findViewById(R.id.editTextGroup);
                db.insertOrUpdateGroup(groupEdit.getText().toString());

                EditText memberEdit;
                User user = new User();
                List<String> Members = new ArrayList();

                memberEdit = (EditText) findViewById(R.id.editText1);
                user.setId(0);
                user.setName(memberEdit.getText().toString());
                db.updateUser(user);
                memberEdit = (EditText) findViewById(R.id.editText2);
                user.setId(1);
                user.setName(memberEdit.getText().toString());
                db.updateUser(user);
                memberEdit = (EditText) findViewById(R.id.editText3);
                user.setId(2);
                user.setName(memberEdit.getText().toString());
                db.updateUser(user);
                memberEdit = (EditText) findViewById(R.id.editText4);
                user.setId(3);
                user.setName(memberEdit.getText().toString());
                db.updateUser(user);


                startActivity(new Intent(this, RoomsActivity.class));
            }
        }
    }
}