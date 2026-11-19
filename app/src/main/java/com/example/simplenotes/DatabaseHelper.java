package com.example.simplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes_db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT UNIQUE, " +
                    COLUMN_CONTENT + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public long insertNote(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);

        long id = db.insertWithOnConflict(TABLE_NOTES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        return id;
    }

    public boolean updateNote(String oldTitle, String newTitle, String newContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_CONTENT, newContent);

        int rows = db.update(TABLE_NOTES, values, COLUMN_TITLE + " = ?", new String[]{oldTitle});
        db.close();
        return rows > 0;
    }

    public boolean deleteNote(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NOTES, COLUMN_TITLE + " = ?", new String[]{title});
        db.close();
        return rows > 0;
    }

    public boolean noteExists(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, new String[]{COLUMN_ID},
                COLUMN_TITLE + " = ?", new String[]{title},
                null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exists;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COLUMN_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
                notes.add(new Note(id, title, content, "SQLite"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }
}
