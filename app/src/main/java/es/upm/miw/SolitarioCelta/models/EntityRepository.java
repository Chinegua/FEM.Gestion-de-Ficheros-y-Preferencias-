package es.upm.miw.SolitarioCelta.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by chinegua on 19/10/17.
 */

public class EntityRepository extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = PuntuacionesContract.tablaPuntuaciones.TABLE_NAME + ".db";
    public EntityRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + PuntuacionesContract.tablaPuntuaciones.TABLE_NAME + " (" +
                PuntuacionesContract.tablaPuntuaciones._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PuntuacionesContract.tablaPuntuaciones.COL_NAME + " TEXT," +
                PuntuacionesContract.tablaPuntuaciones.COL_DAY + " INTEGER," +
                PuntuacionesContract.tablaPuntuaciones.COL_MONTH + " TEXT," +
                PuntuacionesContract.tablaPuntuaciones.COL_TIME + " TEXT," +
                PuntuacionesContract.tablaPuntuaciones.COL_FICHAS + " TEXT"
                + ")";
        db.execSQL(query);
    }

    public long onSave(String name, int day, String month, String time, int fichas) {
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(PuntuacionesContract.tablaPuntuaciones.COL_NAME, name);
        valores.put(PuntuacionesContract.tablaPuntuaciones.COL_DAY, day);
        valores.put(PuntuacionesContract.tablaPuntuaciones.COL_MONTH, month);
        valores.put(PuntuacionesContract.tablaPuntuaciones.COL_TIME, time);
        valores.put(PuntuacionesContract.tablaPuntuaciones.COL_FICHAS, fichas);


        return db.insert(PuntuacionesContract.tablaPuntuaciones.TABLE_NAME, null, valores);

    }

    public ArrayList<PuntuacionesEntity> onGet() {
        String query = "Select * from " + PuntuacionesContract.tablaPuntuaciones.TABLE_NAME + " ORDER BY "+ PuntuacionesContract.tablaPuntuaciones.COL_FICHAS + " DESC";
        ArrayList<PuntuacionesEntity> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                PuntuacionesEntity cliente = new PuntuacionesEntity(
                        cursor.getString(cursor.getColumnIndex(PuntuacionesContract.tablaPuntuaciones.COL_NAME)),
                        cursor.getInt(cursor.getColumnIndex(PuntuacionesContract.tablaPuntuaciones.COL_DAY)),
                        cursor.getString(cursor.getColumnIndex(PuntuacionesContract.tablaPuntuaciones.COL_MONTH)),
                        cursor.getString(cursor.getColumnIndex(PuntuacionesContract.tablaPuntuaciones.COL_TIME)),
                        cursor.getInt(cursor.getColumnIndex(PuntuacionesContract.tablaPuntuaciones.COL_FICHAS))
                );

                lista.add(cliente);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return lista;

    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long count(){
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db,PuntuacionesContract.tablaPuntuaciones.TABLE_NAME);
    }

    public void onDelete() {
        SQLiteDatabase db = this.getWritableDatabase();;
        Log.i("MiW","LLEGAMOS");
        String query = "DELETE * from "+ PuntuacionesContract.tablaPuntuaciones.TABLE_NAME;
        db.delete(PuntuacionesContract.tablaPuntuaciones.TABLE_NAME, null, null);

    }
}