package com.example.bikesshare;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.widget.*;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.location.LocationServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DbOperator db1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db1=new DbOperator(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });

     /*   fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                        }
                    }
                });*/

    /*    FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        //test insert data in station and bike table.





        SQLiteDatabase db = db1.getWritableDatabase();

         final String SECOND_TABLE_NAME = "User";
         db.execSQL("DROP TABLE IF EXISTS " + SECOND_TABLE_NAME);
         final String CREATE_SECOND_TABLE = "create table if not exists "
                + SECOND_TABLE_NAME + "(UserId integer primary key autoincrement,UserPassword varchar(45) ,\n" +
                "    PermissionId int         ,\n" +
                "    FirstName    varchar(45) ,\n" +
                "    LastName     varchar(45) ,\n" +
                "    Email        varchar(45) ,\n" +
                "    PhoneNumber  varchar(20) ,\n" +
                "    PostCode     varchar(45) ,\n" +
                "    Balance      double DEFAULT 0.0)";
        db.execSQL(CREATE_SECOND_TABLE);

        final String THIRD_TABLE_NAME = "Station";
        db.execSQL("DROP TABLE IF EXISTS " +THIRD_TABLE_NAME);
        final String CREATE_THIRD_TABLE = "create table if not exists "
                + THIRD_TABLE_NAME +"(StationId integer primary key autoincrement,\n"+
                "StationName varchar(50),\n"+
                "StationCapacity int,\n"+
                "Street varchar(100),\n"+
                "City varchar(50),\n"+
                "Country varchar(50),\n"+
                "Latitude Decimal(6,9),\n"+
                "Longitude Decimal(6,9))";
        db.execSQL(CREATE_THIRD_TABLE);

        final String FOURTH_TABLE_NAME = "Journey";
        db.execSQL("DROP TABLE IF EXISTS " +FOURTH_TABLE_NAME);
        final String CREATE_FOURTH_TABLE = "create table if not exists "
                + FOURTH_TABLE_NAME +"(JourneyId integer primary key autoincrement,\n"+
                "BikeId integer,\n"+
                "UserId integer,\n"+
                "StartTime datetime,\n"+
                "EndTime datetime,\n"+
                "StartStationId integer,\n"+
                "EndStationId integer)";
        db.execSQL(CREATE_FOURTH_TABLE);


        final String FIFTH_TABLE_NAME = "BikeStatus";
        db.execSQL("DROP TABLE IF EXISTS " +FIFTH_TABLE_NAME);
        final String CREATE_FIFTH_TABLE = "create table if not exists "
                + FIFTH_TABLE_NAME +"(BikeStatusId integer primary key autoincrement,\n"+
                "BikeStatusName varchar(45),\n"+
                "BikeStatusDescription varchar(45))";
        db.execSQL(CREATE_FIFTH_TABLE);
        db = db1.getReadableDatabase();
        Cursor users;

        users = db.rawQuery("select* from BikeStatus" , null);

        int total = users.getCount();
        if(total == 0){

            db1.insertUser("1",2, "Nawal","Althobaity","nawal@hotmail.com","075356677","G12 8PQ", 2500);
            //Adding a customer user type
            db1.insertUser("1",1, "Sara","yeah","sara@hotmail.com","07324677","G13 85Q", 2500);
            db1.insertUser("1",2,"James","wang","1@1","11","G11 6QH",3000);
            db1.insertUser("1",3,"Saige","liu","119@com","101","G99 6PH",1500);

            db1.insertPermission(1,1,1,1,0,0,0,0);
            db1.insertPermission(1,1,1,1,1,1,1,0);
            db1.insertPermission(1,1,1,1,1,1,1,1);
            db1.insertStation("Hillhead",3,"changchun","lizi","uk",55.8754,-4.2923);
            // 55.873711, -4.291829
            db1.insertStation("QMU", 3,"margo","Glasgow","uk", 55.873711, -4.291829);

            db1.insertStation("Kelvingrove Art Gallery", 3,"hello","London","uk", 55.868709, -4.291381);
            db1.insertStation("Haugh Road", 3,"hello","London","uk", 55.865597, -4.290960);
            db1.insertStation("Byres Road", 3,"hello","Glasgow","uk", 55.876633, -4.292208);

            db1.insertBike(1,1);
            db1.insertBike(1,1);
            db1.insertBike(2,1);
            db1.insertBike(3,1);
            db1.insertBike(2,1);
            db1.insertBike(3,1);
            db1.insertBike(3,1);

            db1.insertBrokenEvent(112, "2018.12.01 23:37:50", 1, "Broken wheels");
            db1.insertBrokenEvent(123, "2017.12.01 23:37:50", 1, "Lost a tire");



            db1.insertBikeStatus("Available","The bike is available for use");
            db1.insertBikeStatus("Broken","The bike has been reported broken");
            db1.insertBikeStatus("In-Use","The bike is in use");
            db1.insertBikeStatus("In-Repair","The bike is in repair");

            db1.insertJourney(1,5,"2019.10.22 13:12:25","2019.10.22 14:59:03",1,2);
            db1.insertJourney(2,5,"2019.10.25 09:22:07","2019.10.25 09:23:58",3,5);
            db1.insertJourney(1,5,"2019.10.22 13:12:25","2019.10.22 14:59:03",1,2);
            db1.insertJourney(1,5,"2019.10.22 09:22:07","2019.10.22 09:23:58",3,2);
            db1.insertJourney(1,5,"2019.10.22 14:34:25","2019.10.22 15:23:22",2,2);
            db1.insertJourney(1,5,"2019.10.22 15:34:25","2019.10.22 16:23:22",2,2);
            db1.insertJourney(1,5,"2019.10.22 19:34:25","2019.10.22 19:23:22",5,2);
            db1.insertJourney(2,5,"2019.10.25 09:22:07","2019.10.25 09:23:58",3,5);
            db1.insertJourney(1,5,"2019.11.06 13:12:25","2019.11.06 14:59:03",1,2);
            db1.insertJourney(2,5,"2019.11.06 09:22:07","2019.11.06 09:23:58",3,5);
            db1.insertJourney(1,5,"2019.11.06 14:34:25","2019.11.06 15:23:22",1,2);
            db1.insertJourney(2,5,"2019.11.06 18:22:07","2019.11.06 23:23:58",3,5);
        }





        Button signUp = (Button) findViewById(R.id.sign_up);
        Button signIn = (Button) findViewById(R.id.sign_in);
        ImageButton report = (ImageButton) findViewById(R.id.btn_report_1);

        signUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));

           }
       });


       signIn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, SignIn.class));

           }});

        report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Report.class));
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
