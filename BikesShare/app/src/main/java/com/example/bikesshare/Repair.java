package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Repair extends AppCompatActivity {


    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);


        final  DbOperator db1;
        db1=new DbOperator(this);
        final EditText BikeId = (EditText) findViewById(R.id.editText12);
        Button Repair = (Button) findViewById(R.id.button);
        final List<BrokenEvent> BrokenEventRecords;
        BrokenEventRecords=db1.ReadBrokenEvent();
        db1.close();



       Repair.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    db = db1.getWritableDatabase();
                    boolean flag = false;
                    if(BrokenEventRecords!=null) {
                        //When an operator choose a bike to repair , its status will be updated to "In repair"
                        for (BrokenEvent i : BrokenEventRecords) {
                            if (i.getBikeId() == Integer.parseInt(BikeId.getText().toString()))
                                flag = true;
                        }
                        if (flag) {
                            String query = "UPDATE Bike SET BikeStatusId=4 WHERE BikeId=" + (Integer.parseInt(BikeId.getText().toString()));
                            db.execSQL(query);
                            String query2 = "DELETE FROM BrokenEvent WHERE BikeId=" + (Integer.parseInt(BikeId.getText().toString()));
                            db.execSQL(query2);
                            Toast.makeText(getApplicationContext(), "The status of this bike has been updated to In repair", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "The BikeId  is not correct", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            TableLayout BrokenEvent = (TableLayout) findViewById(R.id.simple_table);

            TableRow Row = new TableRow(this);


            TextView Column1 = new TextView(this);
            Column1.setText("BikeId");
            TextView Column2 = new TextView(this);
            Column2.setText(("StationID"));
            TextView Column3 = new TextView(this);
            Column3.setText(("Repot_Time"));
            TextView Column4 = new TextView(this);
            Column4.setText(("Reason"));

            Row.addView(Column1);
            Row.addView(Column2);
            Row.addView(Column3);
            Row.addView(Column4);

            BrokenEvent.addView(Row);


            BrokenEvent.setStretchAllColumns(true);
            BrokenEvent.bringToFront();
            // To build a dynamic table , contains all BrokenEvent Records.
            int countId = 0;
            if(BrokenEventRecords!=null) {
                Toast.makeText(getApplicationContext(), "There are" + BrokenEventRecords.size() + "Broken events.", Toast.LENGTH_SHORT).show();

                for (BrokenEvent i : BrokenEventRecords) {
                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

                    TextView column1 = new TextView(this);
                    column1.setText(Integer.toString(i.getBikeId()));
                    TextView column2 = new TextView(this);
                    column2.setText((Integer.toString(i.getStationId())));
                    TextView column3 = new TextView(this);
                    column3.setText(i.getReportTime());
                    TextView column4 = new TextView(this);
                    column4.setText((i.getReason()));
                    row.addView(column1);
                    row.addView(column2);
                    row.addView(column3);
                    row.addView(column4);
                    row.setId(countId);
                    countId++;
                    if (row != null) {
                        BrokenEvent.addView(row);
                    }
                }
            }

    }
}

