package issues8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "am_training";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String ID_COL = "user_id";
    private static final String NAME_COL = "user_name";
    private static final String AGE_COL = "age";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + AGE_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addNewUser(String name, String age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(AGE_COL, age);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<UserModel> readUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorUsers = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<UserModel> userArrayLists = new ArrayList<>();
        if (cursorUsers.moveToFirst()) {
            int cnt = 1;
            do {
                userArrayLists.add(new UserModel(cursorUsers.getInt(0), cursorUsers.getString(1), cursorUsers.getString(2), cnt++));
            } while (cursorUsers.moveToNext());
        }
        cursorUsers.close();
        return userArrayLists;
    }

    public void deleteUserById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "user_id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
