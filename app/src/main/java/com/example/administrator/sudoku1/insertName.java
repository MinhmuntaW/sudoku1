package com.example.administrator.sudoku1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class insertName extends ActionBarActivity {
    String user;
    String level;
    String sMin;
    String sSec;
    int min;
    int sec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_name);
        Intent data = this.getIntent();
        level = data.getStringExtra("difficulty");
        min = data.getIntExtra("minutes",0);
        sec =data.getIntExtra("seconds",0);
        sMin = Integer.toString(min);
       sSec = Integer.toString(sec);

        TextView tv = (TextView) findViewById(R.id.textView16);
        tv.setText(String.format("  %02d:%02d", min, sec));

    }

    public void buttonClicked(View v){
        EditText etUser = (EditText)findViewById(R.id.editText);
        user = etUser.getText().toString().trim();
            if(user.length()>0) {

               PostMessageTask p = new PostMessageTask();
               p.execute(user, level,sMin,sSec);

                Intent a = new Intent(this,MainActivity4.class);
                a.putExtra("diff", level);
                a.putExtra("minute", min);
                a.putExtra("second", sec);
                startActivity(a);

          }else {
                Toast t = Toast.makeText(this.getApplication(),"Please input the user name",Toast.LENGTH_SHORT);
                t.show();
            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert_name, menu);
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


    class PostMessageTask extends AsyncTask<String, Void, Boolean> {
        String line;
        StringBuilder buffer = new StringBuilder();

        @Override
        protected Boolean doInBackground(String... params) {
            String user = params[0];
            String message = params[1];
            String minutes = params[2];
            String seconds = params[3];
            HttpClient h = new DefaultHttpClient();
            HttpPost p = new HttpPost("http://ict.siit.tu.ac.th/~u5522770148/its333/post.php");

            List<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("user", user));
            values.add(new BasicNameValuePair("level", message));
            values.add(new BasicNameValuePair("minutes", minutes));
           values.add(new BasicNameValuePair("seconds", seconds));
            try {
                p.setEntity(new UrlEncodedFormEntity(values));
                HttpResponse response = h.execute(p);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                while((line = reader.readLine()) != null) {
                    buffer.append(line);

                }

                return true;
            } catch (UnsupportedEncodingException e) {
                Log.e("Error", "Invalid encoding");
            } catch (ClientProtocolException e) {
                Log.e("Error", "Error in posting a message");
            } catch (IOException e) {
                Log.e("Error", "I/O Exception");
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                /*Toast t = Toast.makeText(insertName.this.getApplicationContext(),
                        "Successfully post ",
                        Toast.LENGTH_SHORT);
                t.show();
*/



            }

            else {
                Toast t = Toast.makeText(insertName.this.getApplicationContext(),
                        "Unable to post your status",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }
}
