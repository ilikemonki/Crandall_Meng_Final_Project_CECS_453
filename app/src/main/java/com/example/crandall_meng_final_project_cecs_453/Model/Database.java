package com.example.crandall_meng_final_project_cecs_453.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crandall_meng_final_project_cecs_453.R;

public class Database implements AutoCloseable {

    public SQLiteDatabase db;

    // DEBUG purposes.
    //protected static final String ENSURE_USER_TABLE = "DROP TABLE login";

    protected static final String ENSURE_USER_TABLE = "create TABLE IF NOT EXISTS login(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL, " +
            "password TEXT NOT NULL, " +
            "email TEXT NOT NULL, " +
            "phone TEXT NOT null, " +
            "age INTEGER)";

    public Database(Context ctx) {
        db = ctx.openOrCreateDatabase(ctx.getResources().getString(R.string.database_name), Context.MODE_PRIVATE, null);
        db.execSQL(ENSURE_USER_TABLE);
    }

    public void close() {
        db.close();
    }
}
