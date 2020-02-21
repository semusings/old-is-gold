package com.aldieemaulana.president.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aldieemaulana.president.model.Price;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Price.CREATE_TABLE);
    }

    public long insertPrice(Price price) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Price.COLUMN_NAME, price.getName());
        values.put(Price.COLUMN_CP, price.getCp());
        values.put(Price.COLUMN_SP, price.getSp());

        // insert row
        long id = db.insert(Price.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public void delete(Price price) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Price.TABLE_NAME, Price.COLUMN_ID + " = ?",
                new String[]{String.valueOf(price.getId())});
        db.close();
    }

    public int update(Price price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Price.COLUMN_NAME, price.getName());
        values.put(Price.COLUMN_CP, price.getCp());
        values.put(Price.COLUMN_SP, price.getSp());

        // updating row
        return db.update(Price.TABLE_NAME, values, Price.COLUMN_ID + " = ?",
                new String[]{String.valueOf(price.getId())});
    }

    public Price getPrice(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Price.TABLE_NAME,
                new String[]{Price.COLUMN_ID, Price.COLUMN_NAME, Price.COLUMN_SP, Price.COLUMN_CP},
                Price.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Price price = new Price(
                cursor.getInt(cursor.getColumnIndex(Price.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Price.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Price.COLUMN_SP)),
                cursor.getString(cursor.getColumnIndex(Price.COLUMN_CP))
        );

        // close the db connection
        cursor.close();

        return price;
    }

    public List<Price> getAllPrices() {
        List<Price> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Price.TABLE_NAME + " ORDER BY " +
                Price.COLUMN_NAME + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Price price = new Price();
                price.setId(cursor.getInt(cursor.getColumnIndex(Price.COLUMN_ID)));
                price.setName(cursor.getString(cursor.getColumnIndex(Price.COLUMN_NAME)));
                price.setSp(cursor.getString(cursor.getColumnIndex(Price.COLUMN_SP)));
                price.setCp(cursor.getString(cursor.getColumnIndex(Price.COLUMN_CP)));

                notes.add(price);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Price.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
}