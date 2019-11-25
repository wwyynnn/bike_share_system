package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ManagerProcesses extends AppCompatActivity {
    Button B1,B2,B3,B4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_processes);

        B1=(Button)findViewById(R.id.generateButton);
        B2=(Button)findViewById(R.id.MTrackbutton);
        B3=(Button)findViewById(R.id.MMoveButton);
        B4=(Button)findViewById(R.id.MRepairButton);

        B1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ManagerProcesses.this, GenerateReportManagern.class));
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ManagerProcesses.this, Track.class));
            }
        });

        B3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ManagerProcesses.this, Move.class));
            }
        });

        B4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ManagerProcesses.this, Repair.class));
            }
        });

    }
}


