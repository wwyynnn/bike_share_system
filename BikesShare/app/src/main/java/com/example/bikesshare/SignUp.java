package com.example.bikesshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;




public class SignUp extends AppCompatActivity {

    /* private int userId;
     private  String password;
     private  int permissionId;
     private  String firstName;
     private  String lastName;
     private  String email;
     private  String phoneNumber;

      String password, String firstName, String lastName, String email, String phoneNumber
     */
    DbOperator db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_sign_up);
        EditText firstName = (EditText) findViewById(R.id.firstNameEntry);
        EditText lastName = (EditText) findViewById(R.id.lastNameEntry);
        EditText email = (EditText) findViewById(R.id.emailEntry);
        EditText phoneNumber = (EditText) findViewById(R.id.phoneNumberEntry);
        EditText postCode = (EditText) findViewById(R.id.postCodeEntry);
        EditText password = (EditText) findViewById(R.id.passwordEntry);

        db = new DbOperator(this);

        Customer customer = new Customer(password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), phoneNumber.getText().toString(), postCode.getText().toString());


        final Button signUp = (Button) findViewById(R.id.register);
        signUp.setOnClickListener(new signUpOnClickListener());
    }


    // page jump

    class signUpOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.register:

                    EditText firstName = (EditText) findViewById(R.id.firstNameEntry);
                    EditText lastName = (EditText) findViewById(R.id.lastNameEntry);
                    EditText email = (EditText) findViewById(R.id.emailEntry);
                    EditText phoneNumber = (EditText) findViewById(R.id.phoneNumberEntry);
                    EditText postCode = (EditText) findViewById(R.id.postCodeEntry);
                    EditText password = (EditText) findViewById(R.id.passwordEntry);
                    CheckBox Admin = (CheckBox) findViewById(R.id.checkBox2);
                    Boolean AdminCheck = false;
                    Customer customer = new Customer(password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), phoneNumber.getText().toString(), postCode.getText().toString());
                    // save data to the database

                    SQLiteDatabase db1 = db.getWritableDatabase();
                    ContentValues content = new ContentValues();
                    content.put("UserPassword", customer.getPassword());
                    if (Admin.isChecked()) {
                        content.put("PermissionId", 2);
                        AdminCheck = true;
                    } else
                        content.put("PermissionId", 1);


                    content.put("FirstName", customer.getFirstName());
                    content.put("LastName", customer.getLastName());
                    content.put("Email", customer.getEmail());
                    content.put("PhoneNumber", customer.getPhoneNumber());
                    content.put("PostCode", customer.getPostCode());
                    content.put("Balance",10.0);

                    long result = db1.insert("User", null, content);


                    String selectQuery = "SELECT  * FROM " + "User";
                    Cursor cursor = db1.rawQuery(selectQuery, null);
                    cursor.moveToLast();

                    customer.setUserId(cursor.getInt(0));
                    if (result != -1) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.sign_up_successful), Toast.LENGTH_SHORT).show();


                        //customer.getFirstName()+"",customer.getUserId()+"",customer.getPermissionId()+"");
                        //jump tp Home page

                        if (result != -1 && AdminCheck == false) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.sign_up_successful), Toast.LENGTH_SHORT).show();

                            //jump tp main page
                            MyApplication application=(MyApplication)getApplication();
                            application.setID(customer.getUserId());
                            Intent intent = new Intent(SignUp.this, Home.class);
                            intent.putExtra("UserId", customer.getUserId() + "");
                            Log.d("The id from sign up", customer.getUserId() + "");
                            intent.putExtra("PermissionId", customer.getPermissionId() + "");
                            intent.putExtra("DisableBook", "false");
                            intent.putExtra("DisableReturn", "true");
                            startActivity(intent);
                        } else if (result != -1 && AdminCheck == true) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.sign_up_successful), Toast.LENGTH_SHORT).show();

                            //jump tp main page
                            Intent intent = new Intent(SignUp.this, OperatorProcesses.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                        db1.close();


                        // startActivity(new Intent(SignUp.this, SignUp.class));
                        break;


                    }
            }
        }


    }
}
