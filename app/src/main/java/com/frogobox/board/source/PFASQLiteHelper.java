package com.frogobox.board.source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.frogobox.board.BuildConfig;
import com.frogobox.board.util.SingleConst;

import java.util.ArrayList;
import java.util.List;

public class PFASQLiteHelper extends SQLiteOpenHelper {


    private static final String SQLITE_DATABASE_NAME = BuildConfig.APPLICATION_ID.replace(".", "_") + SingleConst.SQLiteDatabase.SQLITE_ATTRIBUTE;

    public PFASQLiteHelper(Context context) {
        super(context, SQLITE_DATABASE_NAME, null, SingleConst.SQLiteDatabase.SQLITE_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /*
         * Create the table sample data on the first start
         * Be careful with the final line of the query and the SQL syntax that is used in the String.
         */
        String CREATE_SAMPLEDATA_TABLE = "CREATE TABLE " + SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA +
                "(" +
                SingleConst.SQLiteDatabase.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SingleConst.SQLiteDatabase.KEY_DOMAIN + " TEXT NOT NULL," +
                SingleConst.SQLiteDatabase.KEY_USERNAME + " TEXT NOT NULL," +
                SingleConst.SQLiteDatabase.KEY_LENGTH + " INTEGER);";

        sqLiteDatabase.execSQL(CREATE_SAMPLEDATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA);

        onCreate(sqLiteDatabase);
    }


    /**
     * Adds a single sampleData to our Table
     * As no ID is provided and KEY_ID is autoincremented (see line 50)
     * the last available key of the table is taken and incremented by 1
     *
     * @param sampleData data that will be added
     */
    public void addSampleData(PFADataType sampleData) {
        SQLiteDatabase database = this.getWritableDatabase();

        //To adjust this class for your own data, please add your values here.
        ContentValues values = new ContentValues();
        values.put(SingleConst.SQLiteDatabase.KEY_DOMAIN, sampleData.getDOMAIN());
        values.put(SingleConst.SQLiteDatabase.KEY_USERNAME, sampleData.getUSERNAME());
        values.put(SingleConst.SQLiteDatabase.KEY_LENGTH, sampleData.getLENGTH());

        database.insert(SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA, null, values);
        database.close();
    }

    /**
     * Adds a single sampleData to our Table
     * This method can be used for re-insertion for example an undo-action
     * Therefore, the key of the sampleData will also be written into the database
     *
     * @param sampleData data that will be added
     *                   Only use this for undo options and re-insertions
     */
    public void addSampleDataWithID(PFADataType sampleData) {
        SQLiteDatabase database = this.getWritableDatabase();

        //To adjust this class for your own data, please add your values here.
        ContentValues values = new ContentValues();
        values.put(SingleConst.SQLiteDatabase.KEY_ID, sampleData.getID());
        values.put(SingleConst.SQLiteDatabase.KEY_DOMAIN, sampleData.getDOMAIN());
        values.put(SingleConst.SQLiteDatabase.KEY_USERNAME, sampleData.getUSERNAME());
        values.put(SingleConst.SQLiteDatabase.KEY_LENGTH, sampleData.getLENGTH());

        database.insert(SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA, null, values);

        //always close the database after insertion
        database.close();
    }


    /**
     * This method gets a single sampleData entry based on its ID
     *
     * @param id of the sampleData that is requested, could be get by the get-method
     * @return the sampleData that is requested.
     */
    public PFADataType getSampleData(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        Log.d("DATABASE", Integer.toString(id));

        Cursor cursor = database.query(SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA, new String[]{SingleConst.SQLiteDatabase.KEY_ID,
                        SingleConst.SQLiteDatabase.KEY_DOMAIN, SingleConst.SQLiteDatabase.KEY_USERNAME, SingleConst.SQLiteDatabase.KEY_LENGTH}, SingleConst.SQLiteDatabase.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        PFADataType sampleData = new PFADataType();

        if (cursor != null && cursor.moveToFirst()) {
            sampleData.setID(Integer.parseInt(cursor.getString(0)));
            sampleData.setDOMAIN(cursor.getString(1));
            sampleData.setUSERNAME(cursor.getString(2));
            sampleData.setLENGTH(Integer.parseInt(cursor.getString(3)));

            Log.d("DATABASE", "Read " + cursor.getString(1) + " from DB");

            cursor.close();
        }

        return sampleData;

    }

    /**
     * This method returns all data from the DB as a list
     * This could be used for instance to fill a recyclerView
     *
     * @return A list of all available sampleData in the Database
     */
    public List<PFADataType> getAllSampleData() {
        List<PFADataType> sampleDataList = new ArrayList<PFADataType>();

        String selectQuery = "SELECT  * FROM " + SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        PFADataType sampleData = null;

        if (cursor.moveToFirst()) {
            do {
                //To adjust this class for your own data, please add your values here.
                //be careful to use the right get-method to get the data from the cursor
                sampleData = new PFADataType();
                sampleData.setID(Integer.parseInt(cursor.getString(0)));
                sampleData.setDOMAIN(cursor.getString(1));
                sampleData.setUSERNAME(cursor.getString(2));
                sampleData.setLENGTH(Integer.parseInt(cursor.getString(3)));

                sampleDataList.add(sampleData);
            } while (cursor.moveToNext());
        }

        return sampleDataList;
    }

    /**
     * Updates a database entry.
     *
     * @param sampleData
     * @return actually makes the update
     */
    public int updateSampleData(PFADataType sampleData) {
        SQLiteDatabase database = this.getWritableDatabase();

        //To adjust this class for your own data, please add your values here.
        ContentValues values = new ContentValues();
        values.put(SingleConst.SQLiteDatabase.KEY_DOMAIN, sampleData.getDOMAIN());
        values.put(SingleConst.SQLiteDatabase.KEY_USERNAME, sampleData.getUSERNAME());
        values.put(SingleConst.SQLiteDatabase.KEY_LENGTH, sampleData.getLENGTH());

        return database.update(SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA, values, SingleConst.SQLiteDatabase.KEY_ID + " = ?",
                new String[]{String.valueOf(sampleData.getID())});
    }

    /**
     * Deletes sampleData from the DB
     * This method takes the sampleData and extracts its key to build the delete-query
     *
     * @param sampleData that will be deleted
     */
    public void deleteSampleData(PFADataType sampleData) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA, SingleConst.SQLiteDatabase.KEY_ID + " = ?",
                new String[]{Integer.toString(sampleData.getID())});
        //always close the DB after deletion of single entries
        database.close();
    }

    /**
     * deletes all sampleData from the table.
     * This could be used in case of a reset of the app.
     */
    public void deleteAllSampleData() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + SingleConst.SQLiteDatabase.TABLE_SAMPLEDATA);
    }

}
