package com.example.taskify.core.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dao extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "task.db";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_TASK = "tasks";


    // Task Table Columns
    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_TASK_TITLE = "title";
    public static final String COLUMN_TASK_DESC = "description";
    public static final String COLUMN_TASK_PRIORITY = "priority";
    public static final String COLUMN_TASK_DATE = "date";

    // SQL statements to create the tables

    private static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + TABLE_TASK + " (" +
                    COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TASK_TITLE + " TEXT, " +
                    COLUMN_TASK_DESC + " TEXT, " +
                    COLUMN_TASK_PRIORITY + " TEXT, " +
                    COLUMN_TASK_DATE + " TEXT);";

    public Dao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create both tables
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

}
