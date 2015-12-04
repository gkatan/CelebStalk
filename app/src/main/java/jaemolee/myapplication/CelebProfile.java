
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by dannyslee12 on 2015-11-23.
 *
 * When the item is selected, it will come to the celeb_profile activity with the celeb_profile button
 * (with the unique id) and if user press the celeb_profile button, it will celeb_profile the corresponding id from table
 * and return to the MainActivity
 *
 * Changed this file to be the profile of the celeb that was selected. The button now allows the user to
 * remove/add the profile from the 'saved' list. Users are not able to delete profiles from the master list.
 */
public class CelebProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button saveBT, unsaveBT;
    ImageButton facebook, twitter, tumblr;
    String name, desc, image, fb_username, tw_username, tmb_username;
    int sflag;

    Intent intent = getIntent();

    DBHelper mydb;
    WebView preview;

    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.celeb_profile);
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

        Intent intent = getIntent();
        name = intent.getStringExtra("Pname");
        desc = intent.getStringExtra("desc");
        image = intent.getStringExtra("image");
        sflag = intent.getIntExtra("sflag", 0);
        tw_username = intent.getStringExtra("twitter");
        fb_username = intent.getStringExtra("facebook");
        tmb_username = intent.getStringExtra("tumblr");

        TextView profName = (TextView) findViewById(R.id.profile_name);
        profName.setText(name);

        preview = (WebView) findViewById(R.id.preview);
        preview.getSettings().setJavaScriptEnabled(true);
        preview.getSettings().setDomStorageEnabled(true);

        saveBT = (Button) findViewById(R.id.saveButton);
        unsaveBT = (Button) findViewById(R.id.unsaveButton);
        // TODO - should probably only display one of the buttons depending if the profile is currently saved or unsaved.
        // Also - really shouldn't just go to 'saved', it should go to whatever activity was under this one.
        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.stalkProfile(new Profile(name, desc, image, sflag));

                Intent intent = new Intent(getApplicationContext(), Saved.class);
                startActivity(intent);
            }
        });


        unsaveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.unstalkProfile(new Profile(name, desc, image, sflag));

                Intent intent = new Intent(getApplicationContext(), Saved.class);
                startActivity(intent);
            }
        });

        // Handles when the Facebook button is clicked.
        facebook = (ImageButton) findViewById(R.id.button_facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                preview.loadUrl("https://www.facebook.com/" + fb_username + "/");
            }
        });

        // Handles when the Twitter button is clicked.
        twitter = (ImageButton) findViewById(R.id.button_twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                preview.loadUrl("https://www.twitter.com/" + tw_username);
            }
        });

        // Handles when the Facebook button is clicked.
        tumblr = (ImageButton) findViewById(R.id.button_tumblr);
        tumblr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                preview.loadUrl("http://" + tmb_username + ".tumblr.com/mobile");
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
            startActivity(new Intent(CelebProfile.this, About.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(CelebProfile.this, MainActivity.class));
            return true;
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(CelebProfile.this, Search.class));
            return true;
        } else if (id == R.id.nav_saved) {
            startActivity(new Intent(CelebProfile.this, Saved.class));
            return true;
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(CelebProfile.this, Logout.class));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
