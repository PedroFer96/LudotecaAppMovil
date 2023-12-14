package com.pedrofernandez.appmovilludoteca.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ludoteca implements Parcelable, Serializable {

    private int id;
    private String nombre;
    private String direccion;
    private double latitud;
    private double longitud;
    private String telefono;
    private String fechaCreacion;
    private String nombrePropietario;
    private String apellidosPropietario;


    public Ludoteca() {
    }

    public Ludoteca(int id, String nombre, String direccion, double latitud, double longitud, String telefono, String fechaCreacion, String nombrePropietario, String apellidosPropietario) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.nombrePropietario = nombrePropietario;
        this.apellidosPropietario = apellidosPropietario;
    }

    protected Ludoteca(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        direccion = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
        telefono = in.readString();
        fechaCreacion = in.readString();
        nombrePropietario = in.readString();
        apellidosPropietario = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(direccion);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeString(telefono);
        dest.writeString(fechaCreacion);
        dest.writeString(nombrePropietario);
        dest.writeString(apellidosPropietario);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ludoteca> CREATOR = new Creator<Ludoteca>() {
        @Override
        public Ludoteca createFromParcel(Parcel in) {
            return new Ludoteca(in);
        }

        @Override
        public Ludoteca[] newArray(int size) {
            return new Ludoteca[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public String getApellidosPropietario() {
        return apellidosPropietario;
    }

    public void setApellidosPropietario(String apellidosPropietario) {
        this.apellidosPropietario = apellidosPropietario;
    }
}




