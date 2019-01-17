package co.example.lutfillahmafazi.notes.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.example.lutfillahmafazi.notes.Constant;
import co.example.lutfillahmafazi.notes.DBNoteHelper;
import co.example.lutfillahmafazi.notes.R;
import co.example.lutfillahmafazi.notes.UpdateNoteActivity;
import co.example.lutfillahmafazi.notes.model.NotesModel;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final Context context;
    private final List<NotesModel> dataNotesList;

    // Bundle untuk menampung data yang banyak menjadi 1
    private Bundle bundle;

    public NotesAdapter(Context context, List<NotesModel> dataNotesList) {
        this.context = context;
        this.dataNotesList = dataNotesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notes, viewGroup, false));
    }

    @Override
    // berguna untuk menghandle semua item
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // Mengambil data dari DataNoteList
        final NotesModel dataNotes = dataNotesList.get(i);

        // Mengambil id judul unruk kita gunakan pada saat delete atau update
        final String id = String.valueOf(dataNotes.getId_());

        // Menampilkan ke widget
        viewHolder.tvJudul.setText(dataNotes.getJudul());
        viewHolder.tvIsi.setText(dataNotes.getIsi());

        // Membuat OnCLick untuk item
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Object Bundle
                bundle = new Bundle();
                bundle.putInt(Constant.KEY_ID, dataNotes.getId_());
                bundle.putString(Constant.KEY_JUDUL,dataNotes.getJudul());
                bundle.putString(Constant.KEY_ISI,dataNotes.getIsi());

                // Berpindah halaman dan membawa data
                context.startActivity(new Intent(context, UpdateNoteActivity.class).putExtras(bundle));
            }
        });

        // Membuat onclick pada overvlow / titik 3
        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                // Inflate design xml untuk popup menu
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                // Membuat object dbHelper
                                DBNoteHelper dbNoteHelper = new DBNoteHelper(v.getContext());
                                // Membuat object SQLiteDatabase dengan mode werite
                                SQLiteDatabase deleteData = dbNoteHelper.getWritableDatabase();
                                // Membuat query untuk mencari id judul
                                String selection = DBNoteHelper.MyColumns.id_judul + " LIKE ?";
                                // Mengambil data ID
                                String[] selectionArgument = {id};
                                // Perintah delete
                                deleteData.delete(DBNoteHelper.MyColumns.namaTabel, selection, selectionArgument);
                                // Menghapus data dari dalam List
                                dataNotesList.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(0,dataNotesList.size());
                                Toast.makeText(context, "Berhasi di Hapus", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataNotesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvJudul)
        TextView tvJudul;
        @BindView(R.id.tvIsi)
        TextView tvIsi;
        @BindView(R.id.overflow)
        ImageButton overflow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
