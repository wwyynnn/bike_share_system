package com.example.bikesshare;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;



public class SignIn extends AppCompatActivity {
    DbOperator db;
    EditText firstName;
    EditText Password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //the tool bar
        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.home);
        firstName = (EditText) findViewById(R.id.editText);
        Password = (EditText) findViewById(R.id.editText2);

        db = new DbOperator(this);

        final Button b = (Button) findViewById(R.id.SignIn);
        //b.setOnClickListener(this);
        b.setOnClickListener(new signInOnClickListener());



    }

    class signInOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.SignIn:

                    SQLiteDatabase db1 = db.getReadableDatabase();

                    Cursor r = db1.rawQuery("select* from User", null);
                    boolean flag = false;
                    if (r.getCount() > 0) {
                        while (r.moveToNext()) {
                            if ((r.getString(3)).equals(firstName.getText().toString()) && r.getString(1).equals(Password.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Welcome " + firstName.getText() + '\n' + getResources().getString(R.string.sign_in_successful), Toast.LENGTH_SHORT).show();
                                flag = true;
                                MyApplication application=(MyApplication)getApplication();
                                application.setID(r.getInt(0));

                                if (r.getInt(2) == 1) {
                                    //is customer, jump tp Home page
                                    Intent intent = new Intent(SignIn.this, Home.class);
                                    intent.putExtra("UserId", r.getInt(0) + "");
                                   // Log.d("The id from sign up", customer.getUserId() + "");
                                    intent.putExtra("PermissionId", r.getInt(2)+ "");
                                    intent.putExtra("DisableBook", "false");
                                    intent.putExtra("DisableReturn", "true");
                                    startActivity(intent);
                                    startActivity(intent);
                                } else if (r.getInt(2) == 2) {
                                    //is operator, jump to operatorProcesses page
                                    Intent intent = new Intent(SignIn.this, OperatorProcesses.class);
                                    startActivity(intent);
                                } else if (r.getInt(2) ==3){
                                    //is manager, jump to managerProcesses page
                                    Intent intent = new Intent(SignIn.this,ManagerProcesses.class);
                                    startActivity(intent);

                                }
                            }
                        }

                        if (!flag) {
                            Toast.makeText(getApplicationContext(), "The username or the ID is not correct", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

            }
        }


    }




}

