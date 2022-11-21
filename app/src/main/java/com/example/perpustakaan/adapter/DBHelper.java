package com.example.perpustakaan.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import javax.sql.StatementEvent;

public class DBHelper extends SQLiteOpenHelper {
    public static final String database_name = "db_perpus";
    public static final String tabel_nama = "tabel_perpus";

    public static final String row_id = "id";
    public static final String row_nama = "Nama";
    public static final String row_judul = "Judul";
    public static final String row_pinjam = "TglPinjam";
    public static final String row_kembali = "Kembali";
    public static final String row_status = "Status";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    // Buat query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + tabel_nama + " ( "
                + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + row_nama + " TEXT, "
                + row_judul + " TEXT, "
                + row_pinjam + " TEXT, "
                + row_kembali + " TEXT, "
                + row_status + " TEXT) ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS '" + tabel_nama + "'");
    }

    //Menampilkan semua data SQLite
    public Cursor AllData(){
        Cursor cur = db.rawQuery(" SELECT * FROM " + tabel_nama + " ORDER BY " + row_id + " DESC ", null);
        return cur;
    }

    // Menampilkan 1 data berdasarkan id
    public Cursor oneData(long id){
        Cursor cur = db.rawQuery(" SELECT * FROM " + tabel_nama + " WHERE " + row_id + " = " + id, null);
        return cur;
    }

    // Insert Data
    public void insertData(ContentValues values){
        db.insert(tabel_nama, null, values);
    }

    // Update Data
    public void updateData(ContentValues values, long id){
        db.update(tabel_nama, values, row_id + " = " + id, null);
    }

    // Delete Data
    public void deleteData(long id){
        db.delete(tabel_nama, row_id + " = " + id, null);
    }
}
