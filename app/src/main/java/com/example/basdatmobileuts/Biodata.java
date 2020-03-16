package com.example.basdatmobileuts;

public class Biodata {
    private int id;
    private String nim, nama, jk, agama, alamat;

    public Biodata() {
    }

    public Biodata(String nim, String nama, String jk, String agama, String alamat) {
        this.nim = nim;
        this.nama = nama;
        this.jk = jk;
        this.agama = agama;
        this.alamat = alamat;
    }

    public Biodata(int id, String nim, String nama, String jk, String agama, String alamat) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.jk = jk;
        this.agama = agama;
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
