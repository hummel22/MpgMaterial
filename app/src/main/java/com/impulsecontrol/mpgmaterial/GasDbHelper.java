package com.impulsecontrol.mpgmaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

/**
 * Created by hummel on 7/1/16.
 */
public class GasDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GasNodes.db";

    public static final String TABLE_NAME =  "entry";
    public static final String COLUMN_NAME_MILEAGE = "mileage";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_GALLONS = "gallons";
    public static final String COLUMN_NAME_PRICE_PER_GALLON = "price_per_gallon";
    public static final String COLUMN_NAME_FULL_TANK = "full_tank";
    public static final String COLUMN_NAME_PRIUS_MILES = "prius_miles";
    public static final String COLUMN_NAME_PRIUS_MPG = "prius_mpg";
    public static final String COLUMN_NAME_PRIUS_SPEED = "prius_speed";

    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_INTEGER = " INTEGER";
    public static final String TYPE_DOUBLE = " REAL";
    public static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_NAME_MILEAGE + TYPE_INTEGER + " PRIMARY KEY" + COMMA_SEP +
            COLUMN_NAME_DATE + TYPE_TEXT + COMMA_SEP +
            COLUMN_NAME_GALLONS + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRICE_PER_GALLON + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_FULL_TANK + TYPE_INTEGER + COMMA_SEP +
            COLUMN_NAME_PRIUS_MILES + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRIUS_MPG + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRIUS_SPEED + TYPE_DOUBLE + ")";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public GasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

//    @Override
//    public void onOpen(SQLiteDatabase db) {
//
//    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
