package com.impulsecontrol.mpgmaterial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hummel on 7/1/16.
 */
public class GasDAO {

    Map<Integer, Long> dbMap = new HashMap<>();

    GasDbHelper dbHelper;
    public GasDAO(Context context) {
        dbHelper = new GasDbHelper(context);
    }

    public Boolean addNode(GasNode g) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        values.put(GasDbHelper.COLUMN_NAME_MILEAGE, g.mileage);
        values.put(GasDbHelper.COLUMN_NAME_DATE, dateFormat.format(g.date));
        values.put(GasDbHelper.COLUMN_NAME_FULL_TANK, g.full_tank);
        values.put(GasDbHelper.COLUMN_NAME_GALLONS, g.gallons);
        values.put(GasDbHelper.COLUMN_NAME_PRICE_PER_GALLON, g.price_per_gallon);
        values.put(GasDbHelper.COLUMN_NAME_PRIUS_MILES, g.prius_milage);
        values.put(GasDbHelper.COLUMN_NAME_PRIUS_MPG, g.prius_mpg);
        values.put(GasDbHelper.COLUMN_NAME_PRIUS_SPEED, g.prius_ave_speed);

        long newRowID;
        newRowID = db.insert(GasDbHelper.TABLE_NAME,
                null,
                values);
        dbMap.put(g.mileage, newRowID);
        return true;
    }

    public Boolean deleteGasNode(GasNode g) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(GasDbHelper.TABLE_NAME, GasDbHelper.COLUMN_NAME_MILEAGE + "=" + Integer.toString(g.mileage), null);
        return true;
    }

    public List<GasNode> getNodes() {
        List<GasNode> nodes = new ArrayList<>();
        Log.d("TAG", "Getting Readable DataBase");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Log.d("TAG", "Got IT!");

        String[] columns = {
                GasDbHelper.COLUMN_NAME_MILEAGE,
                GasDbHelper.COLUMN_NAME_DATE,
                GasDbHelper.COLUMN_NAME_FULL_TANK,
                GasDbHelper.COLUMN_NAME_GALLONS,
                GasDbHelper.COLUMN_NAME_PRICE_PER_GALLON,
                GasDbHelper.COLUMN_NAME_PRIUS_MILES,
                GasDbHelper.COLUMN_NAME_PRIUS_MPG,
                GasDbHelper.COLUMN_NAME_PRIUS_SPEED
        };

        String sortOrder = GasDbHelper.COLUMN_NAME_MILEAGE + " DESC";

        Log.d("TAG", "QUery GO!");

        Cursor c = db.query(GasDbHelper.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        Log.d("TAG", "Query Done");

        Log.d("TAG", "Pulling");

        if(c.moveToNext()) {
            Log.d("TAG", "Has Next");

            while(c.isAfterLast() == false) {
                Log.d("TAG", "Found");

                GasNode g = new GasNode();
                g.mileage = c.getInt(c.getColumnIndex(GasDbHelper.COLUMN_NAME_MILEAGE));
                g.price_per_gallon = c.getDouble(c.getColumnIndex(GasDbHelper.COLUMN_NAME_PRICE_PER_GALLON));
                g.gallons = c.getDouble(c.getColumnIndex(GasDbHelper.COLUMN_NAME_GALLONS));
                g.full_tank = (c.getInt(c.getColumnIndex(GasDbHelper.COLUMN_NAME_FULL_TANK))==1)?true:false;
                String date = c.getString(c.getColumnIndex(GasDbHelper.COLUMN_NAME_DATE));
                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    g.date = formatter.parse(date);
                } catch (ParseException e) {

                }
                g.prius_ave_speed = c.getDouble(c.getColumnIndex(GasDbHelper.COLUMN_NAME_PRIUS_SPEED));
                g.prius_milage = c.getDouble(c.getColumnIndex(GasDbHelper.COLUMN_NAME_MILEAGE));
                g.prius_mpg = c.getDouble(c.getColumnIndex(GasDbHelper.COLUMN_NAME_PRIUS_MPG));
                nodes.add(g);
                long rowID = c.getLong(c.getColumnIndex(GasDbHelper.COLUMN_NAME_MILEAGE));
                dbMap.put(g.mileage, rowID);
                c.moveToNext();
            }
        }
        return nodes;
    }

    public void addNodes(List<GasNode> nodes) {
        for( GasNode g : nodes) {
            addNode(g);
        }
    }
}
