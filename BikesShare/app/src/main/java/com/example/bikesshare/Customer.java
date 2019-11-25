package com.example.bikesshare;

public class Customer extends User {
    private String postCode;

    public Customer()
    {
        super();
        this.setPermissionId(2);
        this.postCode = null;
    }

    public Customer(String password, String firstName, String lastName, String email, String phoneNumber,String postCode)
    {
        super(password, firstName, lastName, email, phoneNumber);
        this.setPermissionId(2);
        this.postCode = postCode;
    }


    public  void setPostCode(String postCode){
        this.postCode = postCode;

    }

    public String getPostCode()
    {
        return postCode;
    }

    @Override

    public String toString()
    {
        return "This user is a member of customer";
    }

}
