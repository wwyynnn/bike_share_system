package com.example.bikesshare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class CardPay extends AppCompatActivity implements View.OnClickListener {
    DbOperator db_ob;
    EditText card_number;
    EditText holder_name;
    EditText expire_date;
    EditText cvv_code;
    TextView amount_show;
    Button place_order_btn;
    String lastInput ="";
    int UserID;
    String amount;
    double amount_new;
    boolean ifpaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pay);
//        UserId = getIntent().getStringExtra("UserId");
        amount = getIntent().getStringExtra("amount");
        amount_new = Double.parseDouble(amount);

        card_number = (EditText) findViewById(R.id.card_num);
        holder_name = (EditText) findViewById(R.id.holder_name);
        expire_date = (EditText) findViewById(R.id.ex_date);
        cvv_code = (EditText) findViewById(R.id.cvv_txt);
        amount_show = (TextView) findViewById(R.id.amount_show);
        place_order_btn = (Button) findViewById(R.id.place_order);
        amount_show.setText("￡"+amount_new);
        db_ob = new DbOperator(this);
        place_order_btn.setOnClickListener(this);
        TextChange textChange = new TextChange();
        card_number.addTextChangedListener(textChange);
        holder_name.addTextChangedListener(textChange);
        expire_date.addTextChangedListener(textChange);
        cvv_code.addTextChangedListener(textChange);



        expire_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                String input = s.toString();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
                Calendar expiryDateDate = Calendar.getInstance();
                try {
                    expiryDateDate.setTime(formatter.parse(input));
                } catch (ParseException e) {
                    if (s.length() == 2 && !lastInput.endsWith("/")) {
                        int month = Integer.parseInt(input);
                        if (month <= 12) {
                            expire_date.setText(expire_date.getText().toString() + "/");
                            expire_date.setSelection(expire_date.getText().toString().length());
                        }else{
                            expire_date.setText("");
                        }
                    } else if (s.length() == 2 && lastInput.endsWith("/")) {
                        int month = Integer.parseInt(input);
                        if (month <= 12) {
                            expire_date.setText(expire_date.getText().toString().substring(0, 1));
                            expire_date.setSelection(expire_date.getText().toString().length());
                        } else {
                            expire_date.setText("");
                            expire_date.setSelection(expire_date.getText().toString().length());
                            Toast.makeText(getApplicationContext(), "Enter a valid month", Toast.LENGTH_LONG).show();
                        }
                    } else if (s.length() == 1) {
                        int month = Integer.parseInt(input);
                        if (month > 1) {
                            expire_date.setText("0" + expire_date.getText().toString() + "/");
                            expire_date.setSelection(expire_date.getText().toString().length());
                        }
                    } else {

                    }
                    lastInput = expire_date.getText().toString();
                    return;
                }

            }
        });


    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.place_order:

                    boolean status = expire_date.getText().toString().contains("/");
                    SQLiteDatabase db = db_ob.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    MyApplication application=(MyApplication)getApplication();
                     UserID=application.userID;

                    Cursor cursor = db.query("User ", new String[]{"UserId,Balance"}, "UserId like ?", new String[]{Integer.toString(UserID)}, null, null, null, null);
                    cursor.moveToFirst();
                    double balance = cursor.getDouble(1);

                    cursor.close();

                    double new_balance =  balance + amount_new;
                if(card_number.getText().toString().length() == 16 &&  status){
                    values.put("Balance",new_balance);
                    db.update("User",values,"UserId=?", new String[]{Integer.toString(UserID)});
                    Toast.makeText(CardPay.this, "Top-up Success!" , Toast.LENGTH_SHORT).show();

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                       public void run() {
//                           MyApplication application=(MyApplication)getApplication();
//                           ifpaid = application.ifPaid;
//
//                           if(ifpaid == false){
//
//
//                            Intent intent = new Intent(CardPay.this,Home.class);
//                           intent.putExtra("UserId", UserID + "");
//                           // Log.d("The id from sign up", customer.getUserId() + "");
//                               Toast.makeText(CardPay.this, UserID , Toast.LENGTH_SHORT).show();
//                           intent.putExtra("PermissionId", 1 + "");
//                           intent.putExtra("DisableBook", "true");
//                           intent.putExtra("DisableReturn", "false");
//                            startActivity(intent);
//                           }else{
//                               Toast.makeText(CardPay.this, "dededede" , Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(CardPay.this,Home.class);
                               intent.putExtra("UserId", UserID + "");
                               // Log.d("The id from sign up", customer.getUserId() + "");
                               intent.putExtra("PermissionId", 1 + "");
                               intent.putExtra("DisableBook", "false");
                               intent.putExtra("DisableReturn", "true");
                               startActivity(intent);
//                           }
                        }
                    },800);

                }else{
                    Toast.makeText(CardPay.this, "Card information INCORRECT!" , Toast.LENGTH_SHORT).show();
                }
            }
    }
    class TextChange implements TextWatcher {
        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {


            boolean Sign1 = card_number.getText().length() > 0;
            boolean Sign2 = holder_name.getText().length() > 0;
            boolean Sign3 = expire_date.getText().length() > 0;
            boolean Sign4 = cvv_code.getText().length() > 0;

            if (Sign1 & Sign2 & Sign3 & Sign4) {

                place_order_btn.setBackgroundColor(Color.parseColor("#2F66C0"));
                place_order_btn.setEnabled(true);
            }
            else {
                place_order_btn.setBackgroundColor(Color.GRAY);
                place_order_btn.setEnabled(false);
            }
        }

    }
}


