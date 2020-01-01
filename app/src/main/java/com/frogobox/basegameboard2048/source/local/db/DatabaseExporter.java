package com.frogobox.basegameboard2048.source.local.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseExporter {

    private final String DEBUG_TAG = "DATABASE_EXPORTER";

    private String DB_PATH;
    private String DB_NAME;

    public DatabaseExporter(String DB_PATH, String DB_NAME) {
        this.DB_PATH = DB_PATH;
        this.DB_NAME = DB_NAME;
    }

    /**
     * Turns a single DB table into a JSON string
     * @return JSON string of the table
     */
    public JSONArray tableToJSON(String TABLE_NAME) {

        SQLiteDatabase dataBase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);


        String searchQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = dataBase.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {

                    try {

                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                    }
                }

            }

            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();

        JSONObject finalJSON = new JSONObject();
        try {
            finalJSON.put(TABLE_NAME, resultSet);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.d(DEBUG_TAG, finalJSON.toString());
        return resultSet;

    }

    /**
     * @return a list of all table names, including android_metadata and sqlite_sequence (table that
     * contains current maximal ID of all tables)
     */
    public ArrayList<String> getTableNames() {

        SQLiteDatabase dataBase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = dataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
        }
        return arrTblNames;
    }

    /**
     *
     * @return Entire DB as JSONObject
     * @throws JSONException
     */
    public JSONObject dbToJSON() throws JSONException {
        ArrayList<String> tables = getTableNames();
        JSONObject listList = new JSONObject();

        for (int i = 0; i < tables.size(); i++) {
            listList.put(tables.get(i), tableToJSON(tables.get(i)));
        }

        JSONObject finalDBJSON = new JSONObject();
        finalDBJSON.put(DB_NAME, listList);

        Log.d(DEBUG_TAG, finalDBJSON.toString());

        return finalDBJSON;
    }


}
