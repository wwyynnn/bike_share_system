package com.example.bikesshare;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;
import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;
public class BrokenEvent {

    public BrokenEvent(int brokenEventId, int bikeId, String reportTime, int stationId, String reason) {
        BrokenEventId = brokenEventId;
        BikeId = bikeId;
        ReportTime = reportTime;
        StationId = stationId;
        Reason = reason;
    }
    public BrokenEvent()
    {}

    public void setBrokenEventId(int brokenEventId) {
        BrokenEventId = brokenEventId;
    }

    public void setBikeId(int bikeId) {
        BikeId = bikeId;
    }

    public void setReportTime(String  reportTime) {
        ReportTime = reportTime;
    }

    public void setStationId(int stationId) {
        StationId = stationId;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    int BrokenEventId;
    int BikeId;
    String ReportTime;
    int StationId;
    String Reason;
    public int getBrokenEventId() {
        return BrokenEventId;
    }

    public int getBikeId() {
        return BikeId;
    }

    public String getReportTime() {
        return ReportTime;
    }

    public int getStationId() {
        return StationId;
    }

    public String getReason() {
        return Reason;
    }

    @Override
    public String toString() {
        return "BrokenEvent{" +
                "BrokenEventId=" + BrokenEventId +
                ", BikeId=" + BikeId +
                ", ReportTime='" + ReportTime + '\'' +
                ", StationId=" + StationId +
                ", Reason='" + Reason + '\'' +
                '}';
    }










}
