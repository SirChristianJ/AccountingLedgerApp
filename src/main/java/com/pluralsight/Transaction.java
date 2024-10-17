package com.pluralsight;

import java.time.LocalDateTime;

public class Transaction{

    /**
     *      Class transaction with all the metrics needed to create and track a transaction
     * */
    private String date;
    private String time;
    private String description;
    private String vendorName;
    private double amount;
    private double runningBalance;

    /**
     *      Parameterized constructor was used because I wanted to create a transaction in one line,
     *      and not have to call for setters, also shortens the class file
     * */
    public Transaction(String date, String time, String description,String vendorName, double amount, double runningBalance) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendorName = vendorName;
        this.amount = amount;
        this.runningBalance = runningBalance;
    }

    /**
    *       Getter functions to retrieve data for further functionality used in the Accounts class
    * */
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

    /**
     *      toString() is just an overridden method of the toString() String method
     *      I wanted to format the transaction to presentable and readable data
     * */
    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%.2f|%.2f"/*,getUserName()*/, date, time, description,vendorName, amount, runningBalance);
    }

}
