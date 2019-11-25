package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OperatorProcesses extends AppCompatActivity {
    Button B2,B3,B4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_processes);

        B2=(Button)findViewById(R.id.OTrackButton);
        B3=(Button)findViewById(R.id.OMoveButton);
        B4=(Button)findViewById(R.id.ORepairButton);


        B2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(OperatorProcesses.this, Track.class));
            }
        });

        B3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(OperatorProcesses.this, Move.class));
            }
        });

        B4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(OperatorProcesses.this, Repair.class));
            }
        });
    }


}
