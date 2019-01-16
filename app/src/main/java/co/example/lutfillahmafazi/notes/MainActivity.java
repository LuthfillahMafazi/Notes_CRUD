package co.example.lutfillahmafazi.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.example.lutfillahmafazi.notes.adapter.NotesAdapter;
import co.example.lutfillahmafazi.notes.model.NotesModel;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvMain)
    RecyclerView rvMain;
    @BindView(R.id.fabmain)
    FloatingActionButton fabmain;

    // Variable untuk dbHelper
    private DBNoteHelper dbNoteHelper;
    private List<NotesModel> dataNotesList;
    // Membuat variable adapter
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Membuat Object dbHelper
        dbNoteHelper = new DBNoteHelper(this);

        // Kita inisiasikan variable list
        dataNotesList = new ArrayList<>();

        //Kita ambil data dari SQLite
        getData();
        // Membuat object adapter
        notesAdapter = new NotesAdapter(this, dataNotesList);
        // Setting layout manager
        rvMain.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // Memasang Adapter ke RecycleView
        rvMain.setAdapter(notesAdapter);

    }

    private void getData() {
        // Kita membuat object SQLoteDataBase dengan mode Read
        SQLiteDatabase readData = dbNoteHelper.getReadableDatabase();

        // Membuat perintah mengambil data
        String query = "SELECT * FROM " + DBNoteHelper.MyColumns.namaTabel + " ORDER BY "
                + DBNoteHelper.MyColumns.id_judul + " DESC";

        // Kita akan mengambil data dari cursor
        Cursor cursor = readData.rawQuery(query, null);

        // Kita arahkan cursor ke awal
        cursor.moveToFirst();

        // Mengambil data secara berulang
        for (int count = 0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);
            dataNotesList.add(new NotesModel(cursor.getInt(0), cursor.getString(1)
                    ,cursor.getString(2)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataNotesList.clear();
        getData();
        // untuk memberi tahu adapter ada data yang berubah
        notesAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fabmain)
    public void onViewClicked() {
        // Untuk berpindah activity
        startActivity(new Intent(this,TambahNoteActivity.class));
    }
}
