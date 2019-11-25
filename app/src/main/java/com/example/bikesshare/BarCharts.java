package com.example.bikesshare;
//AIzaSyCFo9XH6DKMgDEwlKJ9f_M8PhVcfaCnQyw
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


import android.os.Bundle;
import android.widget.TextView;

public class BarCharts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_charts);

        Bundle DateValue = getIntent().getExtras();

        // to get the date value that was sent by GenerateReportManager
        String  Date = DateValue.getString("Date");



        final DbOperator db=new DbOperator(this);
        SQLiteDatabase db1 = db.getReadableDatabase();

        String query="select StartStationId,COUNT(StartStationId)from Journey where StartTime like '"+Date+"%"+"' GROUP BY StartStationId";
        Cursor r=db1.rawQuery(query,null);

        String query2="select * from Station";
        Cursor r2=db1.rawQuery(query2,null);

        //String query="select StartStationId,COUNT(StartStationId) from Journey WHERE StartTime='2019.10.23 20:34:46' GROUP BY StartStationId";

        ArrayList <Integer>stationsinChart = new ArrayList<>();
        BarChart StationsChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> barStationEntries = new ArrayList<>();
        ArrayList<String> barStationlabels = new ArrayList<String>();

    /*    for (int i = 0; i < r2.getCount(); i++) {
            if (r2.moveToNext()) {
                Station st = new Station(r2.getInt(0), r2.getString(1), r2.getInt(2));

                for (int j = 0; j < r.getCount(); j++) {
                    if (r.moveToNext()) {



                    }
                }


            }
        }
*/

        int i=0;
        if(r.getCount()>0) {
            while (r.moveToNext()) {
                barStationEntries.add(new BarEntry(r.getInt(1), i));
                stationsinChart.add(r.getInt(0));
                barStationlabels.add("Station"+String.valueOf(r.getInt(0)));
                i++;

            }
        }

        boolean flag2=false;

        if(r2.getCount()>0) {
            while (r2.moveToNext()) {
                for (int index : stationsinChart) {
                    if (index==r2.getInt(0))
                        flag2=true;

                }

                if(!flag2) {
                    barStationEntries.add(new BarEntry(0, i));
                    barStationlabels.add("Station" + String.valueOf(r2.getInt(0)));
                    i++;

                }

                flag2=false;
            }
        }



        BarDataSet barStationDataset = new BarDataSet(barStationEntries, "Stations");





        // barStationEntries.add(new BarEntry(2, i));



        BarData data = new BarData(barStationlabels, barStationDataset);
        StationsChart.setData(data); // set the data and list of labels into chart
        StationsChart.setDescription("How many journeies per station");  // set the description
        barStationDataset.setColors(ColorTemplate.PASTEL_COLORS);


    }
}

