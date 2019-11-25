package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Charge extends AppCompatActivity implements View.OnClickListener {
    DbOperator db_ob;
    EditText num_other_mnt;
    double amount;
    Button jumpToPay;
    RadioGroup group;
    RadioButton p5;
    RadioButton p10;
    RadioButton p20;
    RadioButton p50;
    RadioButton other_mount;
    String UserId;
    String amount_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        jumpToPay = (Button) findViewById(R.id.pay_jump);
        group = (RadioGroup) findViewById(R.id.c_radio_group);
        p5 = (RadioButton) findViewById(R.id.btn_5p);
        p10 = (RadioButton) findViewById(R.id.btn_10p);
        p20 = (RadioButton) findViewById(R.id.btn_20p);
        p50 = (RadioButton) findViewById(R.id.btn_50p);
        other_mount = (RadioButton) findViewById(R.id.btn_othermnt);
        num_other_mnt = (EditText) findViewById(R.id.other_mnt_txt);
        db_ob =new DbOperator(this);
        jumpToPay.setOnClickListener(this);
        //display the balance
        TextView textView = (TextView) findViewById(R.id.textView4);
        MyApplication application=(MyApplication)getApplication();
        int UserID=application.userID;
        DbOperator db = new DbOperator(this);

        SQLiteDatabase db1 = db.getReadableDatabase();
        Cursor r=db1.rawQuery("select Balance from User where UserId = ?",new String[]{Integer.toString(UserID)});
        r.moveToNext();
        double balance = r.getDouble(0);



        textView.setText("Your current balance is: "+balance +" ￡");
    }

    @Override
    public void onClick(View view) {
        Timer timer = new Timer();
        UserId = getIntent().getStringExtra("UserId");
            switch(view.getId()){
                case R.id.pay_jump:
                    for (int i = 0; i < group.getChildCount(); i++) {
                        RadioButton rd = (RadioButton) group.getChildAt(i);
                        if(rd.isChecked()){
                            amount = 5.0;
                            amount_s = Double.toString(amount);
                            Toast.makeText(Charge.this, "Your recharge amount is ￡" + Math.round(amount) , Toast.LENGTH_SHORT).show();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Charge.this, CardPay.class);
                                    intent.putExtra("UserId", UserId);
                                    intent.putExtra("amount", amount_s);
                                    startActivity(intent);
                                }
                            },500);
                            break;
                        }else if(p10.isChecked()){
                            amount = 10.0;
                            amount_s = Double.toString(amount);
                            Toast.makeText(Charge.this, "Your recharge amount is ￡" + Math.round(amount) , Toast.LENGTH_SHORT).show();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Charge.this,CardPay.class);
                                    intent.putExtra("UserId", UserId);
                                    intent.putExtra("amount", amount_s);
                                    startActivity(intent);
                                }
                            },500);
                            break;
                        }else if(p20.isChecked()){
                            amount = 20.0;
                            amount_s = Double.toString(amount);
                            Toast.makeText(Charge.this, "Your recharge amount is ￡" + Math.round(amount) , Toast.LENGTH_SHORT).show();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Charge.this,CardPay.class);
                                    intent.putExtra("UserId", UserId);
                                    intent.putExtra("amount", amount_s);
                                    startActivity(intent);
                                }
                            },500);
                            break;
                        }else if(p50.isChecked()){
                            amount = 50.0;
                            amount_s = Double.toString(amount);
                            Toast.makeText(Charge.this, "Your recharge amount is ￡" + Math.round(amount) , Toast.LENGTH_SHORT).show();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Charge.this,CardPay.class);
                                    intent.putExtra("UserId", UserId);
                                    intent.putExtra("amount", amount_s);
                                    startActivity(intent);
                                }
                            },500);
                            break;
                        }else if (other_mount.isChecked() && TextUtils.isEmpty(num_other_mnt.getEditableText()) == false){
                            amount = Double.parseDouble(num_other_mnt.getEditableText().toString());
                            amount_s = Double.toString(amount);
                            Toast.makeText(Charge.this, "Your recharge amount is ￡" + amount , Toast.LENGTH_SHORT).show();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Charge.this,CardPay.class);
                                    intent.putExtra("UserId", UserId);
                                    intent.putExtra("amount", amount_s);
                                    startActivity(intent);
                                }
                            },500);
                            break;
                        }else {
                            Toast.makeText(Charge.this, "Please enter the amount of recharge!" , Toast.LENGTH_SHORT).show();
                            jumpToPay.setEnabled(false);
                            break;
                        }
                    }

            }


    }
}
