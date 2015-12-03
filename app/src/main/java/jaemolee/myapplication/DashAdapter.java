package jaemolee.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class DashAdapter extends ArrayAdapter<DashItem> {
    private final Context context;
    private final ArrayList<DashItem> postList;

    public DashAdapter(Context context, ArrayList<DashItem> itemsArrayList) {

        super(context, R.layout.celeb_dash_item, itemsArrayList);

        this.context = context;
        this.postList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.celeb_dash_item, parent, false);

        // 3. Get the two text view from the rowView
        ImageView profilePic = (ImageView) rowView.findViewById(R.id.dash_pic);
        ImageView SMtype = (ImageView) rowView.findViewById(R.id.dash_socmed);
        TextView author = (TextView) rowView.findViewById(R.id.dash_author);
        TextView post = (TextView) rowView.findViewById(R.id.dash_post_content);
        TextView date = (TextView) rowView.findViewById(R.id.dash_datetime);
        TextView username = (TextView) rowView.findViewById(R.id.dash_username);

        // 4. Set the text for textViews
        author.setText(postList.get(position).getAuthor());
        post.setText(postList.get(position).getContent());
        date.setText(postList.get(position).getDate());

        switch(postList.get(position).getSocMedType()){
            case "twitter":
                SMtype.setImageResource(R.mipmap.ic_twitter);
                username.setText(postList.get(position).getTWUsername());
                    break;
            case "facebook":
                SMtype.setImageResource(R.mipmap.ic_facebook);
                username.setText(postList.get(position).getFBUsername());
                break;
            case "tumblr":
                SMtype.setImageResource(R.mipmap.ic_tumblr);
                username.setText(postList.get(position).getTMBUsername());
                break;
            default:
                SMtype.setImageResource(R.mipmap.ic_incognito);
                break;
        }

        String imgurl = postList.get(position).getProfImgURL();
        new DownloadImageTask(profilePic).execute(imgurl);

        return rowView;
    }

    // Downloads an image from a URL and sets an ImageView to that downloaded image.
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public DashItem getItem(int position){
        return postList.get(position);
    }
}
