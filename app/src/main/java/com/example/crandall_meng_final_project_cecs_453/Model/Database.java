package com.example.crandall_meng_final_project_cecs_453.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crandall_meng_final_project_cecs_453.R;

/*
    Database is responsible for connecting to and initializing the sql lite database used by
    the rest of the application.
 */
public class Database implements AutoCloseable {

    public SQLiteDatabase db;

    // Ensures that the `login` table exists.
    protected static final String ENSURE_USER_TABLE = "create TABLE IF NOT EXISTS login(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL, " +
            "password TEXT NOT NULL, " +
            "email TEXT NOT NULL, " +
            "phone TEXT NOT null, " +
            "age INTEGER)";

    // Ensures that the `default_user` table exists.
    protected static final String ENSURE_DEFAULT_USER_TABLE = "create TABLE IF NOT EXISTS default_user(username TEXT NOT NULL)";

    // Opens the database connection and Ensures the database is in the proper state
    public Database(Context ctx) {
        db = ctx.openOrCreateDatabase(ctx.getResources().getString(R.string.database_name), Context.MODE_PRIVATE, null);
        db.execSQL(ENSURE_USER_TABLE);
        db.execSQL(ENSURE_DEFAULT_USER_TABLE);
    }

    // Closes the database connection
    public void close() {
        db.close();
    }
}
