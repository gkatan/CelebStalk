package jaemolee.myapplication;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetUtils;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String FABRIC_KEY = "hkgebUK3AqafuGJYt59CWBnWy";
    private static final String FABRIC_SECRET = "knq01FQLfSQd3JmCFkDjqeERvcdBcw1xJAuFeAkrejGLHnjlqD";

    private ArrayList<DashItem> dashboard = new ArrayList<DashItem>();

    DBHelper mydb;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(FABRIC_KEY, FABRIC_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mydb = new DBHelper(this);
        int count = mydb.getProfileCount();

        // Adds things to the dashboard as dummy items.
        //IDEALLY A DATABASE WOULD POPULATE EACH PERSON WITH THEIR RESPECT SOCIAL MEDIA SITE USERNAMES

        String imgurl = "https://pbs.twimg.com/profile_images/585565077207678977/N_eNSBXi.jpg"; // kanye's twitter pic as test
        DashItem newPerson = new DashItem("Kanye West", "11/17/15", imgurl , "twitter",
                "Pusha T “Untouchable”, produced by Timbaland. http://www.good-music.com");
        newPerson.setTWUsername("kanyewest");
        dashboard.add(newPerson);


        String imgurl2 = "https://api.tumblr.com/v2/blog/taylorswift.tumblr.com/avatar";
        DashItem newPerson2 = new DashItem("Taylor Swift", "12/2/15", imgurl2, "twitter", "Making friends on Hamilton Island.");
        newPerson2.setTWUsername("taylorswift13");
        dashboard.add(newPerson2);

        DashItem newPerson3 = new DashItem("Taylor Swift", "12/2/15", imgurl2, "tumblr", "Making friends on Hamilton Island.");
        newPerson3.setTMBUsername("taylorswift");
        dashboard.add(newPerson3);

        DashItem newPerson4 = new DashItem("Kanye West", "1/4/14", imgurl, "facebook",
                "Don't worry over what other people are thinking about you. They're too busy worrying over what you are thinking about them.");
        newPerson4.setFBUsername("TheOfficialKanyeWest");
        dashboard.add(newPerson4);

        String imgurl3 = "https://pbs.twimg.com/profile_images/451007105391022080/iu1f7brY_400x400.png";
        DashItem newPerson5 = new DashItem("Barack Obama", "12/2/15", imgurl3, "twitter",
                "Countries all over the world are standing #UnitedOnClimate—join the conversation to be part of this historic moment: http://ofa.bo/h9ej");
        newPerson5.setTWUsername("BarackObama");
        dashboard.add(newPerson5);

        //parseTwitter("kanyewest");

        // Populates the dashboard and sets list adapters.
        final DashAdapter adapter = new DashAdapter(this, dashboard);
        ListView listView = (ListView) findViewById(R.id.dashboard);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DashItem item = adapter.getItem(position);

                switch (item.getSocMedType()){
                    case "twitter":
                        Intent twitter = new Intent(MainActivity.this, TweetList.class);
                        twitter.putExtra("tw_username", item.getTWUsername());
                        startActivity(twitter);
                        break;
                    case "tumblr":
                        Intent tumblr = new Intent(MainActivity.this, TumblrView.class);
                        tumblr.putExtra("tmb_username", item.getTMBUsername());
                        startActivity(tumblr);
                        break;
                    case "facebook":
                        Intent fb = new Intent(MainActivity.this, FacebookView.class);
                        fb.putExtra("fb_username", item.getFBUsername());
                        startActivity(fb);
                        break;
                }
            }
        });

        b1 = (Button) findViewById(R.id.resetdb);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.closeDB();
                mydb.deleteDB(getApplicationContext());
                buildDB(mydb);
            }
        });

        if (count < 1) {
            buildDB(mydb);
        }
    }

    public void buildDB(DBHelper mydb) {
        String default_image = "android.resource:/jaemolee.myapplication\\" + "+" +  "R.drawable.blankface";
        mydb.insertProfile(new Profile ("Kanye West", "rapper & designer", default_image, 0));
        mydb.insertProfile(new Profile("Barack Obama", "President of US", default_image, 0));
        mydb.insertProfile(new Profile("Hillary Clinton", "Secretary of State 2009-2013", default_image, 0));
        mydb.insertProfile(new Profile("Taylor Swift", "singer & songwriter", default_image, 0));
        mydb.insertProfile(new Profile("Daniel Radcliffe", "English actor", default_image, 0));
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

    /** Populates the dashboard based on who you're following, ideally from their Twitter username.
     public void parseTwitter(String tw_username){
     try {
     // Variable representing the amount of latest tweets for the JSON parser to parse.
     int tweetCount = 5;

     // URL for constructing the new JSON object.
     String url = "{https://api.twitter.com/1.1/statuses/user_timeline.json?count="+ tweetCount + "&screen_name=" + tw_username + "}";
     Log.d("url debug", url);

     // Creating JSON object reader instance
     JSONObject reader = new JSONObject(url);

     String name = reader.getString("name");
     String date = reader.getString("created_at");
     String imgURL = reader.getString("profile_image_url");
     String post = reader.getString("text");

     DashItem newItem = new DashItem(name, date, imgURL, "twitter", post);
     dashboard.add(newItem);

     } catch (JSONException e) {
     e.printStackTrace();
     }
     }

     Populates the dashboard based on who you're following, ideally from their Twitter username.
     public void parseTumblr(String tmb_username) throws ExecutionException, InterruptedException {

     /**String url = "api.tumblr.com/v2/blog/"+ tmb_username + "/posts[/text]?api_key=" + key + "&[limit=5]";
     JSONObject result = new JSONObject("{"+ url +"}");

     JSONArray arr = result.getJSONArray("posts");

     String imgURL = "https://api.tumblr.com/v2/blog/" + tmb_username + ".tumblr.com/avatar";
     for (int i = 0; i < arr.length(); i++){
     JSONObject jObj = arr.getJSONObject(i);

     String name = jObj.getString("name");
     String date = jObj.getString("date");
     String post = jObj.getString("body");

     DashItem newItem = new DashItem(name, date, imgURL, "tumblr", post);
     dashboard.add(newItem);
     }

     ArrayList<DashItem> tempdash = new DownloadTumblr().execute(tmb_username).get();
     for (int i = 0; i < 5; i++) {
     dashboard.add(tempdash.get(i));
     }
     }*/

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
        } else if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, About.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            return true;
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(MainActivity.this, Search.class));
            return true;
        } else if (id == R.id.nav_saved) {
            startActivity(new Intent(MainActivity.this, Saved.class));
            return true;
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(MainActivity.this, Logout.class));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
