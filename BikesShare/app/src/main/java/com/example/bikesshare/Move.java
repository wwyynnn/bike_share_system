package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Move extends AppCompatActivity {


    DbOperator db;
    Spinner from;
    Spinner to;
    Button move;
    Button getBikes;
    Cursor bikes;
    Cursor stations;
    ArrayList<Station> stationListTo;
    ArrayList<Station> stationListFrom;
    ArrayList<Bike> bikeList;
    int stationIdFrom;
    int total;
    int capacity;

    ArrayList<String> bikeIds;
    ArrayList<CheckBox> chkBoxList;
    ArrayList<String> checkedBikes;
    SQLiteDatabase db1;
    LinearLayout rl;

    int stationIdTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        stationListTo = new ArrayList<Station>();
        stationListFrom = new ArrayList<Station>();
        bikeList = new ArrayList<Bike>();
        rl = (LinearLayout) findViewById(R.id.checkBox);
        db = new DbOperator(this);
        db1 = db.getReadableDatabase();
        chkBoxList = new  ArrayList<CheckBox>();
        checkedBikes= new  ArrayList<String>();



        from = (Spinner) findViewById(R.id.bikes_from);
        to = (Spinner) findViewById(R.id.bikes_to);
        move = (Button) findViewById(R.id.move);
        move.setEnabled(false);
        getBikes = (Button) findViewById(R.id.get_bikes);


        stations = db1.rawQuery("select* from Station", null);

        for (int i = 0; i < stations.getCount(); i++) {
            if (stations.moveToNext()) {
                Station station = new Station(stations.getInt(0), stations.getString(1), stations.getInt(2));

                stationListTo.add(station);
                stationListFrom.add(station);

            }


        }



        ArrayList<String> stationNames = new ArrayList<>();






        for(int i = 0; i< stationListTo.size(); i++){
            stationNames.add(stationListTo.get(i).getStationName());
        }


        ArrayAdapter<String> dataAdapterFrom = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,stationNames);

        ArrayAdapter<String> dataAdapterTo = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,stationNames);

        dataAdapterFrom
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterTo
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        to.setAdapter(dataAdapterTo);

        from.setAdapter(dataAdapterFrom);


        String stationTo = to.getSelectedItem().toString();



        stationIdTo=-1;

        for(int i = 0; i< stationListTo.size(); i++){
            if(stationListTo.get(i).getStationName().equals(stationTo))
            {
                stationIdTo = stationListTo.get(i).getStationId();


                break;
            }
        }

//        stationIdFrom=-1;
//
//        for(int i = 0; i< stationListFrom.size(); i++){
//            if(stationListFrom.get(i).getStationName().equals(stationFrom))
//            {
//                stationIdFrom = stationListFrom.get(i).getStationId();
//                break;
//            }
//        }
//         bikeIds = new ArrayList<String>();
//
//        bikes = db1.rawQuery("select* from Bike where StationId = ?", new String[]{Integer.toString(stationIdFrom)});
//
//
//        for (int i = 0; i < bikes.getCount(); i++) {
//            if (bikes.moveToNext()) {
//                Bike bike = new Bike(bikes.getInt(0), bikes.getInt(1), bikes.getInt(2));
//                bikeList.add(bike);
//                bikeIds.add(bike.getBikeId()+"");
//
//
//            }
//
//
//        }

        Cursor bikes2 = db1.rawQuery("select* from Bike where StationId = ?", new String[]{Integer.toString(stationIdTo)});
        total = bikes2.getCount();

        for(int i=0;i<stationListTo.size();i++){

            if(stationListTo.get(i).getStationId() == stationIdTo){
                capacity = stationListTo.get(i).getStationCapacity();
            }

        }




        getBikes.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                rl.removeAllViews();
                String stationFrom = from.getSelectedItem().toString();

                stationIdFrom=-1;

                for(int i = 0; i< stationListFrom.size(); i++){
                    if(stationListFrom.get(i).getStationName().equals(stationFrom))
                    {
                        stationIdFrom = stationListFrom.get(i).getStationId();
                        break;
                    }
                }
                bikeIds = new ArrayList<String>();

                bikes = db1.rawQuery("select* from Bike where StationId = ?", new String[]{Integer.toString(stationIdFrom)});


                for (int i = 0; i < bikes.getCount(); i++) {
                    if (bikes.moveToNext()) {
                        Bike bike = new Bike(bikes.getInt(0), bikes.getInt(1), bikes.getInt(2));
                        bikeList.add(bike);
                        bikeIds.add(bike.getBikeId()+"");


                    }


                }
                for(int i =0; i<stationListTo.size(); i++){
                    if (stationIdTo == stationListTo.get(i).getStationId()){

                        capacity = stationListTo.get(i).getStationCapacity();

                    }

                }
                Log.d("HERE",total+" "+capacity);

                if(total==capacity){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.capacity_exceeded),Toast.LENGTH_SHORT).show();
                    move.setEnabled(false);
                }

                else{

                    for(int i = 0; i <bikeIds.size(); i++) {
                        CheckBox cb = new CheckBox(getApplicationContext());
                        cb.setText(bikeIds.get(i));
                        cb.setPadding(25,25,100,25);
                        rl.addView(cb);

                    }

                    move.setEnabled(true);
                }

            }
        });


        move.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {



                for(int i = 0; i <bikeIds.size(); i++) {
                    final CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setText(bikeIds.get(i));
                    cb.setPadding(25,25,100,25);

                  //  chkBoxList.add(cb);
                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                            if(isChecked){
                                checkedBikes.add(cb.getText().toString());
                            }
                            else
                            {

                            }

                        }
                    }
                    );


                    //   cb.setOnCheckedChangeListener(handleCheck(cb));
                    rl.addView(cb);
                /*    cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkedBikes.add(cb.getText().toString());

                        }
                    });*/

                   //Calling the function, add
                   /* cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            // update your model (or other business logic) based on isChecked
                            if(isChecked == true){
                                checkedBikes.add(cb.getText().toString());
                            }
                        }


                    });*/


                }

              /*  for(int i =0; i<chkBoxList.size();i++)
                {
                    if(chkBoxList.get(i).isChecked())
                    {
                        checkedBikes.add(chkBoxList.get(i).getText().toString());
                    }
                }*/

                if(!checkedBikes.isEmpty() && checkedBikes!=null ) {
                    for(int i =0; i<checkedBikes.size();i++){
                        //take the bike id from list and where the bike id matches, change station id
                        bikes = db1.rawQuery("select* from Bike where BikeId = ?", new String[]{checkedBikes.get(i)});
                        for (int j = 0; j < bikes.getCount(); j++) {
                            if (bikes.moveToNext()) {

                                Bike bike = new Bike(bikes.getInt(0), bikes.getInt(1), bikes.getInt(2));
                                ContentValues cv = new ContentValues();
                                cv.put("StationId", stationIdTo); //These Fields should be your String values of actual column names
                                cv.put("BikeStatusId","1");
                                db1.update("Bike", cv, "BikeId="+checkedBikes.get(i), null);


                            }
                        }


                    }
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.bike_moved),Toast.LENGTH_SHORT).show();
                    db.close();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.bike_moved_unsuccess),Toast.LENGTH_SHORT).show();
                    db.close();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }

            }




        });






    }




    }









