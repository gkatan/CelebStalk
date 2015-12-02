package jaemolee.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TweetList extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        Intent intent = getIntent();

        String username = intent.getStringExtra("tw_username");

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(username)
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);
    }
}

