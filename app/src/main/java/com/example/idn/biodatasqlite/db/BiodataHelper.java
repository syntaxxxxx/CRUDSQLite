package com.example.idn.biodatasqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.idn.biodatasqlite.entity.Biodata;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.idn.biodatasqlite.db.BiodataContract.BiodataColumn.COLUMN_GENDER;
import static com.example.idn.biodatasqlite.db.BiodataContract.BiodataColumn.COLUMN_NAMA;
import static com.example.idn.biodatasqlite.db.BiodataContract.BiodataColumn.COLUMN_UMUR;
import static com.example.idn.biodatasqlite.db.BiodataContract.BiodataColumn.COLUMN_WEIGHT;
import static com.example.idn.biodatasqlite.db.BiodataContract.BiodataColumn.TANGGAL;
import static com.example.idn.biodatasqlite.db.BiodataContract.TABLE_NAME;

public class BiodataHelper {

    private static String DATABASE_TABEL = TABLE_NAME;
    private BiodataDbHelper biodataDbHelper;
    private SQLiteDatabase database;
    private Context context;

    public BiodataHelper(Context context) {
        this.context = context;
    }


    // method untuk membuat dan membaca data database
    public BiodataHelper open() throws SQLException {
        biodataDbHelper = new BiodataDbHelper(context);
        database = biodataDbHelper.getWritableDatabase();
        return this;
    }


    // jika sudah dibuat close database nya
    public void close() {
        biodataDbHelper.close();
    }


    // pada proses load data dilakukan dengan eksekusi query()
    public ArrayList<Biodata> query() {
        ArrayList<Biodata> arrayList = new ArrayList<Biodata>();

        // cursor digunakan ambil data hasil dari query
        // lalu ditampung dalam cursor
        Cursor cursor = database.query(DATABASE_TABEL,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();

        Biodata biodata;
        if (cursor.getCount() > 0) {

            // looping untuk mengakses data satu persatu didalam cursor
            do {

                biodata = new Biodata();
                biodata.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                biodata.setNama(cursor.getString(cursor.getColumnIndexOrThrow(
                        COLUMN_NAMA)));
                biodata.setUmur(cursor.getString(cursor.getColumnIndexOrThrow(
                        COLUMN_UMUR)));
                biodata.setGender(cursor.getString(cursor.getColumnIndexOrThrow(
                        COLUMN_GENDER)));
                biodata.setWeight(cursor.getString(cursor.getColumnIndexOrThrow(
                        COLUMN_WEIGHT)));
                biodata.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(
                        TANGGAL)));

                // data yang sudah diakses tampung ke arraylist dan tambahkan
                // lalu ditampung kedalam class model
                arrayList.add(biodata);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        // tutup setelah mengakses data
        cursor.close();

        // kembalian data nya ke dalam bentuk arraylsit
        return arrayList;

    }


    // insert data
    public long insert(Biodata biodata) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAMA,
                biodata.getNama());
        values.put(COLUMN_UMUR,
                biodata.getUmur());
        values.put(COLUMN_GENDER,
                biodata.getGender());
        values.put(COLUMN_WEIGHT,
                biodata.getWeight());
        values.put(TANGGAL,
                biodata.getTanggal());

        return database.insert(DATABASE_TABEL, null, values);
    }

    //update data
    public long update(Biodata biodata) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA,
                biodata.getNama());
        values.put(COLUMN_UMUR,
                biodata.getUmur());
        values.put(COLUMN_GENDER,
                biodata.getUmur());
        values.put(COLUMN_WEIGHT,
                biodata.getWeight());
        values.put(TANGGAL,
                biodata.getTanggal());

        return database.update(
                DATABASE_TABEL, values,
                _ID
                        + " = '"
                        + biodata.getId()
                        + "'", null);

    }


    // delete data
    public int delete(int id) {
        return database.delete(
                TABLE_NAME, _ID
                        + " = '" + id
                        + "'", null);

    }
}
