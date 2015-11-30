
package jaemolee.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.sql.SQLException;

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
public class CelebProfile extends Activity implements View.OnClickListener {

    Button saveBT;
    String name;
    DBHelper mydb;
    WebView webView;

    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.celeb_profile);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        mydb = new DBHelper(this);

        saveBT = (Button) findViewById(R.id.saveButton);
        Intent intent = getIntent();
        name = intent.getStringExtra("Pname");

        saveBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.saveButton:

                // Probably should also pass the stalkingFlag through so we could determine if the person needs to be saved or unsaved.
                mydb.stalkProfile(new Profile(name, "", "", 0));

                Intent intent = new Intent(getApplicationContext(), Search.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.unsaveButton:
                mydb.unstalkProfile(new Profile(name, "", "", 0));

                Intent intent2 = new Intent(getApplicationContext(), Search.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);


            case R.id.button_facebook:
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                webView.loadUrl("https://www.facebook.com");
                break;

            case R.id.button_twitter:
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                webView.loadUrl("https://www.twitter.com");
                break;
        }
    }
}
