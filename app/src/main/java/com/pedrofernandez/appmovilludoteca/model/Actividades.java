package com.pedrofernandez.appmovilludoteca.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Actividades implements Parcelable, Serializable {

    private int id;
    private String hora;
    private String fecha;
    private String nombreTipo;
    private int tipo;
    private String nombre;
    private String apellidos;
    private String sala;
    private int precio;

    public Actividades() {
    }

    public Actividades(int id, String hora, String fecha, String nombreTipo, int tipo, String nombre, String apellidos, String sala) {
        this.id = id;
        this.hora = hora;
        this.fecha = fecha;
        this.nombreTipo = nombreTipo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sala = sala;
    }

    public Actividades(int id, String hora, String fecha, String nombreTipo, int tipo, String nombre, String apellidos, String sala, int precio) {
        this.id = id;
        this.hora = hora;
        this.fecha = fecha;
        this.nombreTipo = nombreTipo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sala = sala;
        this.precio = precio;
    }

    protected Actividades(Parcel in) {
        id = in.readInt();
        hora = in.readString();
        fecha = in.readString();
        nombreTipo = in.readString();
        tipo = in.readInt();
        nombre = in.readString();
        apellidos = in.readString();
        sala = in.readString();
        precio = in.readInt();
    }

    public static final Creator<Actividades> CREATOR = new Creator<Actividades>() {
        @Override
        public Actividades createFromParcel(Parcel in) {
            return new Actividades(in);
        }

        @Override
        public Actividades[] newArray(int size) {
            return new Actividades[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(hora);
        parcel.writeString(fecha);
        parcel.writeString(nombreTipo);
        parcel.writeInt(tipo);
        parcel.writeString(nombre);
        parcel.writeString(apellidos);
        parcel.writeString(sala);
        parcel.writeInt(precio);
    }
}
