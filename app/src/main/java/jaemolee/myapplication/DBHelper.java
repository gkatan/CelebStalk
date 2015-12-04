package jaemolee.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

// A lot of the format of the code comes from http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Profiles.db";
    // Profiles table
    public static final String P_TABLE_NAME = "AllProfiles";
    public static final String P_NAME = "name";
    public static final String P_IMAGE = "image";
    public static final String P_DESCRIPTION = "description";
    public static final String P_STALKING_FLAG = "stalking_flag";
    public static final String P_TWITTER_NAME = "twitter_name";
    public static final String P_FACEBOOK_NAME = "facebook_name";
    public static final String P_TUMBLR_NAME = "tumblr_name";

    // Profile table create statement
    public static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + P_TABLE_NAME + " (" + P_NAME + " TEXT PRIMARY KEY, "
            + P_IMAGE + " TEXT, " + P_DESCRIPTION + " TEXT, "
            + P_STALKING_FLAG + " INTEGER, "
            + P_TWITTER_NAME + " TEXT, "
            + P_FACEBOOK_NAME + " TEXT, "
            + P_TUMBLR_NAME + " TEXT " + ")";


    public Context context;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + P_TABLE_NAME + "");
        onCreate(db);
    }

    public boolean insertProfile (Profile p)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(P_NAME, p.getName());
        contentValues.put(P_IMAGE, p.getImage());
        contentValues.put(P_DESCRIPTION, p.getDescription());
        contentValues.put(P_STALKING_FLAG, p.getStalkingFlag());
        contentValues.put(P_TWITTER_NAME, p.getTwitterName());
        contentValues.put(P_FACEBOOK_NAME, p.getFacebookName());
        contentValues.put(P_TUMBLR_NAME, p.getTumblrName());

        db.insert(P_TABLE_NAME, null, contentValues);
        return true;
    }

    // Returns an array list of all Profiles in the database.
    public ArrayList<Profile> getAllProfiles()
    {
        ArrayList<Profile> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + P_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Profile(res.getString(res.getColumnIndex(P_NAME)),
                    res.getString(res.getColumnIndex(P_IMAGE)),
                    res.getString(res.getColumnIndex(P_DESCRIPTION)),
                    res.getInt(res.getColumnIndex(P_STALKING_FLAG))
                    ,res.getString(res.getColumnIndex(P_TWITTER_NAME)),
                    res.getString(res.getColumnIndex(P_FACEBOOK_NAME)),
                    res.getString(res.getColumnIndex(P_TUMBLR_NAME))
            ));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public Profile getProfileByName(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        String n = "\"" + name + "\"";
        Cursor res =  db.rawQuery( "select * from " + P_TABLE_NAME + " where " + P_NAME + "= " + n, null );
        res.moveToFirst();

        Profile p = new Profile(res.getString(res.getColumnIndex(P_NAME)),
                    res.getString(res.getColumnIndex(P_IMAGE)),
                    res.getString(res.getColumnIndex(P_DESCRIPTION)),
                    res.getInt(res.getColumnIndex(P_STALKING_FLAG)),
                    res.getString(res.getColumnIndex(P_TWITTER_NAME)),
                    res.getString(res.getColumnIndex(P_FACEBOOK_NAME)),
                    res.getString(res.getColumnIndex(P_TUMBLR_NAME))
            );
            res.moveToNext();
        res.close();
        return p;

    }

    public String[] getAllNames() {
        int size = getAllProfiles().size();

        String[] A = new String[size];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select " + P_NAME + " from " + P_TABLE_NAME, null );
        res.moveToFirst();

        int count = 0;
        while(!res.isAfterLast()){
            A[count] = (res.getString(res.getColumnIndex(P_NAME)));
            res.moveToNext();
            count++;
        }
        res.close();
        return A;
    }

    public String[] getMyNames() {
        int size = getMyProfiles().size();

        String[] A = new String[size];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select " + P_NAME + " from " + P_TABLE_NAME + " where " + P_STALKING_FLAG + "= 1", null );
        res.moveToFirst();

        int count = 0;
        while(!res.isAfterLast()){
            A[count] = (res.getString(res.getColumnIndex(P_NAME)));
            res.moveToNext();
            count++;
        }
        res.close();
        return A;
    }


    // return array list of only the profiles you are stalking.
    public ArrayList<Profile> getMyProfiles()
    {
        ArrayList<Profile> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + P_TABLE_NAME + " where " + P_STALKING_FLAG + "= 1", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add( new Profile (res.getString(res.getColumnIndex(P_NAME)),
                    res.getString(res.getColumnIndex(P_IMAGE)),
                    res.getString(res.getColumnIndex(P_DESCRIPTION)),
                    res.getInt(res.getColumnIndex(P_STALKING_FLAG)),
                    res.getString(res.getColumnIndex(P_TWITTER_NAME)),
                    res.getString(res.getColumnIndex(P_FACEBOOK_NAME)),
                    res.getString(res.getColumnIndex(P_TUMBLR_NAME))
            ));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    // Updating a profile, matches the given profile p by name.
    // So you can't update the NAME of the profile.
    public int updateProfile(Profile p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(P_NAME, p.getName());
        contentValues.put(P_IMAGE, p.getImage());
        contentValues.put(P_DESCRIPTION, p.getDescription());
        contentValues.put(P_STALKING_FLAG, p.getStalkingFlag());
        contentValues.put(P_TWITTER_NAME, p.getTwitterName());
        contentValues.put(P_FACEBOOK_NAME, p.getFacebookName());
        contentValues.put(P_TUMBLR_NAME, p.getTumblrName());

        // updating row
        return db.update(P_TABLE_NAME, contentValues, P_NAME + " = ?",
                new String[]{String.valueOf(p.getName())});
    }

    // Add profile to My list
    public int stalkProfile(Profile p) {
        p.setStalkingFlag (1);
        return updateProfile(p);
    }

    // Remove profile from My list
    public int unstalkProfile( Profile p) {
        p.setStalkingFlag(0);
        return updateProfile(p);
    }


    public int getProfileCount() {
        String countQuery = "SELECT  * FROM " + P_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    // Deletes the entire database. Call onCreate(db) afterwards to rebuild it.
    public void deleteDB(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}