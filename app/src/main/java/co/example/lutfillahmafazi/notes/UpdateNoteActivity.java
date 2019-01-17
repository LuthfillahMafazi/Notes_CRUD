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

public class UpdateNoteActivity extends AppCompatActivity {

    @BindView(R.id.edtJudul)
    EditText edtJudul;
    @BindView(R.id.edtIsi)
    EditText edtIsi;
    @BindView(R.id.btnSave)
    Button btnSave;

    // TODO 2 Membuat variable
    private Bundle bundle;
    private String judul, isi;
    private int id_judul;

    private DBNoteHelper dbNoteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        ButterKnife.bind(this);

        // TODO 3
        setTitle("Update Note");

        // Membuat Object dbHelper
        dbNoteHelper = new DBNoteHelper(this);
        // Menangkap data bundle
        bundle = getIntent().getExtras();
        // Pengecekan bundle
        if (bundle != null){
            showData();
        }

    }

    private void showData() {
        // Mengambil data id yang berada di dalam bundle
        id_judul = bundle.getInt(Constant.KEY_ID);
        judul = bundle.getString(Constant.KEY_JUDUL);
        isi = bundle.getString(Constant.KEY_ISI);

        // Menampilkan data ke layar
        edtJudul.setText(judul);
        edtIsi.setText(isi);
    }

    // TODO 1 Butterknife
    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        // TODO 4 Menyimpan data update ke SQLite
        // Mengambil data dari EditText
        getData();

        // Mengupdate data
        UpdateData();
        // Menghilangkan data text
        clearData();

        // FININSH
        finish();
    }

    private void clearData() {
        edtIsi.setText("");
        edtJudul.setText("");
    }

    private void UpdateData() {
        // Bikin object SQLiteDataBase untuk kita dapat menggunakan operasi SQLite, Mode read untuk update
        SQLiteDatabase database = dbNoteHelper.getReadableDatabase();

        // Menampung data ke dalam content values
        ContentValues values = new ContentValues();
        // Mengisi data ke Content values
        values.put(DBNoteHelper.MyColumns.judul,judul);
        values.put(DBNoteHelper.MyColumns.isi,isi);

        // Membuat query untuk pencarian data berdasarkan ID Judul
        String selection = DBNoteHelper.MyColumns.id_judul + " LIKE ? ";
        // Menampung id yang di targetkan
        String[] selectionArgs = {String.valueOf(id_judul)};
        // Melakukan operasi Update
        database.update(DBNoteHelper.MyColumns.namaTabel,values, selection, selectionArgs);
        Toast.makeText(this, "Berhasil di Update", Toast.LENGTH_SHORT).show();

    }

    private void getData() {
        judul = edtJudul.getText().toString();
        isi = edtIsi.getText().toString();
    }
}
