package issues10.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import issues10.Model.AppModel;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "am_training_migration";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "app";
    private static final String ID_COL = "app_id";
    private static final String NAME_COL = "app_name";
    private static final String RESOURCE_ID = "resource_id";
    private static final String FAVORITE_COL = "is_favorite";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + RESOURCE_ID + " INTEGER,"
                + FAVORITE_COL + " INTEGER)";

        db.execSQL(query);
    }

    public void addNewApp(String name, int resourceId, int isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(RESOURCE_ID, resourceId);
        values.put(FAVORITE_COL, isFavorite);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<AppModel> readApps() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorApps = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<AppModel> appModelArrayLists = new ArrayList<>();
        if (cursorApps.moveToFirst()) {
            do {
                appModelArrayLists.add(new AppModel(cursorApps.getInt(0), cursorApps.getInt(2), cursorApps.getString(1), cursorApps.getInt(3)));
            } while (cursorApps.moveToNext());
        }
        cursorApps.close();
        return appModelArrayLists;
    }

    public ArrayList<AppModel> readAppsByFavorite(int isFavorite) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorApps = db.query(TABLE_NAME, null, FAVORITE_COL + "=?", new String[]{String.valueOf(isFavorite)}, null, null, null);

        ArrayList<AppModel> appModelArrayLists = new ArrayList<>();
        if (cursorApps.moveToFirst()) {
            do {
                appModelArrayLists.add(new AppModel(cursorApps.getInt(0), cursorApps.getInt(2), cursorApps.getString(1), cursorApps.getInt(3)));
            } while (cursorApps.moveToNext());
        }
        cursorApps.close();
        return appModelArrayLists;
    }

    public void updateApp(int isFavorite, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVORITE_COL, isFavorite);
        db.update(TABLE_NAME, values, ID_COL + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
