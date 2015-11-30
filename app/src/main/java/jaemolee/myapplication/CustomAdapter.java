package jaemolee.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        //TODO Should be: Name, Image, Description, maybe a stalking 'flag' icon?. Can add date to the Profile object if desired.

        TextView name = (TextView) view.findViewById(R.id.pname);
        TextView desc = (TextView) view.findViewById(R.id.note);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        name.setText(entries.get(index).getName());
        desc.setText( entries.get(index).getDescription());

        //Uri imgUri= Uri.parse(entries.get(index).getImage());
        //image.setImageURI(imgUri);

        return view;
    }
}
