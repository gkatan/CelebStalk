package jaemolee.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DBHelper mydb;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        b1 = (Button) findViewById(R.id.resetdb);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
