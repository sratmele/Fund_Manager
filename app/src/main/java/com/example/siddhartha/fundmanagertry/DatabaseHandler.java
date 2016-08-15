package com.example.siddhartha.fundmanagertry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FundManagerDB";

    private static final String TABLE_LABELS_FUNDS = "fundlist";
    private static final String TABLE_LABELS_BILLS = "billslist";

    private static final String KEY_NAME = "name";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DATE = "date";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_FUND = "fund";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_LABELS_FUNDS+"("+KEY_NAME+" TEXT, "+KEY_AMOUNT+" INTEGER, "+KEY_DATE+" DATE)";
        db.execSQL(CREATE_TABLE);
        CREATE_TABLE = "CREATE TABLE "+TABLE_LABELS_BILLS+"("+KEY_NUMBER+" TEXT, "+KEY_AMOUNT+" INTEGER, "+KEY_FUND+" TEXT "+KEY_DATE+" DATE)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LABELS_FUNDS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LABELS_BILLS);
    }

    public boolean insertFund(String name, long amount, String date){
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME,name);
            values.put(KEY_AMOUNT,amount);
            values.put(KEY_DATE,date);
            db.insert(TABLE_LABELS_FUNDS, null, values);
            db.close();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertBill(String number, long amount, String fund, String date){
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NUMBER,number);
            values.put(KEY_AMOUNT,amount);
            values.put(KEY_FUND, fund);
            values.put(KEY_DATE,date);
            db.insert(TABLE_LABELS_BILLS, null, values);
            db.close();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
