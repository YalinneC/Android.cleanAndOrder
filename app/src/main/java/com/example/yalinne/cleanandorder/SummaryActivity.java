package com.example.yalinne.cleanandorder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yalinnecastelan on 4/25/16.
 */
public class SummaryActivity extends AppCompatActivity {

    private
    SqlHelper db;
    String theMessage = "Here is the list of chores!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        db = new SqlHelper(this);

        List<User> users = new ArrayList<User>();
        users = db.getAllUsers();

        ScrollView userScrollView = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout LL1 = (LinearLayout) findViewById(R.id.linearLayout);
        for (Integer i=0; i<users.size(); i++){
            LinearLayout LL2 = new LinearLayout(this);
            LL1.addView(LL2);
            LL2.setBackgroundColor(Color.parseColor("#FF9b00"));
            LL2.setPadding(20, 60, 20, 40);
            LL2.setOrientation(LinearLayout.VERTICAL);
            LL2.refreshDrawableState();
            TextView tv = new TextView(this);

            tv.setText(users.get(i).getName());
            theMessage = theMessage + "\n" + users.get(i).getName() + ":\n";
            tv.setTextSize(18);
            tv.setTypeface(null, Typeface.BOLD);
            LL2.addView(tv);

            List<Chore> chores = new ArrayList<Chore>();
            chores = db.getChoresOfUser(users.get(i).getId());
            for (Integer k = 0; k<chores.size(); k++) {
                TextView tv2 = new TextView(this);
                tv2.setText(chores.get(k).getName());
                theMessage = theMessage + chores.get(k).getName() + "\n";
                tv2.setTextSize(16);
                LL2.addView(tv2);
            }
            theMessage = theMessage + "\n\n";
        }
    }

    public void onEmailClick(View view) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

/* Fill it with Data */
        //emailIntent.setType("text/plain");
        emailIntent.setType("application/octet-stream");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Clean and Order");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, theMessage);

/* Send it off to the Activity-Chooser */
        this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
