package co.example.lutfillahmafazi.notes.model;

import android.content.Intent;

public class NotesModel {
    private Integer id_;
    private String judul;
    private String isi;

    // Construktor untuk kita dapat menerima data dari class lain
    public NotesModel(Integer id_, String judul, String isi) {
        this.id_ = id_;
        this.judul = judul;
        this.isi = isi;
    }

    // Getter untuk kita dapat mengambil data
    public Integer getId_() {
        return id_;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }
}
