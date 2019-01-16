package co.example.lutfillahmafazi.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBNoteHelper extends SQLiteOpenHelper {

    //TODO 4 membuat variable constand nama table dan nama colom yang diinginkan
    // fungsi static agar dapat diakses dengan class lain tanpa meminta
    public static abstract class MyColumns implements BaseColumns{
        public static final String namaTabel = "Notes";
        public static final String id_judul = "ID_Judul";
        public static final String judul = "Judul";
        public static final String isi = "Isi";
    }

    // TODO 2 Membuat variable namaDb dan versi
    private static final String namaDatabase = "catatan.db";
    private static final int versiDatabase = 1;

    // TODO 3 Membuat contruktor DBHelper
    // Contruktor pasti memiliki nama yang sama dengan class
    public DBNoteHelper(Context context){
        super(context, namaDatabase, null, versiDatabase);
    }

    // TODO 1 Implement method dari SqLiteHelper
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 6 Menjalankan perintah untuk membuat table
        db.execSQL(SQL_CREATE_TABLE);
    }

    // TODO 5 Perintah untuk membuat table
    // "CREATE TABLE NOTES (ID Judul INTEGER PRIMARY KEY AUTOINCREMENT, Judul TEXT NOT NULL, Isi TEXT NOT NULL)"
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + MyColumns.namaTabel + "(" + MyColumns.id_judul +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + MyColumns.judul + " TEXT NOT NULL, " + MyColumns.isi + " TEXT NOT NULL)";

    //TODO 7 Perintah untuk menghapus table
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + MyColumns.namaTabel;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
