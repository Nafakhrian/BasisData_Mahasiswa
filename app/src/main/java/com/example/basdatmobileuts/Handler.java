package com.example.basdatmobileuts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

public class Handler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "biodataDB.db";
    private static final String TABLE_NAME = "biodata";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NIM = "_nim";
    public static final String COLUMN_NAMA = "_nama";
    public static final String COLUMN_JK = "_jk";
    public static final String COLUMN_AGAMA = "_agama";
    public static final String COLUMN_ALAMAT = "_alamat";

    public Handler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NIM + " TEXT UNIQUE, "
                + COLUMN_NAMA + " TEXT, "
                + COLUMN_JK + " TEXT, "
                + COLUMN_AGAMA + " TEXT, "
                + COLUMN_ALAMAT + " TEXT "
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> allProducts() {
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> product = new HashMap<>();
            product.put("id", cursor.getString(0));
            product.put("nim", cursor.getString(1));
            product.put("nama", cursor.getString(2));
            product.put("jk", cursor.getString(3));
            product.put("agama", cursor.getString(4));
            product.put("alamat", cursor.getString(5));
            productList.add(product);
        }
        return productList;
    }

    public void addBio(Biodata biodata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NIM, biodata.getNim());
        values.put(COLUMN_NAMA, biodata.getNama());
        values.put(COLUMN_JK, biodata.getJk());
        values.put(COLUMN_AGAMA, biodata.getAgama());
        values.put(COLUMN_ALAMAT, biodata.getAlamat());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean editBio(Biodata biodata) {
        boolean result = false;
        int bioId = biodata.getId();
        String query = "SELECT * FROM " + TABLE_NAME+ " WHERE " + COLUMN_ID + " =\"" + bioId + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NIM, biodata.getNim());
            values.put(COLUMN_NAMA, biodata.getNama());
            values.put(COLUMN_JK, biodata.getJk());
            values.put(COLUMN_AGAMA, biodata.getAgama());
            values.put(COLUMN_ALAMAT, biodata.getAlamat());
            db.update(TABLE_NAME, values, COLUMN_ID + "= ?",
                    new String[]{String.valueOf(biodata.getId())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean deleteBio(String bioNim) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NIM + "=\"" + bioNim + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Biodata biodata = new Biodata();
        if (cursor.moveToFirst()) {
            biodata.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + "= ?",
                    new String[]{String.valueOf(biodata.getId())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
