package com.software1605.androidtraining.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDaoSQLite extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "androidtest.db";
    public static final String TABLE_NAME = "tb_user";
    public UserDaoSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key, CustomName text, OrderPrice integer, Country text)";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
