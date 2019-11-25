package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class GenerateReportManagern extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener ChooseDateListener;
    private Button B1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generate_report_managern);
        B1=(Button)findViewById(R.id.button3);


        final String TAG = "GenerateReportManagern";
        final DbOperator db = new DbOperator(this);

        Calendar calender = Calendar.getInstance();
        final int YY = calender.get((Calendar.YEAR));
        final int MM = calender.get((Calendar.MONTH));
        final int DD = calender.get((Calendar.DAY_OF_MONTH));
        B1.setOnClickListener(new View.OnClickListener() {
                          public void onClick(View v) {

                              B1.setVisibility(View.INVISIBLE);

                              DatePickerDialog d = new DatePickerDialog(GenerateReportManagern.this, android.R.style.Theme_DeviceDefault_Light_DarkActionBar, ChooseDateListener, YY, MM, DD);
                              d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.blue(2)));
                              d.show();
                          }
                      });

        ChooseDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                month = month + 1;


               // Log.d(TAG, "onDateSet: yyyy/mm/dd" + year + "." + month + "." + dayOfMonth);

                String date = year+ "." + month + "." + dayOfMonth;

               Intent i = new Intent(GenerateReportManagern.this, BarCharts.class);
               Bundle b = new Bundle();
               b.putString("Date", date);
               i.putExtras(b);
               startActivity(i);

            }


        };





    }


}