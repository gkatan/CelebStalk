package jaemolee.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

//import com.tumblr.jumblr.JumblrClient;
//import com.tumblr.jumblr.types.Post;

/**
 * Created by Iris on 12/2/2015.
 */
public class DownloadTumblr extends AsyncTask<String, Void, ArrayList<DashItem>>{
    private ArrayList<DashItem> tempdash = new ArrayList<DashItem>();


    @Override
    protected ArrayList<DashItem> doInBackground(String... tmb_username){
        try {
            String key = "4IX1C3AvGK0k7VFmtyAuKIboBLczt737O5dR65mzJjYogDT2tr";
            String secret = "QNSnOEOqsrZZqF9A9m8bGFWiPKADVKpU4O41AKlRcSbGOGCYKo";
            /**
            // Authenticate via API Key
            JumblrClient client = new JumblrClient(key, secret);

            // Make the request
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", "text");
            params.put("limit", 5);
            List<Post> posts = client.blogPosts(tmb_username +".tumblr.com", params);

            String imgURL = "api.tumblr.com/v2/blog/" + tmb_username + ".tumblr.com/avatar";

            for (int i = 0; i < posts.size(); i++){
                String name = posts.get(i).getBlogName();
                String date = posts.get(i).getDateGMT();
                String post = posts.get(i).toString();

                DashItem newItem = new DashItem(name, date, imgURL, "tumblr", post);
                tempdash.add(newItem);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempdash;
    }

    protected void onPostExecute(){
        Log.d("tumblr request", "finished loading");
    }
}