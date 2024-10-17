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

    /**
     *      The main function creates an instance of the Account class and passes that instance to the user menu
     * */

    public static void main(String[] args) {
        Accounts test = new Accounts();
        test = test.createAccount();
        //System.out.println(test.toString());
        // accountScreen();
        homeScreen(test);
    }

    /**
     *      The homeScreen function acts as the official entry to the program and prompts
     *      the user with the first level menu, the account object is
     *      passed to the function because, when a user is created, that user is then referenced
     *      in the accounts class where an account is then created simultaneously with the user
     * */

    public static void homeScreen(Accounts account){
        do{
            try {
                printStarsToTransitionMenu();
                System.out.println("Welcome to Accounting Ledger Application, " + account.getUser().getFullName() + "!");
                System.out.println("1) Add Deposit:");
                System.out.println("2) Make Payment:");
                System.out.println("3) Ledger:");
                System.out.println("4) Exit");

                int homeScreenChoice = Console.PromptForByte("\nEnter your selection: ");

                switch (homeScreenChoice) {
                    case 1:
                        account.addDeposit();
                        break;
                    case 2:
                        account.makePayment();
                        break;
                    case 3:
                        account.ledger();
                        break;
                    case 4:
                        System.out.println("Exiting application...");
                        return;
                    default:
                        System.out.println("Please enter one of the available selections.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " <----- This is not allowed. Please Try Again");
            }
        }while(true);
    }

    /**
     *      printStarsToTransitionMenu() just prints a smiley face to transition menus,
     *      in this instance it'll occur when the user first enters the program
     * */
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