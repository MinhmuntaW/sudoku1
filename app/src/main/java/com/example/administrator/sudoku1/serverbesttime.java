package com.example.administrator.sudoku1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class serverbesttime extends ActionBarActivity {
    ArrayList<Map<String, String>> data;
    SimpleAdapter adapter;
    String user;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serverbesttime);
        Intent a = this.getIntent();
        level = a.getStringExtra("level");
        data = new ArrayList<Map<String, String>>();
        adapter = new SimpleAdapter(this,data,android.R.layout.simple_list_item_2,
                new String[] {"user", "message"},
                new int[] {android.R.id.text1, android.R.id.text2});
        ListView l = (ListView)findViewById(R.id.View);
        l.setAdapter(adapter);
        LoadMessageTask task = new LoadMessageTask();
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_serverbesttime, menu);
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
    class LoadMessageTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;
            String messages;
            String uname;
            //int min;
            //int sec;

            try {
                Log.e("LoadMessageTask",""+level);
                URL u = new URL("http://ict.siit.tu.ac.th/~u5522770148/its33/fetch.php?level="
                        + "hard");
                HttpURLConnection h = (HttpURLConnection) u.openConnection();
                h.setRequestMethod("GET");
                h.setDoInput(true);
                h.connect();

                int response = h.getResponseCode();
                if (response == 200) {
                    reader = new BufferedReader(new InputStreamReader(h.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    Log.e("LoadMessageTask", buffer.toString());
                    //Parsing JSON and displaying messages

                    //To append a new message:
                    //Map<String, String> item = new HashMap<String, String>();
                    //item.put("user", u);
                    //item.put("message", m);
                    //data.add(0, item);
                    JSONObject json = new JSONObject(buffer.toString());

                    JSONArray msgArray = json.getJSONArray("msg");
                    JSONObject jMessages = msgArray.getJSONObject(0);
                    messages = jMessages.getString("user");

                    /*for (int i = 0; i < msgArray.length(); i++) {
                        JSONObject jMessages = msgArray.getJSONObject(i);
                        uname = jMessages.getString("user");
                        messages = jMessages.getString("level");


                        //min = jMessages.getInt("minutes");
                        //sec = jMessages.getInt("seconds");
                        Map<String, String> item = new HashMap<String, String>();
                        item.put("user", uname);
                        item.put("message", messages);
                        data.add(0, item);
                    }*/

                }
                return true;
            } catch (MalformedURLException e) {
                Log.e("LoadMessageTask", "Invalid URL");
            } catch (IOException e) {
                Log.e("LoadMessageTask", "I/O Exception");
            } catch (JSONException e) {
                Log.e("LoadMessageTask", "Invalid JSON");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                adapter.notifyDataSetChanged();
                Toast t = Toast.makeText(serverbesttime.this.getApplicationContext(),
                        "Updated the timeline",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }
}
