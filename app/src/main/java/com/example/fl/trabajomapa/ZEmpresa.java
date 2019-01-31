package com.example.fl.trabajomapa;

import android.os.Parcel;
import android.os.Parcelable;

public class ZEmpresa implements Parcelable{

    String uid;
    String email;
    String nombre;

    public static final Parcelable.Creator<ZEmpresa> CREATOR = new
            Parcelable.Creator<ZEmpresa>(){
                @Override
                public ZEmpresa createFromParcel(Parcel in) {
                    return new ZEmpresa(in);
                }

                @Override
                public ZEmpresa[] newArray(int size) {
                    return new ZEmpresa[size];
                }
            };

    public ZEmpresa(String uid, String email, String nombre) {
        this.uid = uid;
        this.email = email;
        this.nombre = nombre;
    }

    public ZEmpresa(Parcel p){
        readFromParcel(p);
    }

    public ZEmpresa(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.uid);
        dest.writeString(this.email);
        dest.writeString(this.nombre);

    }

    private void readFromParcel(Parcel p){
        this.uid = p.readString();
        this.email = p.readString();
        this.nombre = p.readString();

    }

}
