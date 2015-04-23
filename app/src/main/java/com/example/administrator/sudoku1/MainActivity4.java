package com.example.administrator.sudoku1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity4 extends ActionBarActivity {

 String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4);
        Toast.makeText(MainActivity4.this, "Congratulations!", Toast.LENGTH_SHORT).show();


//        TextView t = (TextView)findViewById(R.id.TV9);
//        t.setText(diff+Integer.toString(min)+Integer.toString(sec));

        SudokuDBHelper helper = new SudokuDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase(); Intent data = this.getIntent();

        String diff = data.getStringExtra("diff");
        level = diff;
        int min = data.getIntExtra("minute", 0);
        int sec = data.getIntExtra("second", 0);
        ContentValues r = new ContentValues();

        r.put("difficulty",diff);
        r.put("minutes",min);
        r.put("seconds",sec);
        long new_id = db.insert("sudoku",null,r);



        SQLiteDatabase Rdb = helper.getReadableDatabase();
        Cursor cursor = Rdb.rawQuery("SELECT difficulty, minutes, seconds FROM sudoku WHERE difficulty ='" +diff+"' ORDER BY minutes ASC,seconds ASC LIMIT 0,5;",null);
        cursor.moveToFirst();
        int[] textViewIds = new int[] {R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5};
        //for(int i=0;i<5;i++) {
        int i = 0;
            do {

                TextView tv = (TextView) findViewById(textViewIds[i]);

                tv.setText("      "+Integer.toString(i + 1) + "                     " + cursor.getString(0) + "                " + String.format("%02d",cursor.getInt(1)) + " : " + String.format("%02d", cursor.getInt(2)));


                System.out.println(cursor.getString(0) + cursor.getInt(1) + cursor.getInt(2));
                i++;

            } while (cursor.moveToNext());
        //}
     //   String d = cursor.getString(0);
//        int m  = cursor.getInt(1);
//        int s  = cursor.getInt(2);
//        System.out.print(d+m+s);
//       TextView tv = (TextView)findViewById(R.id.TV9);

//       if(m>=0){
//
//           // tv.setText(d+"   "+Integer.toString(m)+" : "+Integer.toString(s));
//
//       }
    }
    public void backClicked(View v){

        Intent a = new Intent(this,MainActivity.class);
        startActivity(a);
    }
    public void globalClicked(View v){

        Intent a = new Intent(this,serverbesttime.class);
        a.putExtra("level",level);
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity4, menu);


       // LinearLayout LL = (LinearLayout) findViewById(R.id.LL);

        //LinearLayout layout = (LinearLayout) findViewById(R.id.info);
//          helper = new SudokuDBHelper(this);
//          SQLiteDatabase db = helper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT id,difficulty,MIN(time) FROM sudoku ORDER BY MIN(time) GROUP BY MIN(time) LIMIT 10;",null);
//        cursor.moveToFirst();



        return true;
    }
    protected void onActivityResult(int resultCode,Intent data){
                String diff = data.getStringExtra("difficulty");
         //   if(resultCode == RESULT_OK) {
//                minutes = data.getIntExtra("minutes", 0);
//                seconds = data.getIntExtra("seconds", 0);
//         //   }
//                  TextView t = (TextView)findViewById(R.id.TV9);
//                   t.setText(diff +" "+ minutes +" " + seconds);


//                SudokuDBHelper helper = new SudokuDBHelper(this);
//                SQLiteDatabase db = helper.getWritableDatabase();
//                ContentValues r= new ContentValues();
//
//                r.put("difficulty", diff);
//                r.put("minutes", minutes);
//                r.put("seconds", seconds);
//                long new_id = db.insert("sudoku",null,r);


       // Log.d("sudoku", "onActivityResult");
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
