package com.example.asian.issue8.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asian.issue8.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static UserDatabase instance;
    private SQLiteDatabase db;
    private final DatabaseHandler dbHandler;

    private UserDatabase(Context context) {
        dbHandler = new DatabaseHandler(context);
        open();
    }

    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new UserDatabase(context);
        }
        return instance;
    }

    private void open() {
        db = dbHandler.getWritableDatabase();
    }

    private void close() {
        dbHandler.close();
    }

    public void insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_USERNAME, user.getUsername());
        values.put(DatabaseHandler.KEY_AGE, user.getAge());
        db.insert(DatabaseHandler.TABLE_NAME, null, values);
    }

    public void deleteUser(int id) {
        db.delete(DatabaseHandler.TABLE_NAME, DatabaseHandler.KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllUser() {
        String sql = "DELETE FROM " + DatabaseHandler.TABLE_NAME;
        db.execSQL(sql);
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM " + DatabaseHandler.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursorToUser(cursor));
                cursor.moveToNext();
            }
            cursor.close();
            return users;
        }
        return null;
    }

    public User getNewUser() {
        String sql = "SELECT * FROM " + DatabaseHandler.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        User user = new User();
        if (cursor != null) {
            cursor.moveToLast();
            user = cursorToUser(cursor);
            cursor.close();
            return user;
        }
        return null;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setUsername(cursor.getString(1));
        user.setAge(cursor.getInt(2));
        return user;
    }
}
