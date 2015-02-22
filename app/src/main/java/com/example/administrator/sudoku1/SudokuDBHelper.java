package com.example.administrator.sudoku1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yoyoyok on 2/21/15 AD.
 */
public class SudokuDBHelper extends SQLiteOpenHelper {
    private static final String name = "sudoku.sqlite3";
    private static final  int version = 2;

    public SudokuDBHelper(Context ctx){

        super(ctx, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE sudoku (" +
                "_id integer primary key autoincrement,"+
                "difficulty text not null," +
                "minutes int default 0," +
                "seconds int default 0);" ;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS sudoku;";
        db.execSQL(sql);
        this.onCreate(db);
    }
}

