package es.upm.miw.SolitarioCelta.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PuntuacionesEntity implements Parcelable {

    private int id;
    private String name;
    private int day;
    private String month;
    private String time;
    private int numeroFichas;

    public PuntuacionesEntity(String name, int day, String month, String time, int numeroFichas) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.time = time;
        this.numeroFichas = numeroFichas;
    }

    protected PuntuacionesEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        day = in.readInt();
        month = in.readString();
        time = in.readString();
        numeroFichas = in.readInt();
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(day);
        dest.writeString(month);
        dest.writeInt(numeroFichas);
        dest.writeString(time);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PuntuacionesEntity> CREATOR = new Parcelable.Creator<PuntuacionesEntity>() {
        @Override
        public PuntuacionesEntity createFromParcel(Parcel in) {
            return new PuntuacionesEntity(in);
        }

        @Override
        public PuntuacionesEntity[] newArray(int size) {
            return new PuntuacionesEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumeroFichas() {
        return numeroFichas;
    }

    public void setNumeroFichas(int numeroFichas) {
        this.numeroFichas = numeroFichas;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}