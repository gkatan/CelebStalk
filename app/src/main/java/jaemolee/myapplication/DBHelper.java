package jaemolee.myapplication;

        import java.util.ArrayList;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteDatabase;

// A lot of the format of the code comes from http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Profiles.db";
    // Profiles table
    public static final String P_TABLE_NAME = "AllProfiles";
    public static final String P_NAME = "name";
    public static final String P_IMAGE = "image";
    public static final String P_DESCRIPTION = "description";
    public static final String P_STALKING_FLAG = "stalking_flag";

    // Actions table
    public static final String A_TABLE_NAME = "Actions";
    public static final String A_NAME = "name";
    public static final String A_FORUM = "forum";
    public static final String A_DATE = "date";
    public static final String A_TEXT = "text";
    public static final String A_ID = "id";

    // Profile table create statement
    public static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + P_TABLE_NAME + " (" + P_NAME + " TEXT PRIMARY KEY, "
            + P_IMAGE + " TEXT, " + P_DESCRIPTION + " TEXT, "
            + P_STALKING_FLAG + " INTEGER" + ")";

    // Actions table create statement
    public static final String CREATE_TABLE_ACTIONS = "CREATE TABLE "
            + A_TABLE_NAME + " (" + A_ID + " INTEGER PRIMARY KEY, "
            + A_NAME + " TEXT, " + A_FORUM + " TEXT, "
            + A_DATE + " DATETIME, " + A_TEXT + " TEXT" + ")";

    public Context context;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_ACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + A_TABLE_NAME + "");
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

        db.insert(P_TABLE_NAME, null, contentValues);
        return true;
    }

    public long insertAction (Action a)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(A_NAME, a.getName());
        contentValues.put(A_FORUM, a.getForum());
        contentValues.put(A_TEXT, a.getText());
        contentValues.put(A_DATE, a.getDate());

        return db.insert(P_TABLE_NAME, null, contentValues);
    }

    // Returns an array list of all Profiles in the database.
    public ArrayList<Profile> getAllProfiles()
    {
        ArrayList<Profile> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + P_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add( new Profile (res.getString(res.getColumnIndex(P_NAME)),
                    res.getString(res.getColumnIndex(P_IMAGE)),
                    res.getString(res.getColumnIndex(P_DESCRIPTION)),
                    res.getInt(res.getColumnIndex(P_STALKING_FLAG))
            ));
            res.moveToNext();
        }
        return array_list;
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
                    res.getInt(res.getColumnIndex(P_STALKING_FLAG))
            ));
            res.moveToNext();
        }
        return array_list;
    }

    // Returns an array list of all actions in the database.
    public ArrayList<Action> getAllActions()
    {
        ArrayList<Action> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + A_TABLE_NAME, null ); // Order by date?
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add( new Action (res.getString(res.getColumnIndex(A_NAME)),
                    res.getString(res.getColumnIndex(A_FORUM)),
                    res.getString(res.getColumnIndex(A_TEXT)),
                    res.getString(res.getColumnIndex(A_DATE)),
                    res.getInt(res.getColumnIndex(A_ID))
            ));
            res.moveToNext();
        }
        return array_list;
    }

    // Returns an array list of all of the given person's actions in the db.
    // NOTE - the name MUST match the name previously used EXACTLY.
    public ArrayList<Action> getActionsByName(String name)
    {
        ArrayList<Action> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + A_TABLE_NAME + " where " + A_NAME + " = " + name, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add( new Action (res.getString(res.getColumnIndex(A_NAME)),
                    res.getString(res.getColumnIndex(A_FORUM)),
                    res.getString(res.getColumnIndex(A_TEXT)),
                    res.getString(res.getColumnIndex(A_DATE)),
                    res.getInt(res.getColumnIndex(A_ID))
            ));
            res.moveToNext();
        }
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

        // updating row
        return db.update(P_TABLE_NAME, contentValues, P_NAME + " = ?",
                new String[] { String.valueOf(p.getName()) });
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

    // Updating an action
    // Matches the action by id number.
    public int updateAction (Action a) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(A_NAME, a.getName());
        contentValues.put(A_FORUM, a.getForum());
        contentValues.put(A_TEXT, a.getText());
        contentValues.put(A_DATE, a.getDate());
        // updating row
        return db.update(A_TABLE_NAME, contentValues, A_ID + " = ?",
                new String[] { String.valueOf(a.getId()) });
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