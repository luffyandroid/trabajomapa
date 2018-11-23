package com.example.fl.trabajomapa;

import android.os.Parcel;
import android.os.Parcelable;

public class ZOferta implements Parcelable {

    String uidempresa;
    String uid;
    String nombre;
    String detalle;
    String salario;
    String tipopuesto;
    String direccion;
    Double latitud;
    Double longitud;
    String telefono;
    String correo;
    String fecha;
    String disponible;

    public static final Parcelable.Creator<ZOferta> CREATOR = new
            Parcelable.Creator<ZOferta>(){
                @Override
                public ZOferta createFromParcel(Parcel in) {
                    return new ZOferta(in);
                }

                @Override
                public ZOferta[] newArray(int size) {
                    return new ZOferta[size];
                }
            };

    public ZOferta(String uidempresa, String uid, String nombre, String detalle, String salario, String tipopuesto, String direccion, Double latitud, Double longitud, String telefono, String correo, String fecha, String disponible) {
        this.uidempresa = uidempresa;
        this.uid = uid;
        this.nombre = nombre;
        this.detalle = detalle;
        this.salario = salario;
        this.tipopuesto = tipopuesto;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.telefono = telefono;
        this.correo = correo;
        this.fecha = fecha;
        this.disponible = disponible;
    }

    public ZOferta(Parcel p){
        readFromParcel(p);
    }

    public ZOferta(){

    }

    public String getUidempresa() {
        return uidempresa;
    }

    public void setUidempresa(String uidempresa) {
        this.uidempresa = uidempresa;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getTipopuesto() {
        return tipopuesto;
    }

    public void setTipopuesto(String tipopuesto) {
        this.tipopuesto = tipopuesto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.uidempresa);
        dest.writeString(this.uid);
        dest.writeString(this.nombre);
        dest.writeString(this.detalle);
        dest.writeString(this.salario);
        dest.writeString(this.tipopuesto);
        dest.writeString(this.direccion);
        dest.writeDouble(this.latitud);
        dest.writeDouble(this.longitud);
        dest.writeString(this.telefono);
        dest.writeString(this.correo);
        dest.writeString(this.fecha);
        dest.writeString(this.disponible);
    }

    private void readFromParcel(Parcel p){
        this.uidempresa = p.readString();
        this.uid = p.readString();
        this.nombre = p.readString();
        this.detalle = p.readString();
        this.salario = p.readString();
        this.tipopuesto = p.readString();
        this.direccion = p.readString();
        this.latitud = p.readDouble();
        this.longitud = p.readDouble();
        this.telefono = p.readString();
        this.correo = p.readString();
        this.fecha = p.readString();
        this.disponible = p.readString();
    }

}
