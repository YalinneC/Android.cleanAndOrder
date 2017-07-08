package com.example.yalinne.cleanandorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class NewChoreActivity extends AppCompatActivity {

    private
    SqlHelper db;
    Integer theId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newchore);
        db = new SqlHelper(this);

        theId = -1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            theId = extras.getInt("id");
        }
    }

    public void onNextClick(View view) {
        switch (view.getId()) {
            case R.id.nextButton: {
                EditText newChoreEdit = (EditText) findViewById(R.id.editText);
                Chore chore = new Chore(newChoreEdit.getText().toString(), -1, theId);
                db.addChore(chore);

                Intent intent = new Intent(this, ChoresSelectActivity.class);
                intent.putExtra("id", theId);
                startActivity(intent);
            }
        }
    }
}
