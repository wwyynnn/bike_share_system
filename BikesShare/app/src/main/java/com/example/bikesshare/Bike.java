package com.example.bikesshare;

public class Bike {


    private int BikeId;
    private int StationId;
    private int BikeStatusId;

    public Bike(int bikeId, int stationId, int bikeStatusId) {
        this.BikeId = bikeId;
        this.StationId = stationId;
        this.BikeStatusId = bikeStatusId;
    }

    public int getBikeId() {
        return BikeId;
    }

    public int getStationId() {
        return StationId;
    }

    public int getBikeStatusId() {
        return BikeStatusId;
    }

    public void setBikeId(int bikeId) {
        this.BikeId = bikeId;
    }

    public void setStationId(int stationId) {
        this.StationId = stationId;
    }

    public void setBikeStatusId(int bikeStatusId) {
        this.BikeStatusId = bikeStatusId;
    }



}
