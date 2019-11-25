package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Report extends AppCompatActivity implements View.OnClickListener{
    EditText editText_2;
    EditText editText_1;
    Button report_1;
    CheckBox chekbx_wheel;
    CheckBox chekbx_pedal;
    CheckBox chekbx_chain;
    CheckBox chekbx_brake;
    CheckBox chekbx_handlebar;
    CheckBox chekbx_lock;
    DbOperator db_ob;
    private List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        editText_2=(EditText)findViewById(R.id.specific_description);
        editText_1=(EditText)findViewById(R.id.report_id_txt);
        report_1 = (Button) findViewById(R.id.submit_btn);
        chekbx_wheel = (CheckBox)findViewById(R.id.checkBox_wheel);
        chekbx_pedal = (CheckBox)findViewById(R.id.checkBox_pedal);
        chekbx_chain = (CheckBox)findViewById(R.id.checkBox_chain);
        chekbx_brake = (CheckBox)findViewById(R.id.checkBox_brake);
        chekbx_handlebar = (CheckBox)findViewById(R.id.checkBox_handlebar);
        chekbx_lock = (CheckBox)findViewById(R.id.checkBox_lock);
        db_ob =new DbOperator(this);
        report_1.setOnClickListener(this);
        checkBoxList.add(chekbx_wheel);
        checkBoxList.add(chekbx_pedal);
        checkBoxList.add(chekbx_chain);
        checkBoxList.add(chekbx_brake);
        checkBoxList.add(chekbx_handlebar);
        checkBoxList.add(chekbx_lock);
    }
    private String YMDHMS(){  //get date and transfer to timestamp
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        long time = System.currentTimeMillis() / 1000;
        String str = String.valueOf(time);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(str);
        int i = Integer.parseInt(str);
        String time_s = sdr.format(new Date(i * 1000L));
        return time_s;
    }
    @Override
    public void onClick(View view){

        switch(view.getId()){
            case R.id.submit_btn:
                String id_txt = editText_1.getText().toString();
                String des_txt = editText_2.getText().toString();
                if (chekbx_wheel.isChecked() == false&&chekbx_pedal.isChecked()==false&&chekbx_chain.isChecked()==false
                        &&chekbx_brake.isChecked()==false&&chekbx_handlebar.isChecked()==false
                        &&chekbx_lock.isChecked()==false) {
                    Toast.makeText(Report.this, "Please choose a damaged part at least!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(id_txt)||TextUtils.isEmpty(des_txt)) {
                    Toast.makeText(Report.this, "Please enter BIKE'S ID or DESCRIPTION!", Toast.LENGTH_SHORT).show();
                } else {
                    int bikeId = Integer.valueOf(id_txt).intValue();
                    /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date date = sdf.parse(YMDHMS());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    StringBuffer sb = new StringBuffer();
                    for (CheckBox checkbox : checkBoxList) {
                        if (checkbox.isChecked()) {
                            sb.append(checkbox.getText().toString() + " ");
                        }
                    }
                    String reason_f = des_txt +", and damaged part: "+ sb.toString();
                    boolean b = insertBrokenEvent(bikeId,YMDHMS(),1,reason_f);
                    if (b) {
                        Toast.makeText(Report.this,"Thank you for reporting malfuctions!", Toast.LENGTH_SHORT).show();
                        db_ob.close();
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
//                                Intent intent = new Intent(Report.this,MainActivity.class);
//                                startActivity(intent);
                                finish();
                            }
                        },500);

                        break;
                    }else{
                        Toast.makeText(Report.this,"Oops! Something wrong!", Toast.LENGTH_SHORT).show();

                    }
                }
        }

    }
    public boolean insertBrokenEvent(int BikeId , String ReportTime, int StationId , String Reason)
    {
        SQLiteDatabase db= db_ob.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put("BikeId  ",BikeId);
        content.put("ReportTime ", ReportTime);
        content.put("StationId ",StationId);
        content.put("Reason ",Reason);

        long result=db.insert("BrokenEvent",null,content);
        if(result==-1) {
            return false;
        }else {
            return true;
        }
    }

}
