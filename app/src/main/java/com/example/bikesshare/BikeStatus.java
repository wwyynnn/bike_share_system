package com.example.bikesshare;

public class BikeStatus {
    int BikeStatusId;
    String BikeStatusName;
    String BikeStatusDescription;


    public void setBikeStatusId(int bikeStatusId) {
        BikeStatusId = bikeStatusId;
    }

    public void setBikeStatusName(String bikeStatusName) {
        BikeStatusName = bikeStatusName;
    }

    public void setBikeStatusDescription(String bikeStatusDescription) {
        BikeStatusDescription = bikeStatusDescription;
    }

    public BikeStatus(int bikeStatusId, String bikeStatusName, String bikeStatusDescription) {
        BikeStatusId = bikeStatusId;
        BikeStatusName = bikeStatusName;
        BikeStatusDescription = bikeStatusDescription;




    }


    public int getBikeStatusId() {
        return BikeStatusId;
    }

    public String getBikeStatusName() {
        return BikeStatusName;
    }

    public String getBikeStatusDescription() {
        return BikeStatusDescription;
    }
}
