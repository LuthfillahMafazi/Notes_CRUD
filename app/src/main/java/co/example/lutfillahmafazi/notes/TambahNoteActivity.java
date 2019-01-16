package co.example.lutfillahmafazi.notes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahNoteActivity extends AppCompatActivity {

    @BindView(R.id.edtJudul)
    EditText edtJudul;
    @BindView(R.id.edtIsi)
    EditText edtIsi;
    @BindView(R.id.btnSave)
    Button btnSave;

    // Buat variable untuk database
    private DBNoteHelper dbNoteHelper;
    private String getJudul, getIsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_note);
        ButterKnife.bind(this);

        // Untuk memngganti judul action bar
        setTitle("Add Data");

        // Buat objek untuk memanggil dbHelper
        dbNoteHelper = new DBNoteHelper(this);

    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        getData();
        savedata();
    }

    private void savedata() {
        // Membuat object SQLiteDatabase dengan mode menulis
        SQLiteDatabase create = dbNoteHelper.getWritableDatabase();

        // kita tampung data dari user ke dalam Content values agar meringkas
        ContentValues values = new ContentValues();
        values.put(DBNoteHelper.MyColumns.judul, getJudul);
        values.put(DBNoteHelper.MyColumns.isi, getIsi);

        // Kita tambahkan data baru ke dalam table
        create.insert(DBNoteHelper.MyColumns.namaTabel,null, values);
        Toast.makeText(this, "Tersimpan", Toast.LENGTH_SHORT).show();

        // Menghapus isian dari user pada edittext agar terlihat sudah tersimpan
        clearData();
        finish();
    }

    private void clearData() {
        edtIsi.setText("");
        edtJudul.setText("");
    }

    private void getData() {
        // Mengambil data dari inputan user
        getJudul = edtJudul.getText().toString();
        getIsi = edtIsi.getText().toString();
    }
}
