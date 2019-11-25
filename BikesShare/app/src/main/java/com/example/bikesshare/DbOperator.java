package com.example.bikesshare;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbOperator extends SQLiteOpenHelper {

     int DATABASE_VERSION = 1;


     String TABLE1 = "create table if not exists "
            + "Bike"
            + " ( BikeId  integer primary key autoincrement,\n"
            + " StationId   int    references " + "Station" + "(StationId)  ,\n"
            + " BikeStatusId int references " + "BikeStatus" + "(BikeStatusId) );";

     String TABLE2 = "create table if not exists "
            + "User" + "(UserId integer primary key autoincrement,UserPassword varchar(45) ,\n" +
            "    PermissionId int         ,\n" +
            "    FirstName    varchar(45) ,\n" +
            "    LastName     varchar(45) ,\n" +
            "    Email        varchar(45) ,\n" +
            "    PhoneNumber  varchar(20) ,\n" +
            "    PostCode     varchar(45) ,\n" +
            "    Balance      Double)";
     String TABLE3 = "create table if not exists "
            + "Station" + "(StationId      integer primary key autoincrement,\n" +
            "    StationName     varchar(50)  ,\n" +
            "    StationCapacity int          ,\n" +
            "    Street          varchar(100) ,\n" +
            "    City            varchar(50)  ,\n" +
            "    Country         varchar(50),\n" +
            "    Latitude        Decimal (6,9), \n" +
            "    Longitude       Decimal(6,9))";

     String TABLE4 = "create table if not exists "
            + "Journey" + "( JourneyId       integer primary key autoincrement,\n" +
            "    BikeId         int references " + "Bike" + "(BikeId ) ,\n" +
            "    UserId         int references " + "User" + "(UserId )     ,\n" +
            "    StartTime      datetime ,\n" +
            "    EndTime        datetime ,\n" +
            "    StartStationId int references " + "Station" + "(StationId)      ,\n" +
            "    EndStationId   int references " + "Station" + "(StationId)     )";
     String TABLE5 = "create table if not exists "
            + "CustomerTransaction" + "(TransactionId    integer primary key autoincrement,\n" +
            "    UserId          int references " + "User" + "(UserId )         ,\n" +
            "    TransactionTime datetime    ,\n" +
            "    AmountCharged   decimal(9)  ,\n" +
            "    JourneyId       int references " + "Journey" + "(JourneyId  )         ,\n" +
            "    AccountNumber   varchar(45) )";
     String TABLE6 = "create table if not exists "
            + "BikeStatus" + "(BikeStatusId     integer primary key autoincrement,\n" +
            "    BikeStatusName         varchar(45) ,\n" +
            "    BikeStatusDescription varchar(45) )";
     String TABLE7 = "create table if not exists "
            + "BrokenEvent" + "(BrokenEventId  integer primary key autoincrement,\n" +
            "    BikeId        int references " + "Bike" + "(BikeId )         ,\n" +
            "    ReportTime    datetime     ,\n" +
            "    StationId     int  references " + "Station" + "(StationId)        ,\n" +
            "    Reason        varchar(100) )";
     String TABLE8 = "create table if not exists "
            + "Permission" + "( PermissionId    integer primary key autoincrement,\n" +
            "    RentBike       tinyint(1) ,\n" +
            "    ReportBroken   tinyint(1) ,\n" +
            "    ReturnBike     tinyint(1) ,\n" +
            "    PayFee         tinyint(1) ,\n" +
            "    TrackBike      tinyint(1) ,\n" +
            "    RepairBike     tinyint(1) ,\n" +
            "    MoveBike       tinyint(1) ,\n" +
            "    GenerateReport tinyint(1) )";
     String TABLE9 = "create table if not exists "
            + "OperatorLog" + "(OperatorLogId  integer primary key autoincrement,\n" +
            "    BikeId        int  references " + "Bike" + "(BikeId )    ,\n" +
            "    BikeStatusId  int  references " + "BikeStatus" + "(BikeStatusId  )    ,\n" +
            "    UserId        int   references " + "User" + "(UserId )   ,\n" +
            "    StartTime     datetime ,\n" +
            "    EndTime       datetime ,\n" +
            "    StartStation  int references " + "Station" + "(StationId)      ,\n" +
            "    EndStation    int  references " + "Station" + "(StationId)     )";


    public DbOperator(Context context) {
        super(context, "BikeShareDB.db", null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE1);
        db.execSQL(TABLE2);
        db.execSQL(TABLE3);
        db.execSQL(TABLE4);
        db.execSQL(TABLE5);
        db.execSQL(TABLE6);
        db.execSQL(TABLE7);
        db.execSQL(TABLE8);
        db.execSQL(TABLE9);
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertBrokenEvent(int BikeId , String ReportTime, int StationId , String Reason/*, String Street,String city,String country*/)
    {

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put("BikeId  ",BikeId);
        content.put("ReportTime ",ReportTime.toString());
        content.put("StationId ",StationId);
        content.put("Reason ",Reason);

        long result=db.insert("BrokenEvent",null,content);
        if(result==-1)
            return false;
        else
            return true;


    }

    public boolean insertStation(String StationName, int StationCapacity, String Street, String City, String Country, double Latitude, double Longitude){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues content= new ContentValues();
       // content.put("StationId  ",StationId);
        content.put("StationName ",StationName);
        content.put("StationCapacity ",StationCapacity);
        content.put("Street ",Street);
        content.put("City ",City);
        content.put("Country ",Country);
        content.put("Latitude ",Latitude);
        content.put("Longitude",Longitude);
        long result=db.insert("Station",null,content);

        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean insertUser(String password, int permission,  String Fname, String Lname, String Email,String Phonenumber,String postcode, double Balance){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues content= new ContentValues();
        content.put("UserPassword  ",password);
        content.put("PermissionId ",permission);
        content.put("FirstName ",Fname);
        content.put("LastName ",Lname);
        content.put("Email ",Email);
        content.put("PhoneNumber ",Phonenumber);
        content.put("PostCode ",postcode);
        content.put("Balance ",Balance);


        long result=db.insert("User",null,content);

        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean insertPermission( int rentBike, int reportBike, int returnBike, int payFee, int trackBike, int repairBike, int moveBike, int generateReport){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        //content.put("PermissionId",permissionId);
        content.put("RentBike",rentBike);
        content.put("ReportBroken",reportBike);
        content.put("ReturnBike",returnBike);
        content.put("PayFee",payFee);
        content.put("TrackBike",trackBike);
        content.put("RepairBike",repairBike);
        content.put("MoveBike",moveBike);
        content.put("GenerateReport",generateReport);

        long result = db.insert("Permission",null,content);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertBike( int StationId, int BikeStatusId){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues content= new ContentValues();
        //content.put("BikeId",BikeId);
        content.put("StationId ",StationId);
        content.put("BikeStatusId ",BikeStatusId);
        long result=db.insert("Bike",null,content);

        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean insertBikeStatus( String BikeStatusName, String BikeStatusDescription){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues content= new ContentValues();
        //content.put("BikeId",BikeId);
        content.put("BikeStatusName ",BikeStatusName);
        content.put("BikeStatusDescription ",BikeStatusDescription);
        long result=db.insert("BikeStatus",null,content);

        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean insertJourney(
             int BikeId,
             int UserId,
             String StartTime,
             String EndTime,
             int StartStationId,
             int EndStationId
    ){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues content= new ContentValues();

        content.put("BikeId ",BikeId);
        content.put("UserId",UserId);
        content.put("StartTime",StartTime);
        content.put("EndTime",EndTime);
        content.put("StartStationId",StartStationId);
        content.put("EndStationId",EndStationId);

        long result=db.insert("Journey",null,content);

        if(result==-1)
            return false;
        else
            return true;
    }
    public boolean insertJourney(
                                 int bikeId, int userId,
                                 String startTime

    ){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues content= new ContentValues();
        //content.put("BikeId",BikeId);

        content.put("BikeId ",bikeId);
        content.put("UserId",userId);
        content.put("StartTime",startTime);


        long result=db.insert("Journey",null,content);

        if(result==-1)
            return false;
        else
            return true;
    }


    public List<BrokenEvent> ReadBrokenEvent( )
    {

        SQLiteDatabase db= this.getWritableDatabase();

        Cursor r=db.rawQuery("select * from BrokenEvent",null);
        List<BrokenEvent> data = new ArrayList<>();

        if (r.getCount() > 0) {
            while (r.moveToNext()) {
                BrokenEvent event=new BrokenEvent();
                event.setBikeId(r.getInt(1));
                event.setStationId(r.getInt(3));
               event.setReportTime(r.getString(2));
                event.setReason(r.getString(4));
                data.add(event);
            }
            db.close();
            return data;

        }
        db.close();
        return null;

    }

    public List<String> ReadBikes( )
    {


        SQLiteDatabase db= this.getWritableDatabase();

        Cursor r=db.rawQuery("select * from Bike,Station ",null);
        List<String> data = new ArrayList<String>();

        if (r.getCount() > 0) {
            while (r.moveToNext()) {
                data.add(r.getString(0));
            }
            return data;
        }
        return null;

    }



}