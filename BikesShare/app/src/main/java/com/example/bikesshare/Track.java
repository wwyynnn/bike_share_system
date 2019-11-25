package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Track extends AppCompatActivity {

    DbOperator db;
    List<bikeTrackList> bikeList = new ArrayList<>();
    private TableLayout BikeTable;



    class bikeTrackList {
        public int bikeId;
        public String stationName;
        public String bikeStatus;

        bikeTrackList(int bikeId, String stationName, String bikeStatus){
            this.bikeId = bikeId;
            this.stationName = stationName;
            this.bikeStatus = bikeStatus;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        BikeTable = (TableLayout) findViewById(R.id.bike_track_table);
        db = new DbOperator(this);

        SQLiteDatabase db1 = db.getReadableDatabase();

        Cursor bicycle = db1.rawQuery("select * from Bike", null);

        for(int i=0;i<bicycle.getCount();i++){
            if(bicycle.moveToNext()){
                Bike bike = new Bike(bicycle.getInt(0),bicycle.getInt(1),bicycle.getInt(2));
                int bikeId = bike.getBikeId();
                int stationId = bike.getStationId();
                int statusId = bike.getBikeStatusId();
               // Log.d("-----------------------"," "+bikeId+" "+stationId+" "+statusId);
                String stationName;
                String statusName;



                if(statusId == 3){
                    // when bike is in-use, don't track the location (because it's moving)
                    stationName = "(USING)";


                }else{
                    Cursor station = db1.rawQuery("select StationName from Station where StationId = ?",new String[]{Integer.toString(stationId)});
                    station.moveToNext();
                   // Log.v("-----------------------"," "+station.getString(0));
                    stationName = station.getString(0);
                }

                Cursor status = db1.rawQuery("select BikeStatusName from BikeStatus where BikeStatusId = ?", new String[]{Integer.toString(statusId)});
               // Log.d("-----------------------"," "+status.getCount());
                status.moveToNext();

               // Log.d("-----------------------",status.getColumnName(0));
                statusName = status.getString(0);



                bikeTrackList btl = new bikeTrackList(bikeId,stationName,statusName);
               // Log.v("-------WWWYYYNNN-------"," "+btl.bikeId+" "+btl.stationName+" "+btl.bikeStatus);

                bikeList.add(btl);


            }
        }

        //show bikeList in tablerow
        int rows = bikeList.size();
        if(rows != 0){
            BikeTable.removeAllViewsInLayout(); //remove the last time output
            //create table dynamically
            for(int i=0;i<rows;i++){
                TableRow tablerow = new TableRow(getBaseContext());
                //show column content
                TextView column1 = new TextView(getBaseContext());
                column1.setPadding(1, 1, 1, 1);
                column1.setText(Integer.toString(bikeList.get(i).bikeId));
                TextView column2 = new TextView(getBaseContext());
                column2.setText(bikeList.get(i).stationName);
                TextView column3 = new TextView(getBaseContext());
                column3.setText(bikeList.get(i).bikeStatus);

                tablerow.addView(column1);
                tablerow.addView(column2);
                tablerow.addView(column3);



                BikeTable.addView(tablerow);
            }
        }

    }
}
