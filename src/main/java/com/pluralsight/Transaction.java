package com.pluralsight;

import java.time.LocalDateTime;

public class Transaction extends User{

    private String date;
    private String time;
    private String description;
    private double amount;

    public Transaction(/*String userName, */ String date, String time, String description,String vendorName, double amount) {
        super(/*userName,*/vendorName);
        this.date = date;
        this.time = time;
        this.description = description;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%.2f"/*,getUserName()*/, date, time, description,getVendorName(), amount);
    }
}
