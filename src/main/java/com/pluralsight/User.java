package com.pluralsight;
/**
 *User class is used to represent a vendor/user
 * Here you will keep track of the vendor name and the name of the user who initiated the transaction.
 */

public class User {

    //private String userName;
    private String vendorName;

    public User(/*String userName ,*/String vendorName) {
        //this.userName = userName;
        this.vendorName = vendorName;
    }

    /*public String getUserName() {
        return userName;
    }*/

    public String getVendorName() {
        return vendorName;
    }

}
