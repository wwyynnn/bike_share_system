package com.example.bikesshare;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class Journey {

    private int journeyId;
    private int bikeId;
    private int userId;
    private String startTime;
    private String endTime;



    public Journey(int journeyId,int bikeId, int userId, String startTime)
    {
        this.journeyId = journeyId;
        this.bikeId = bikeId;
        this.userId = userId;



    }

    public Journey(int journeyId,int bikeId, int userId, String startTime, String endTime)
    {

        this.journeyId = journeyId;
        this.bikeId = bikeId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public int getJourneyId() {
        return journeyId;
    }

    public int getBikeId() {
        return bikeId;
    }

    public int getUserId() {
        return userId;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setJourneyId(int journeyId) {
        this.journeyId = journeyId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
