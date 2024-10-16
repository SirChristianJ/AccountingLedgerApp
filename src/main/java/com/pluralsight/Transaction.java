package com.pluralsight;

import java.time.LocalDateTime;

public class Transaction{

    private String date;
    private String time;
    private String description;
    private String vendorName;
    private double amount;
    private double runningBalance;

    public Transaction( String date, String time, String description,String vendorName, double amount, double runningBalance) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendorName = vendorName;
        this.amount = amount;
        this.runningBalance = runningBalance;
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

    public String getVendorName() {
        return vendorName;
    }

    public double getAmount() {
        return amount;
    }

    public double getRunningBalance(){
        return runningBalance;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%.2f|%.2f"/*,getUserName()*/, date, time, description,vendorName, amount, runningBalance);
    }

}
