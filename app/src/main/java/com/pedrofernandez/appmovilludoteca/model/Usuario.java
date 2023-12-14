package com.pedrofernandez.appmovilludoteca.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Usuario implements Parcelable {

    private int id;
    private String nombreC;
    private String apellidosC;
    private String fechaNacimiento;
    private String dniC;
    private String phoneC;
    private String correoC;
    private String userC;
    private String passwordC;
    private int estado;
    private int idTutor;
    private String nombreT;
    private String apellidosT;
    private String fechaNacimientoTutor;
    private String dniT;
    private int phoneT;
    private int codSeguridad;

    public Usuario() {
    }


    public Usuario(int id, String nombreC, String apellidosC, String fechaNacimiento, String dniC, String phoneC, String correoC, String userC, String passwordC, int estado, int idTutor, String nombreT, String apellidosT, String fechaNacimientoTutor, String dniT, int phoneT, int codSeguridad) {
        this.id = id;
        this.nombreC = nombreC;
        this.apellidosC = apellidosC;
        this.fechaNacimiento = fechaNacimiento;
        this.dniC = dniC;
        this.phoneC = phoneC;
        this.correoC = correoC;
        this.userC = userC;
        this.passwordC = passwordC;
        this.estado = estado;
        this.idTutor = idTutor;
        this.nombreT = nombreT;
        this.apellidosT = apellidosT;
        this.fechaNacimientoTutor = fechaNacimientoTutor;
        this.dniT = dniT;
        this.phoneT = phoneT;
        this.codSeguridad = codSeguridad;
    }


    protected Usuario(Parcel in) {
        id = in.readInt();
        nombreC = in.readString();
        apellidosC = in.readString();
        fechaNacimiento = in.readString();
        dniC = in.readString();
        phoneC = in.readString();
        correoC = in.readString();
        userC = in.readString();
        passwordC = in.readString();
        estado = in.readInt();
        idTutor = in.readInt();
        nombreT = in.readString();
        apellidosT = in.readString();
        fechaNacimientoTutor = in.readString();
        dniT = in.readString();
        phoneT = in.readInt();
        codSeguridad = in.readInt();
    }



    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getApellidosC() {
        return apellidosC;
    }

    public void setApellidosC(String apellidosC) {
        this.apellidosC = apellidosC;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDniC() {
        return dniC;
    }

    public void setDniC(String dniC) {
        this.dniC = dniC;
    }

    public String getPhoneC() {
        return phoneC;
    }

    public void setPhoneC(String phoneC) {
        this.phoneC = phoneC;
    }

    public String getCorreoC() {
        return correoC;
    }

    public void setCorreoC(String correoC) {
        this.correoC = correoC;
    }

    public String getUserC() {
        return userC;
    }

    public void setUserC(String userC) {
        this.userC = userC;
    }

    public String getPasswordC() {
        return passwordC;
    }

    public void setPasswordC(String passwordC) {
        this.passwordC = passwordC;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }

    public String getNombreT() {
        return nombreT;
    }

    public void setNombreT(String nombreT) {
        this.nombreT = nombreT;
    }

    public String getApellidosT() {
        return apellidosT;
    }

    public void setApellidosT(String apellidosT) {
        this.apellidosT = apellidosT;
    }

    public String getFechaNacimientoTutor() {
        return fechaNacimientoTutor;
    }

    public void setFechaNacimientoTutor(String fechaNacimientoTutor) {
        this.fechaNacimientoTutor = fechaNacimientoTutor;
    }

    public String getDniT() {
        return dniT;
    }

    public void setDniT(String dniT) {
        this.dniT = dniT;
    }

    public int getPhoneT() {
        return phoneT;
    }

    public void setPhoneT(int phoneT) {
        this.phoneT = phoneT;
    }

    public int getCodSeguridad() {
        return codSeguridad;
    }

    public void setCodSeguridad(int codSeguridad) {
        this.codSeguridad = codSeguridad;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombreC);
        parcel.writeString(apellidosC);
        parcel.writeString(fechaNacimiento);
        parcel.writeString(dniC);
        parcel.writeString(phoneC);
        parcel.writeString(correoC);
        parcel.writeString(userC);
        parcel.writeString(passwordC);
        parcel.writeInt(estado);
        parcel.writeInt(idTutor);
        parcel.writeString(nombreT);
        parcel.writeString(apellidosT);
        parcel.writeString(fechaNacimientoTutor);
        parcel.writeString(dniT);
        parcel.writeInt(phoneT);
        parcel.writeInt(codSeguridad);
    }
}
