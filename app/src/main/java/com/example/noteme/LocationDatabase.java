package com.example.noteme;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LocationDatabase extends SQLiteOpenHelper  {
    public static final int DB_VERSION = 1;
    public static String DB_NAME = "Location.db";
    public static String DB_TABLE = "LocationTable";

    public static String COLUMN_ID = "LocationID";
    public static String COLUMN_ADDRESS = "Address";
    public static String COLUMN_LAT = "Latitude";
    public static String COLUMN_LONG = "Longtitude";
    public LocationDatabase(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_LAT + " TEXT," +
                COLUMN_LONG + " TEXT" + ")";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)   {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }
    public void addEntry(EntryModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, note.getAddressInput());
        values.put(COLUMN_LAT, note.getLatitudeInput());
        values.put(COLUMN_LONG, note.getLongitudeInput());
        db.insert(DB_TABLE, null, values);
        db.close();
    }

    public List<EntryModel> getEntry() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<EntryModel> entryModelList = new ArrayList<>();

        try {
            String queryStatement = "SELECT * FROM " + DB_TABLE;
            Cursor cursor = db.rawQuery(queryStatement, null);

            if (cursor.moveToFirst()) {
                do {
                    EntryModel entryModel = new EntryModel();
                    entryModel.setId(cursor.getInt(0));
                    entryModel.setAddressInput(cursor.getString(1));
                    entryModel.setLatitudeInput(cursor.getString(2));
                    entryModel.setLongitudeInput(cursor.getString(3));
                    entryModelList.add(entryModel);
                    Log.d("NoteCount", "Number of notes retrieved: " + entryModelList.size());
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return entryModelList;
    }
    public EntryModel getEntry(int id)   {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] query = new String[] {COLUMN_ID, COLUMN_ADDRESS, COLUMN_LAT, COLUMN_LONG,};
        Cursor cursor = db.query(DB_TABLE, query, COLUMN_ID+"=?", new String []{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return new EntryModel(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
    }
    void deleteEntry(int id)     {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DB_TABLE, COLUMN_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
