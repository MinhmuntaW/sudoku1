package com.example.administrator.sudoku1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class MainActivity2 extends ActionBarActivity {
    SudokuDBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
    }
    public void level4clicked(View v){
      Intent i;
//      Intent a;
      int [] model;
        int id = v.getId();
        switch(id) {
            case R.id.btEasy:
                i = new Intent(this,MainActivity3.class);

                model = new int[]{0,0,0,0,8,0,0,1,0,
                        0,8,0,0,0,0,0,2,0,
                        0,0,9,1,7,0,3,4,0,
                        0,0,0,2,0,3,5,0,0,
                        7,0,3,4,5,8,1,0,2,
                        0,0,6,7,0,9,0,0,0,
                        0,2,8,0,3,4,7,0,0,
                        0,7,0,0,0,0,0,9,0,
                        0,3,0,0,9,0,0,0,0};
                i.putExtra("value",model);
                 i.putExtra("difficulty", "Easy");

                startActivity(i);
                break;
            case R.id.btMedium:
                i = new Intent(this,MainActivity3.class);

                model = new int[]{3,4,0,0,0,0,0,0,5,
                        0,0,0,0,0,0,0,0,1,
                        0,0,0,2,6,5,0,0,0,
                        0,0,8,0,1,0,4,0,0,
                        0,0,9,8,0,7,6,0,0,
                        0,0,7,0,3,0,2,0,0,
                        0,0,0,4,8,3,0,0,0,
                        5,0,0,0,0,0,0,0,0,
                        6,0,0,0,0,0,0,8,7};
                i.putExtra("value",model);
                i.putExtra("difficulty", "Norm");

                startActivity(i);

                break;
            case R.id.btHard:
                i = new Intent(this,MainActivity3.class);

                model = new int[]{4,9,7,6,0,0,0,0,5,
                        6,0,0,3,0,0,0,0,1,
                        2,0,5,1,0,0,0,0,0,
                        9,1,8,0,0,0,0,8,0,
                        0,0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,5,0,1,
                        0,0,0,0,0,2,9,3,5,
                        0,0,0,0,0,5,0,0,4,
                        0,0,0,0,0,9,1,0,2};
                i.putExtra("value",model);
                i.putExtra("difficulty", "Hard");

                startActivity(i);
                break;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
}
