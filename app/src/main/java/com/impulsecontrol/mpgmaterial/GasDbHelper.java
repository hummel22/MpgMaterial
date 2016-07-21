package com.impulsecontrol.mpgmaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by hummel on 7/1/16.
 */
public class GasDbHelper extends SQLiteOpenHelper {


    public static class Patch {
        public void applyVersion(SQLiteDatabase db) {

        }
        public void revert (SQLiteDatabase db) {

        }

    }
    private static final Patch[] PATCHES = new Patch[] {
            //Update to Version 2
            //Create New table
            //Copy Copy old values in - new table should auto set id
            new Patch() {
                @Override
                public void applyVersion(SQLiteDatabase db) {
                    Log.d("Database", "Applying Patch 1");
                    Log.i("Database", "Creating SQL Table: " + SQL_CREATE_ENTRIES);
                    db.execSQL(SQL_CREATE_ENTRIES);
                    String copySql = "INSERT INTO " + ENTRY_TABLE + " " +
                            "(" +
                            COLUMN_NAME_MILEAGE + " " + COMMA_SEP +
                            COLUMN_NAME_DATE + " " + COMMA_SEP +
                            COLUMN_NAME_GALLONS + " " + COMMA_SEP +
                            COLUMN_NAME_PRICE_PER_GALLON + " " + COMMA_SEP +
                            COLUMN_NAME_FULL_TANK+  " " + COMMA_SEP +
                            COLUMN_NAME_PRIUS_MILES + " " + COMMA_SEP +
                            COLUMN_NAME_PRIUS_MPG + " " + COMMA_SEP +
                            COLUMN_NAME_PRIUS_SPEED +  " " +
                            ") " +
                            "SELECT " +
                            COLUMN_NAME_MILEAGE + " " + COMMA_SEP +
                            COLUMN_NAME_DATE + " " + COMMA_SEP +
                            COLUMN_NAME_GALLONS + " " + COMMA_SEP +
                            COLUMN_NAME_PRICE_PER_GALLON + " " + COMMA_SEP +
                            COLUMN_NAME_FULL_TANK+  " " + COMMA_SEP +
                            COLUMN_NAME_PRIUS_MILES + " " + COMMA_SEP +
                            COLUMN_NAME_PRIUS_MPG + " " + COMMA_SEP +
                            COLUMN_NAME_PRIUS_SPEED +  " " +
                            "FROM " + ENTRY_TABLE_OLD;
                    Log.i("Database", "Copying " + copySql);
                    db.execSQL(copySql);
                    Log.i("Database", "Migration from DB.v.1 to DB.v.2 complete");
                }

                @Override
                public void revert(SQLiteDatabase db) {
                    super.revert(db);
                }
            }
    };
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "GasNodes.db";

    public static final String ENTRY_TABLE =  "entry_table";
    public static final String ENTRY_TABLE_OLD =  "entry";
    public static final String COLUMN_NAME_MILEAGE = "mileage";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_GALLONS = "gallons";
    public static final String COLUMN_NAME_PRICE_PER_GALLON = "price_per_gallon";
    public static final String COLUMN_NAME_FULL_TANK = "full_tank";
    public static final String COLUMN_NAME_PRIUS_MILES = "prius_miles";
    public static final String COLUMN_NAME_PRIUS_MPG = "prius_mpg";
    public static final String COLUMN_NAME_PRIUS_SPEED = "prius_speed";
    public static final String COLUMN_NAME_UNIQUE_ID = "unique_id";

    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_INTEGER = " INTEGER";
    public static final String TYPE_DOUBLE = " REAL";
    public static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + ENTRY_TABLE + " (" +
            COLUMN_NAME_UNIQUE_ID + TYPE_INTEGER + " PRIMARY KEY" + COMMA_SEP +
            COLUMN_NAME_MILEAGE + TYPE_INTEGER + COMMA_SEP +
            COLUMN_NAME_DATE + TYPE_TEXT + COMMA_SEP +
            COLUMN_NAME_GALLONS + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRICE_PER_GALLON + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_FULL_TANK + TYPE_INTEGER + COMMA_SEP +
            COLUMN_NAME_PRIUS_MILES + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRIUS_MPG + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRIUS_SPEED + TYPE_DOUBLE + ")" ;

    public static final String SQL_CREATE_ENTRIES_OLD = "CREATE TABLE " + ENTRY_TABLE_OLD + " (" +
            COLUMN_NAME_MILEAGE + TYPE_INTEGER + " PRIMARY KEY" + COMMA_SEP +
            COLUMN_NAME_DATE + TYPE_TEXT + COMMA_SEP +
            COLUMN_NAME_GALLONS + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRICE_PER_GALLON + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_FULL_TANK + TYPE_INTEGER + COMMA_SEP +
            COLUMN_NAME_PRIUS_MILES + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRIUS_MPG + TYPE_DOUBLE + COMMA_SEP +
            COLUMN_NAME_PRIUS_SPEED + TYPE_DOUBLE + ")" ;
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ENTRY_TABLE_OLD;

    public GasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES_OLD);
        onUpgrade(db, 1, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Database", "Updating Database from verion " + Integer.toString(oldVersion) + " to version " + Integer.toString(newVersion));
        for(int i = oldVersion - 1; i < newVersion -1; i++) {
            PATCHES[i].applyVersion(db);
        }
    }


    @Override
    public void onOpen(SQLiteDatabase db) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No Downgrades allowed!
    }
}
