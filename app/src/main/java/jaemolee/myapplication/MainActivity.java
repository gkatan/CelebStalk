package jaemolee.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.*;
import io.fabric.sdk.android.Fabric;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "hkgebUK3AqafuGJYt59CWBnWy";
    private static final String TWITTER_SECRET = "knq01FQLfSQd3JmCFkDjqeERvcdBcw1xJAuFeAkrejGLHnjlqD";

    DBHelper mydb;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
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

        // TODO: Use a more specific parent
        //
        final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();
        // TODO: Base this Tweet ID on some data from elsewhere in your app
        long tweetId = 631879971628183552L;
        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                TweetView tweetView = new TweetView(MainActivity.this, result.data);
                parentView.addView(tweetView);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Load Tweet failure", exception);
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
        mydb.insertProfile(new Profile ("Kanye West", "Rapper & designer", default_image, 0));
        mydb.insertProfile(new Profile("Barack Obama", "President of US", default_image, 0));
        mydb.insertProfile(new Profile("Taylor Swift", "Singer & songwriter", default_image, 0));
        mydb.insertProfile(new Profile("Hillary Clinton", "Secretary of State 2009-2013", default_image, 0));
        mydb.insertProfile(new Profile("Daniel Radcliffe", "English actor", default_image, 0));
        mydb.insertProfile(new Profile("Kim Kardashian", "American television & social media personality", default_image, 0));
        mydb.insertProfile(new Profile("Beyonce", "Singer, songwriter, and actress", default_image, 0));
        mydb.insertProfile(new Profile("Justin Bieber", "Singer & songwriter", default_image, 0));
        mydb.insertProfile(new Profile("Oprah Winfrey", "media proprietor & talk show host", default_image, 0));
        mydb.insertProfile(new Profile("Ellen DeGeneres", "Talk show host & comedian", default_image, 0));

        // Putting in actions for the first three celebrities: Kanye West, Obama, and Hillary Clinton
       // mydb.insertAction(new Action("Barack Obama", "facebook", "Read from EPA Administrator Gina McCarthy on why the global community is in a strong position heading into the international climate talks. http://ofa.bo/c5HX", getDateTime()));

    }

    // get DateTime
    private String getDateTime(int n) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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
        } else if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, About.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
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
