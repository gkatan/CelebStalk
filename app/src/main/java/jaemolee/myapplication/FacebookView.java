package jaemolee.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FacebookView extends AppCompatActivity {
    private WebView webview;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_view);

        progress = ProgressDialog.show(this, "Loading", "Please wait...", true);
        progress.setCancelable(false);

        Intent intent = getIntent();
        String username = intent.getStringExtra("fb_username");
        String url = "https://m.facebook.com/" + username;

        webview = (WebView) findViewById(R.id.facebookWeb);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progress.show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progress.dismiss();
            }
        });

        webview.loadUrl(url);
    }
}
