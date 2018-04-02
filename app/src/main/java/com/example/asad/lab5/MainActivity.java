package com.example.asad.lab5;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final public String TAG = "MainActivity";
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor c = db.getData();

        try {
            while (c.moveToNext()) {
                addToView(c.getString(c.getColumnIndex("name")));
            }
        } finally {
            c.close();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Add:
                Log.d(TAG, "onOptionsItemSelected: works");
                openDialog();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDialog() {
        final Dialog commentDialog = new Dialog(this);
        commentDialog.setContentView(R.layout.reply);
        Button okBtn = (Button) commentDialog.findViewById(R.id.ok);
        final EditText input = commentDialog.findViewById(R.id.body);


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = input.getText().toString();
                addToView(reply);

                Toast.makeText(
                        getApplicationContext(),
                        reply,
                        Toast.LENGTH_LONG
                ).show();

                db.insertAnimal(reply);
                commentDialog.dismiss();
            }
        });

        Button cancelBtn = (Button) commentDialog.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                commentDialog.dismiss();
            }
        });
        commentDialog.show();
    }

    private void addToView(String reply) {
        TextView tv = new TextView(this);
        tv.setText(reply);
        tv.setTextSize(2, 20);
        tv.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        ((LinearLayout)findViewById(R.id.main)).addView(tv);
    }
}
