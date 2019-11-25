package com.example.bikesshare;
import android.app.Application;
public class MyApplication extends Application {
        int userID=0;
        boolean ifPaid = true;
        public void setID(int userID) {
            this.userID = userID;
        }
        public void setIfPaid(boolean ifPaid)  {
            this.ifPaid = ifPaid;
        }
    }

