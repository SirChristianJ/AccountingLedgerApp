package com.pluralsight;
import com.pluralsight.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;

public class Main {
   ;
    static ArrayList<Accounts> accounts = new ArrayList<>();

    public static void main(String[] args) {
        /*Accounts test = new Accounts();
        test = test.createAccount();
        System.out.println(test.toString());*/
        // accountScreen();
        homeScreen();
    }

    public static void homeScreen(){
        do{
            try {
                printStarsToTransitionMenu();
                System.out.println("Welcome to Accounting Ledger Application!");
                System.out.println("1) Add Deposit:");
                System.out.println("2) Make Payment:");
                System.out.println("3) Ledger:");
                System.out.println("4) Exit");

                int homeScreenChoice = Console.PromptForByte("\nEnter your selection: ");

                switch (homeScreenChoice) {
                    case 1:
                        Accounts.addDeposit();
                        break;
                    case 2:
                        Accounts.makePayment();
                        break;
                    case 3:
                        Accounts.ledger();
                        break;
                    case 4:
                        System.out.println("Exiting application...");
                        return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " <----- Error. Please Try Again");
            }
        }while(true);
    }
    public static void printStarsToTransitionMenu(){
        System.out.println("\n");
        for(int i = 0; i < 2; i++)
            System.out.println("***************************************");
        for(int j = 0; j < 4; j++)
            System.out.println("**********   *************   **********");
        for (int k = 0; k <2; k++)
            System.out.println("***************************************");
        for(int l = 0; l < 1; l++)
            System.out.println("*********   ****************   ********");
        for(int m = 0; m < 1; m++)
            System.out.println("************   **********   ***********");
        for (int n = 0; n<1; n++)
            System.out.println("******************    *****************");
        for(int o = 0; o < 2; o++)
            System.out.println("***************************************");
        System.out.println("\n");

    }
}