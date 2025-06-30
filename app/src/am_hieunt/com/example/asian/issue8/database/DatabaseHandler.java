package com.example.asian.issue8.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "User";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "User";
    public static final String KEY_ID = "user_id";
    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_AGE = "age";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_users_table = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER)", TABLE_NAME, KEY_ID, KEY_USERNAME, KEY_AGE);
        db.execSQL(create_users_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_users_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_users_table);
        onCreate(db);
    }
}
