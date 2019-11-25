package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class StationInfo extends AppCompatActivity{
    private ArrayList<Station> stationsList;
    DbOperator db;
    Cursor bikes;
    SQLiteDatabase db1;
    String userId;
    String permissionId;
   // boolean disableBook;
   // private DbOperator db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_info);
        db = new DbOperator(this);
        db1 = db.getReadableDatabase();


        Intent intent = getIntent();
        String stationId = intent.getStringExtra("stationId");
         userId = intent.getStringExtra("UserId");
         permissionId = intent.getStringExtra("PermissionId");
         Log.d("HEREEXTRA", userId);
        Button bookButton = (Button) findViewById(R.id.book_button);

        Log.d("HEREINTENT",stationId);

        if(!stationId.equals(null)) {
            //stationsList =(ArrayList<Station>)intent.getSerializableExtra("stationsList");
            Cursor st = db1.rawQuery("select* from Station where StationId = ?", new String[]{stationId});

            Station station = null;
            for (int i = 0; i < st.getCount(); i++) {
                if (st.moveToNext()) {
                    station = new Station(st.getInt(0), st.getString(1), st.getInt(2), st.getString(3), st.getString(4), st.getString(5), st.getDouble(6), st.getDouble(7));

                }
            }


            //Station station = stationsList.get(stationId);

            TextView stationName = (TextView) findViewById(R.id.station_name);
            stationName.setText(station.getStationName());

            TextView street = (TextView) findViewById(R.id.street);
            street.setText(station.getStreet());

            TextView city = (TextView) findViewById(R.id.city);
            city.setText(station.getCity());

            TextView bikeAvail = (TextView) findViewById(R.id.bikeAvail);



            //   Cursor stations = db1.rawQuery("select * from Station where StationId = ?", new String[]{Integer.toString(station.getStationId())});

            bikes = db1.rawQuery("select* from Bike where StationId = ?", new String[]{Integer.toString(station.getStationId())});

            int total = bikes.getCount();


            if (total == 0) {
                bikeAvail.setText(R.string.bike_not_avaail);
                bookButton.setEnabled(false);
            } else {
                bikeAvail.setText(R.string.bike_avail);
            }



        }



        Button backButton = (Button) findViewById(R.id.back_button);
       // ImageButton report = (ImageButton) findViewById(R.id.btn_report_1);
        Intent startIntent;

        bookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  Toast.makeText(getApplicationContext(),getResources().getString(R.string.bike_booked_msg),Toast.LENGTH_SHORT).show();



                for (int i = 0; i < bikes.getCount(); i++) {
                    if (bikes.moveToNext()) {
                        Bike bike = new Bike(bikes.getInt(0), bikes.getInt(1), bikes.getInt(2));

                        if (bike.getBikeStatusId() == 1) {
                            //put the object into list

                            ContentValues cv = new ContentValues();
                            cv.put("StationId", bike.getStationId()); //These Fields should be your String values of actual column names
                            cv.put("BikeStatusId","3");
                            db1.update("Bike", cv, "BikeId="+bike.getBikeId(), null);
                            /*db.insertJourney( int bikeId,
                            int userId,
                            SimpleDateFormat startTime);*/
                            SimpleDateFormat formatter= new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                            Date date = new Date(System.currentTimeMillis());
                            String finalDate = formatter.format(date);
                            db.insertJourney(bike.getBikeId(),Integer.parseInt(userId),finalDate);


                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.bike_booked_msg),Toast.LENGTH_SHORT).show();

                            break;

                        }

                    }
                }
                Intent intent = new Intent(StationInfo.this,Home.class);
                intent.putExtra("DisableBook", "true");
                intent.putExtra("DisableReturn", "false");
                intent.putExtra("UserId", userId);
                intent.putExtra("PermissionId", permissionId);
                startActivity(intent);

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.back_to_map_msg),Toast.LENGTH_SHORT).show();

                finish();
            }});





       // db.close();


    }


    }

    /*class returnSubmitOnClickListener implements View.OnClickListener{
        @Override

    }*/

