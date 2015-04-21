package com.example.administrator.sudoku1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.CharArrayWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity3 extends ActionBarActivity {
    Handler handler = new Handler();
    Timer timer = new Timer();
    TimerTask timetask;
    long starttime = 0L;
    static int [][] model;
    int seconds =0;
    int minutes = 0;
    int totaltime = 0;
    int pause =0;
    int [][]board ;
    String [] Cmd={"Yes","No"};
    EditText valueTV[][] = new EditText[9][9];

    String diff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);
        Intent model = this.getIntent();
        int[] input = model.getIntArrayExtra("value");
        diff = model.getStringExtra("difficulty");



       fromPuzzleString(input);
        starttime=SystemClock.uptimeMillis();
      doTask();

    }
    public void doTask(){
        timetask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        if(pause==0){
                            seconds++;
                        }
                        if(seconds%60==0){
                            minutes++;
                        }

                        seconds= seconds %60;
//

                        TextView result = (TextView) findViewById(R.id.txtResult);
                        result.setText(String.format("  %02d:%02d", minutes, seconds));
                        totaltime = minutes;


//                        result.setText();

                    }

                });

            }};

        timer.schedule(timetask, 0, 1000); // Every 1 second

    }


    public void stopTask(){

        if(timetask != null)

        {

            timetask.cancel();

            TextView result = (TextView) findViewById(R.id.txtResult);

            result.setText("TimeTask stop.");

        }

    }

       public void menuClicked(View view){
           if(pause==0){
               pause=1;

           }
           registerForContextMenu(view);
           openContextMenu(view);
           unregisterForContextMenu(view);
       }


        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            menu.setHeaderIcon(android.R.drawable.btn_star_big_on);

            menu.setHeaderTitle("Are you want to quite");

            String [] menuItems = Cmd;
            for(int i=0;i<menuItems.length;i++){
                menu.add(Menu.NONE,i,i,menuItems[i]);
            }
        }
    public boolean onContextItemSelected(MenuItem item){
        int menuItemIndex = item.getItemId();
        String[] menuItems = Cmd;
        String CmdName = menuItems[menuItemIndex];

        if("Yes".equals(CmdName)){
            Intent a = new Intent(this,MainActivity.class);
            startActivity(a);

        }
        else if("No".equals(CmdName)){
            pause=0;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        RelativeLayout RL = (RelativeLayout) findViewById(R.id.MainL);


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                valueTV[i][j] = new EditText(this);
                valueTV[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                valueTV[i][j].setTextSize(15);
                if(model[i][j]!=0){

                  valueTV[i][j].setText(Integer.toString(model[i][j]));
                  valueTV[i][j].setBackgroundColor(0xFFAE6E3F);
                }
                else{
                    valueTV[i][j].setText(" ");
                    valueTV[i][j].setBackgroundColor(0xFF653319);
                }
                valueTV[i][j].setId(i);
                valueTV[i][j].setTextColor(Color.WHITE);


                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(70, 70);





                if (j<3){
                    if (i<3)
                        layoutParams.setMargins((i * 70) + ( 5* (i + 1)+7), (j * 70) + ((5 * (j + 1))+155), 0, 0);

                    else if ( i<6)
                        layoutParams.setMargins((i * 70) + (5 * (i + 1)+14), (j * 70) + (5 * (j + 1)+155), 0, 0);
                    else
                        layoutParams.setMargins((i * 70) + (5 * (i + 1)+21), (j * 70) + (5 * (j + 1)+155), 0, 0);

                }
                else if ( j<6) {
                    if (i < 3)
                        layoutParams.setMargins((i * 70) + (5 * (i + 1) + 7), (j * 70) + ((5 * (j + 1)) + 162), 0, 0);
                     else if (i < 6)
                        layoutParams.setMargins((i * 70) + (5 * (i + 1) + 14), (j * 70) + (5 * (j + 1) + 162), 0, 0);
                    else
                        layoutParams.setMargins((i * 70) + (5 * (i + 1) + 21), (j * 70) + (5 * (j + 1) + 162), 0, 0);
                }else if ( j<9) {
                    if (i < 3)
                        layoutParams.setMargins((i * 70) + (5 * (i + 1) + 7), (j * 70) + ((5 * (j + 1)) + 169), 0, 0);
                     else if (i < 6)
                        layoutParams.setMargins((i * 70) + (5 * (i + 1) + 14), (j * 70) + (5 * (j + 1) + 169), 0, 0);
                    else
                        layoutParams.setMargins((i * 70) + (5 * (i + 1) + 21), (j * 70) + (5 * (j + 1) + 169), 0, 0);

                }









                valueTV[i][j].setGravity(Gravity.CENTER);
                valueTV[i][j].setPadding(0, 0, 0, 0);
                valueTV[i][j].setLayoutParams(layoutParams);


                RL.addView(valueTV[i][j]);

            }
        }


        return true;
    }


    public void DoneClicked(View view){
       // pause=1;
        setmatrix();

        if(!isValid(board)){

            Toast.makeText(MainActivity3.this,"Oops! Try again", Toast.LENGTH_SHORT).show();
        }
        else{


             //  System.out.println(diff+minutes+seconds);
                Intent i = new Intent(this, MainActivity4.class);
                i.putExtra("difficulty", diff);
                i.putExtra("minutes", minutes);
                i.putExtra("seconds", seconds);
                startActivity(i);

        }


    }
    public void setmatrix()
    {
        board= new int[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++){
                String text=valueTV[i][j].getText().toString().trim();
                if(text.equals("")||text==null) {
                    board[i][j] = 0;

                }else{


                    board[i][j]=Integer.parseInt(text);
                }
            }
        }


    }
    public static boolean isValid(int[][] grid){
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++){
                System.out.println("\n\nCHECK ISVALID I = " + i + " GET J  = " + j );
                if(grid[i][j]<1 || grid[i][j]>9 || !isValid(i,j,grid)) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isValid(int i, int j,int[][] grid){

        System.out.println("\n\n LOOP GET I = " + i + " GET J  = " + j );

        for(int col=0;col<9;col++) {
            System.out.println("loop1 = "+grid[i][col]);

            if (col != j && grid[i][col] == grid[i][j])
                return false;
        }
        for(int row=0;row<9;row++) {
            System.out.println("loop2 = "+grid[row][j]);

            if (row != i && grid[row][j] == grid[i][j])
                return false;
        }
        for(int row=(i/3)*3;row<(i/3)*3+3;row++) {

            for (int col = (j/ 3) * 3; col < (j / 3) * 3 + 3; col++) {
                System.out.println("loop3 = "+grid[row][col]);

                if (row != i && col != j && grid[row][col] == grid[i][j])
                    return false;
            }
        }
        return true;
    }






//    public void DoneClicked(View v){
//        // check if the board had been filled
//
//
//
//
//        //else
//        Intent result = new Intent();
//        result.putExtra("time",totaltime);
//
//        this.setResult(RESULT_OK,result);
//        this.finish();
//    }

    public void fromPuzzleString(int[] str) {
        int[][] puz = new int[9][9];
        int k=0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                puz[i][j] = str[k];
                k++;

           }

        }
        model = puz;

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
