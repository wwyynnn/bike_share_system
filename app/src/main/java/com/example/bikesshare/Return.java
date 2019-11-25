package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Return extends AppCompatActivity {


    DbOperator db;

    private Spinner chooseStation = null;
    private TextView result = null;
    private ArrayAdapter<String> adapter = null;
    String userId;
    String journeyId;
    String booktime;
    String returntime;
    String userId_new;
    double totalBilling;
    double beforeCheck;
    double balance;
    double newBalance;
    String permissionId;

    // list of object :station

    ArrayList<Station> availableStations = new ArrayList<Station>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        db = new DbOperator(this);
        Intent intent = getIntent();
        String stationId = intent.getStringExtra("stationId");
        userId = intent.getStringExtra("UserId");
        permissionId = intent.getStringExtra("PermissionId");

        Button returnSubmit = (Button) findViewById(R.id.ReturnSubmit);
        returnSubmit.setOnClickListener(new returnSubmitOnClickListener());
        ImageButton report = (ImageButton) findViewById(R.id.ReportButton);
        report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(Return.this, Report.class));

            }
        });

        SQLiteDatabase db1 = db.getReadableDatabase();

        Cursor stations = db1.rawQuery("select * from Station", null);

        for (int i = 0; i < stations.getCount(); i++) {
            if (stations.moveToNext()) {
                Station st = new Station(stations.getInt(0), stations.getString(1), stations.getInt(2));
                int capa = st.getStationCapacity();
                int id = st.getStationId();
                Cursor bikes = db1.rawQuery("select* from Bike where StationId = ?", new String[]{Integer.toString(id)});
                int total = bikes.getCount();
                if (capa > total) {
                    //put the object into list
                    availableStations.add(st);
                }

            }
        }

        String[] availableStationName = new String[availableStations.size()];

        for (int i = 0; i < availableStations.size(); i++) {
            availableStationName[i] = availableStations.get(i).getStationName();
        }
        result = (TextView) findViewById(R.id.result);
        chooseStation = (Spinner) findViewById(R.id.chooseStation);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availableStationName);
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,language);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseStation.setAdapter(adapter);
        chooseStation.setVisibility(View.VISIBLE);
        chooseStation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                result.setText(getResources().getString(R.string.chooseStation) + ((TextView) view).getText());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    // page jump

    class returnSubmitOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // Button returnSubmit = (Button) findViewById(R.id.ReturnSubmit);
            switch (view.getId()) {

                case R.id.ReturnSubmit:

                    Timer timer = new Timer();
                    SQLiteDatabase db2 = db.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    userId = getIntent().getStringExtra("UserId");
                    journeyId = getIntent().getStringExtra("JourneyId");

//                    Toast.makeText(getApplicationContext(), userId+journeyId, Toast.LENGTH_SHORT).show();

                    Cursor cursor = db2.query("User", new String[]{"Balance"}, "UserId like ?", new String[]{userId}, null, null, null, null);
                    cursor.moveToNext();
                    balance = cursor.getDouble(0);
                    cursor.close();

//                    Toast.makeText(getApplicationContext(), Double.toString(balance), Toast.LENGTH_SHORT).show();

                    Cursor cursor_1 = db2.query("Journey", new String[]{"UserId,StartTime,EndTime"}, "JourneyId like ?", new String[]{journeyId}, null, null, null, null);
                    cursor_1.moveToFirst();
                    userId_new = Integer.toString(cursor_1.getInt(0));
                    booktime = cursor_1.getString(1);
                    cursor_1.close();
//                    Toast.makeText(getApplicationContext(), userId_new+booktime+ returntime, Toast.LENGTH_SHORT).show();

                    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    returntime = simpleFormat.format(date);
//                    Toast.makeText(getApplicationContext(), returntime, Toast.LENGTH_SHORT).show();

                    ContentValues cv = new ContentValues();
                    cv.put("EndTime", returntime);
                    cv.put("StartStationId", 1);
                    cv.put("EndStationId", 3);
                    db2.update("Journey", cv, "JourneyId=?", new String[]{journeyId});

                        try {
                            Date fromDate = simpleFormat.parse(booktime);
                            Date toDate = simpleFormat.parse(returntime);
                            long from = fromDate.getTime();
                            long to = toDate.getTime();
                            int minutes = (int) ((to - from) / (1000 * 60));
//                            Toast.makeText(getApplicationContext(), Integer.toString(minutes), Toast.LENGTH_SHORT).show();
                            beforeCheck = minutes * 0.5;
                            if (beforeCheck >= 100.0) {
                                totalBilling = 100.0;
                            } else if(minutes == 0){
                                totalBilling = 0.5;
                            }
                            else {
                                totalBilling = beforeCheck;
                            }
                        } catch (ParseException e) {
                            Toast.makeText(getApplicationContext(), "Billing Error!!!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    if (balance >= totalBilling) {
                        newBalance = balance - totalBilling;
                        values.put("Balance", newBalance);

                        db2.update("User", values, "UserId=?", new String[]{userId_new});
                       Intent intent = new Intent(Return.this, Home.class);
                        intent.putExtra("DisableBook", "false");
                        intent.putExtra("DisableReturn", "true");
                        intent.putExtra("UserId", userId);
                        intent.putExtra("PermissionId", permissionId);
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.ReturnSuccessful), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "Insufficient balance, please recharge！", Toast.LENGTH_SHORT).show();

                        timer.schedule(new TimerTask() {
                            public void run() {
                                MyApplication application=(MyApplication)getApplication();
                                application.setIfPaid(false);
                                Intent intent = new Intent(Return.this, Charge.class);
                                intent.putExtra("UserId", userId_new);
                                startActivity(intent);
                            }
                        }, 800);
                    }

            }
        }
    }
    //Log.w("Wyn", view.getResources().getResourceName(view.getId()));

}
