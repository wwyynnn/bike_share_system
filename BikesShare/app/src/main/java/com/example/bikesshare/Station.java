package com.example.bikesshare;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Station implements java.io.Serializable {

   private int StationId;

    private String StationName ;
    private int StationCapacity ;
    private String Street;
    private String City;
    private String Country;
    private double Latitude;
    private double Longitude;


    public Station(int stationId, String stationName, int stationCapacity, String street, String city, String country) {
        this.StationId = stationId;
        this.StationName = stationName;
        this.StationCapacity = stationCapacity;
        this.Street = street;
        this.City = city;
        this.Country = country;
    }
    public Station(int stationId, String stationName, int stationCapacity) {
        this.StationId = stationId;
        this.StationName = stationName;
        this.StationCapacity = stationCapacity;

    }

    public Station(int stationId, String stationName, int stationCapacity, String street, String city, String country, double latitude, double longitude) {
        this.StationId = stationId;
        this.StationName = stationName;
        this.StationCapacity = stationCapacity;
        this.Street = street;
        this.City = city;
        this.Country = country;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public void setStationId(int stationId) {
        this.StationId = stationId;
    }

    public void setStationName(String stationName) {
        this.StationName = stationName;
    }

    public void setStationCapacity(int stationCapacity) {
        this.StationCapacity = stationCapacity;
    }

    public void setStreet(String street) {
        this.Street = street;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public void setCountry(String country) {
        this.Country = country;
    }
    public void setLatitude (double latitude){this.Latitude = latitude;}
    public void setLongitude (double longitude){this.Longitude = longitude;}

    public int getStationId() {
        return StationId;
    }

    public String getStationName() {
        return StationName;
    }

    public int getStationCapacity() {
        return StationCapacity;
    }

    public String getStreet() {
        return Street;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }
    public double getLatitude(){ return Latitude;}
    public double getLongitude(){return Longitude;}



}
