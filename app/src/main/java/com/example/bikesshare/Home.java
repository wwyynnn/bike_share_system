package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import android.widget.ImageButton;


public class Home extends AppCompatActivity {
    String userId;
    String permissionId;
    String bookBoolean;
    String returnBoolean;
    String journeyId;
    DbOperator db;
    Cursor journey;
    SQLiteDatabase db1;
    Journey userJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        Button Book = (Button) findViewById(R.id.Book);
        Button Return = (Button) findViewById(R.id.Return);

        Button Wallet = (Button) findViewById(R.id.walletButton);

        TextView bookInfo = (TextView) findViewById(R.id.BookedBike);




        bookInfo.setVisibility(View.INVISIBLE);


        if(!getIntent().equals(null)){

            userId =getIntent().getStringExtra("UserId");
            permissionId =getIntent().getStringExtra("PermissionId");



            bookBoolean = getIntent().getStringExtra("DisableBook");
            returnBoolean = getIntent().getStringExtra("DisableReturn");




            if(bookBoolean.equals("true")&& !bookBoolean.equals(null) && userId!=null ) {

                Book.setEnabled(false);
                Book.setVisibility(View.GONE);
                bookInfo.setVisibility(View.VISIBLE);
                db = new DbOperator(this);
                db1 = db.getReadableDatabase();
                journey = db1.rawQuery("select * from Journey where UserId = ? and endTime IS NULL" , new String[]{userId});

                Station station = null;
                for (int i = 0; i < journey.getCount(); i++) {
                    if (journey.moveToNext()) {
                        userJourney = new Journey(journey.getInt(0), journey.getInt(1), journey.getInt(2), journey.getString(3), journey.getString(4));
                        journeyId = Integer.toString(journey.getInt(0));
                    }
                }


                bookInfo.setText(getString(R.string.bike_book)+ userJourney.getBikeId());

            }else  {

                Book.setEnabled(true);
                bookInfo.setText("");
                bookInfo.setVisibility(View.GONE);
            }



            if(returnBoolean.equals("true")&& !returnBoolean.equals(null))
            {
                Return.setEnabled(false);
                Return.setVisibility(View.GONE);
            }

            else  {

                Return.setEnabled(true);
            }


        }


     //  Button Wallet=(Button) findViewById(R.id.walletButton);

       // Button Wallet =(Button) findViewById(R.id.walletButton);



/*
        else  {
            Return.setEnabled(true);
        }*/







        Return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(new Intent(Home.this, Return.class));
                intent.putExtra("UserId", userId);
                intent.putExtra("PermissionId", permissionId);
                intent.putExtra("JourneyId", journeyId);
             //   Log.d("The id from Home", userId);
                startActivity(intent);

            }
        });



        Book.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MapsActivity.class);
                intent.putExtra("UserId", userId);
                intent.putExtra("PermissionId", permissionId);
                Log.d("The id from Home", userId);
                startActivity(intent);

            }
        });

        Wallet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Charge.class));
            }
        });
    }
}
