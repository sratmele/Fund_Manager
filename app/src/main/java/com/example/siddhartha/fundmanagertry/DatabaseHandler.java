package com.example.siddhartha.fundmanagertry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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

    public int getNumberOfFunds() {
        int c = 0;
        String selectQuery = "SELECT * FROM "+TABLE_LABELS_FUNDS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            c++;
            while (cursor.moveToNext()){
                c++;
            }
        }
        cursor.close();
        db.close();
        return c;
    }

    public int getNumberOfBills() {
        int c = 0;
        String selectQuery = "SELECT * FROM "+TABLE_LABELS_BILLS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            c++;
            while (cursor.moveToNext()){
                c++;
            }
        }
        cursor.close();
        db.close();
        return c;
    }

    public List<String> getFunds() {
        List<String> funds = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_LABELS_FUNDS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                funds.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return funds;
    }

    public List<String> getFundAmounts() {
        List<String> amounts = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_LABELS_FUNDS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                amounts.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return amounts;
    }

    public List<String> getFundDates() {
        List<String> dates = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_LABELS_FUNDS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                dates.add(cursor.getString(2));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dates;
    }

    public List<String> getBills(String fundName) {
        List<String> bills = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_LABELS_BILLS+" WHERE "+KEY_FUND+" = "+fundName;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                bills.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bills;
    }

    public List<String> getBillAmounts(String fundName) {
        List<String> amounts = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_LABELS_BILLS+" WHERE "+KEY_FUND+" = ?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{fundName});
        if (cursor.moveToFirst()){
            do {
                amounts.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return amounts;
    }

    public List<String> getBillDates(String fundName) {
        List<String> dates = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_LABELS_BILLS+" WHERE "+KEY_FUND+" = "+fundName;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                dates.add(cursor.getString(3));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dates;
    }
}