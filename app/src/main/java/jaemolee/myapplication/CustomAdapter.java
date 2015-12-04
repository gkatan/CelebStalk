package jaemolee.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;


public class CustomAdapter extends BaseAdapter {

    ArrayList<Profile> entries;
    Context context;
    DBHelper mydb;

    public CustomAdapter(Context context, String type) {

        mydb = new DBHelper(context);

        switch (type) {
            case "all":
                entries = mydb.getAllProfiles();
                break;
            case "saved":
                entries = mydb.getMyProfiles();
                break;
            default:
                entries = mydb.getAllProfiles();

        }

        this.context = context;
    }
    public void addEntry(Profile e) {
        entries.add(e);
    }

    public int getCount() {
        return entries.size();
    }

    public Object getItem (int index) {
        return entries.get(index);
    }

    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView (int index, View view, ViewGroup parent) {
        // Something is broken, cause this is never called.

    if (view == null) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.search_box, null);
    }

        //TODO Should be: Name, Image. Maybe a short description?

        TextView name = (TextView) view.findViewById(R.id.pname);
        //TextView desc = (TextView) view.findViewById(R.id.note);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        String imgurl = entries.get(index).getImage();

        if (!imgurl.equals(null)){
            new DownloadImageTask(image).execute(imgurl);
        }

        name.setText(entries.get(index).getName());


        return view;
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
}
