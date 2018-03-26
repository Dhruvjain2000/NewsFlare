package com.example.android.newsflare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "OKHTTPCallBack";
    private ArrayList<String> selectedPreferences;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.RecycleNews);
        String url = "https://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=704e22f755d64beea5360a1d8dd6509e";
        makeConnection(url);
    }

    private void makeConnection(String urlS) {

        try {
            URL url = new URL(urlS);
            OkHttpClient okHttpClient = new OkHttpClient();

//            OkHttpClient.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
//            OkHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Error");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String ans = response.body().string();
                    ArrayList<NewsBrief> newsBriefs = null;
                    try {
                        newsBriefs = parseJson(ans);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final ArrayList<NewsBrief> finalNewsBriefs = newsBriefs;
                    Log.e(TAG, "Size = " + newsBriefs.size());
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(finalNewsBriefs);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerView.setAdapter(recycleViewAdapter);
//                            recycleViewAdapter.notifyDataSetChanged();
                        }
                    });


                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    private ArrayList<NewsBrief> parseJson(String ans) throws JSONException {
        ArrayList<NewsBrief> newsBriefs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(ans);
        JSONArray jsonArray = jsonObject.getJSONArray("articles");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String title = object.getString("title");
            String imgURL = object.getString("urlToImage");
            String description = object.getString("description");
            String newsURL = object.getString("url");
            String publishedAt = object.getString("publishedAt");
            NewsBrief component = new NewsBrief(title, imgURL, description, newsURL, publishedAt);
            newsBriefs.add(component);
        }
//        for (int i=0;int<newsBriefs.size();i++)

//        Log.e(TAG,"json is-"+newsBriefs.get(i).getNewsTitle());

        return newsBriefs;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.action_preferences) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Choose Your Preferences");
            builder.setView(R.id.dialog);
            CheckBox checkBoxEduction, checkBoxLaw, checkBoxHealth, checkBoxFinance, checkBoxSports;
            checkBoxEduction = findViewById(R.id.checkBoxEduction);
            checkBoxLaw = findViewById(R.id.checkBoxLaw);
            checkBoxFinance = findViewById(R.id.checkBoxFinance);
            checkBoxHealth = findViewById(R.id.checkBoxHealth);
            checkBoxSports = findViewById(R.id.checkBoxSports);
           // builder.setPositiveButton() {
                String element;
                if (checkBoxEduction.isChecked()) {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Education")
                            break;
                        if (i == selectedPreferences.size() - 1)
                            selectedPreferences.add("Education");
                    }
                } else {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Education")
                            selectedPreferences.remove(i);
                    }
                }

                if (checkBoxFinance.isChecked()) {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Finance")
                            break;
                        if (i == selectedPreferences.size() - 1)
                            selectedPreferences.add("Finance");
                    }
                } else {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Finance")
                            selectedPreferences.remove(i);
                    }
                }

                if (checkBoxHealth.isChecked()) {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Health and Fitness")
                            break;
                        if (i == selectedPreferences.size() - 1)
                            selectedPreferences.add("Health and Fitness");
                    }
                } else {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Health and Fitness")
                            selectedPreferences.remove(i);
                    }
                }
                if (checkBoxLaw.isChecked()) {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Law")
                            break;
                        if (i == selectedPreferences.size() - 1)
                            selectedPreferences.add("Law");
                    }
                } else {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Law")
                            selectedPreferences.remove(i);
                    }
                }
                if (checkBoxSports.isChecked()) {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Sports")
                            break;
                        if (i == selectedPreferences.size() - 1)
                            selectedPreferences.add("Sports");
                    }
                } else {
                    for (int i = 0; i < selectedPreferences.size(); i++) {
                        element = selectedPreferences.get(i);
                        if (element == "Sports")
                            selectedPreferences.remove(i);
                    }

                }
            builder.create();
        }


//        }
        return super.onOptionsItemSelected(item);
    }

//
//    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
        } else if (id == R.id.nav_education) {
            // Handle the education action
            setContentView(R.layout.activity_education);
        } else if (id == R.id.nav_slideshow) {
            setContentView(R.layout.activity_health);

        } else if (id == R.id.nav_health) {
            setContentView(R.layout.activity_health);

        } else if (id == R.id.nav_law) {
            setContentView(R.layout.activity_law);

        } else if (id == R.id.nav_save) {
            setContentView(R.layout.activity_health);

        } else if (id == R.id.nav_preferences) {
            setContentView(R.layout.activity_health);
        } else if (id == R.id.nav_sports) {
            setContentView(R.layout.activity_sports);
        } else if (id == R.id.nav_finance) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
