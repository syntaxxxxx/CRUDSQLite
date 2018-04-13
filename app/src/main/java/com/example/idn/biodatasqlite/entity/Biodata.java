package com.example.idn.biodatasqlite.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Biodata implements Parcelable {

    String nama, gender, umur, weight, tanggal;
    int id;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Biodata() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeString(this.gender);
        dest.writeString(this.umur);
        dest.writeString(this.weight);
        dest.writeString(this.tanggal);
        dest.writeInt(this.id);
    }

    protected Biodata(Parcel in) {
        this.nama = in.readString();
        this.gender = in.readString();
        this.umur = in.readString();
        this.weight = in.readString();
        this.tanggal = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<Biodata> CREATOR = new Creator<Biodata>() {
        @Override
        public Biodata createFromParcel(Parcel source) {
            return new Biodata(source);
        }

        @Override
        public Biodata[] newArray(int size) {
            return new Biodata[size];
        }
    };
}

