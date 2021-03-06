package jaemolee.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Search extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AutoCompleteTextView actv;
    ListView listView;
    DBHelper mydb;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

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


        this.listView = (ListView)findViewById(R.id.nameList);

        ArrayList<Profile> list = mydb.getAllProfiles();

        CustomAdapter custom = new CustomAdapter(this, "all");
        listView.setAdapter(custom);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Profile p = mydb.getAllProfiles().get(position);

                Intent intent = new Intent(getApplicationContext(), CelebProfile.class);
                intent.putExtra("Pname", p.getName());
                intent.putExtra("desc", p.getDescription());
                intent.putExtra("image", p.getImage());
                intent.putExtra("sflag", p.getStalkingFlag());
                intent.putExtra("twitter", p.getTwitterName());
                intent.putExtra("facebook", p.getFacebookName());
                intent.putExtra("tumblr", p.getTumblrName());

                startActivity(intent);
            }
        });

        String[] people = {"Kanye West", "Hilary Clinton", "Daniel Radcliffe", "Ellen DeGeneres", "Oprah Winfrey",
                "Justin Bieber", "Beyonce", "Kim Kardashian", "Taylor Swift", "Barack Obama"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this,android.R.layout.simple_dropdown_item_1line, people);

        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteInput);
        actv.setAdapter(adapter2);

        //String text = this.actv.getEditableText().toString();

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = actv.getEditableText().toString();
                Profile q = mydb.getProfileByName(text);
                Intent intent2 = new Intent(getApplicationContext(), CelebProfile.class);
                intent2.putExtra("Pname", q.getName());
                intent2.putExtra("desc", q.getDescription());
                intent2.putExtra("image", q.getImage());
                intent2.putExtra("sflag", q.getStalkingFlag());
                intent2.putExtra("twitter", q.getTwitterName());
                intent2.putExtra("facebook", q.getFacebookName());
                intent2.putExtra("tumblr", q.getTumblrName());

                startActivity(intent2);

            }
        });

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
        // automatically handle clicks on the Home/Up button
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(Search.this, About.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(Search.this, MainActivity.class));
            return true;
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(Search.this, Search.class));
            return true;
        } else if (id == R.id.nav_saved) {
            startActivity(new Intent(Search.this, Saved.class));
            return true;
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(Search.this, Logout.class));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
