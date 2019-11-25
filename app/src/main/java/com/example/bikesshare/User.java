package com.example.bikesshare;

public class User {

    private int userId;
    private  String password;
    private  int permissionId;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String phoneNumber;


    public User()
    {
        this.userId = -1;
        this.password = null;
        this.permissionId = -1;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phoneNumber = null;

    }
    //for customer
    public User(String password, String firstName, String lastName, String email, String phoneNumber)
    {

        this.password = password;
        this.permissionId = permissionId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }



    public void setUserId(int userId){
        this.userId = userId;

    }

    public int  getUserId()
    {
        return userId;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }


    public  void setPermissionId(int permissionId){
        this.permissionId = permissionId;
    }

    public int getPermissionId(){
        return permissionId;
    }

    public  void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public  void setLastName(String lastName){
        this.lastName = lastName;

    }

    public String getLastName()
    {
        return lastName;
    }


    public  void setEmail(String email){
        this.email = email;

    }


    public String getEmail()
    {
        return email;
    }

    public  void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;

    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }












}
