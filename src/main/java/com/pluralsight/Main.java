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
    static final String fileName = "transaction.csv";
    static ArrayList<Transaction> transactions = initializeTransactions();

    public static void main(String[] args) {
        homeScreen();
    }

    /** First level menu
    *
    */
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
                        addDeposit();
                        break;
                    case 2:
                        makePayment();
                        break;
                    case 3:
                        ledger();
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
    /** Initializes my arraylist of transactions,
    * reads initial data from data file
    */
    public static ArrayList<Transaction> initializeTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String input;
            bufferedReader.readLine();
            while ((input=bufferedReader.readLine()) !=null){
                String[] dataCategory = input.split("\\|");
                Transaction t = new Transaction(dataCategory[0],
                        dataCategory[1],
                        dataCategory[2],
                        dataCategory[3],
                        Double.parseDouble(dataCategory[4]),
                        Double.parseDouble(dataCategory[5]));
                transactions.add(t);
            }
        }catch (IOException e){
            System.out.println("something wrong");
        }

        return transactions;
    }
    public static void addDeposit() {
        boolean isAnotherDeposit = false;
        do{
            try {
                printStarsToTransitionMenu();
                boolean isAddDeposit = Console.PromptForYesNo("Would you like to add a deposit? ");
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
                String timeStampFormatter = dateTime.format(formatter);
                String[] timeStamp  =  timeStampFormatter.split(" ");

                if (isAddDeposit) {
                    //String transactionUser = Console.PromptForString("Enter the name of the user initiating the transaction: ");
                    String transactionDescription = Console.PromptForString("Enter a description for this transaction: ");
                    String transactionVendor = Console.PromptForString("Enter the name of the vendor making this deposit: ");
                    double transactionPrice = Console.PromptForDouble("Enter a price: ");
                    double transactionBalance = 0;
                    for (Transaction t : transactions)
                        transactionBalance = t.getRunningBalance();
                    Transaction transaction = new Transaction(timeStamp[0],timeStamp[1], transactionDescription,transactionVendor,  transactionPrice,transactionBalance+transactionPrice );
                    //System.out.println(transaction.getDateTime() + " " + transaction.getDescription() + " " + transaction.getVendor() + " " + transactionPrice * -1.0);
                    transactions.add(transaction);
                    writeTransactionToCSV();
                }
                isAnotherDeposit = Console.PromptForYesNo("\nWould you like to make another deposit? ");
                if(!isAnotherDeposit) return;

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + "<---- Make sure your input is numeric.");

            }
        }while (true);
    }
    public static void makePayment(){
        boolean isAnotherDeposit = false;
        do{
            try {
                printStarsToTransitionMenu();
                boolean isAddDeposit = Console.PromptForYesNo("Would you like to make a payment? ");
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
                String timeStampFormatter = dateTime.format(formatter);
                String[] timeStamp  =  timeStampFormatter.split(" ");

                if (isAddDeposit) {
                    String transactionDescription = Console.PromptForString("Enter a description for this transaction: ");
                    String transactionVendor = Console.PromptForString("Enter the name of the vendor receiving this payment: ");
                    double transactionPrice = Console.PromptForDouble("Enter a price: ");
                    double transactionBalance = 0;
                    for(Transaction t: transactions)
                        transactionBalance = t.getRunningBalance();
                    Transaction transaction = new Transaction(timeStamp[0],timeStamp[1],transactionDescription,transactionVendor, transactionPrice*-1.0,transactionBalance-transactionPrice);
                    //System.out.println(transaction.getDateTime() + " " + transaction.getDescription() + " " + transaction.getVendor() + " " + transactionPrice * -1.0);
                    transactions.add(transaction);
                    writeTransactionToCSV();
                }
                isAnotherDeposit = Console.PromptForYesNo("\nWould you like to make another payment? ");


            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + "<---- Make sure your input is numeric.");

            }
        }while (isAnotherDeposit);
    }
    public static void writeTransactionToCSV() {
        try {

            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("date|time|description|vendor|amount|balance\n");
            for (Transaction t : transactions) {
                String data = String.format("%s|%s|%s|%s|%.2f|%.2f\n",t.getDate(),t.getTime(), t.getDescription(),t.getVendorName(), t.getAmount(), t.getRunningBalance());
                fileWriter.write(data);
            }


            fileWriter.close();
        } catch (Exception e) {
            System.out.println("FILE WRITE ERROR");
        }
    }

    /**
     *Second level menu
     * handles all functionality related to filtering the ledger
     */
    public static void ledger(){
        ledgerScreen();
    }
    public static void ledgerScreen(){
        do{

            try {
                printStarsToTransitionMenu();
                System.out.println("\nWelcome to the Ledger Screen!");
                System.out.println("1) Display all entries");
                System.out.println("2) Display deposits");
                System.out.println("3) Display payments");
                System.out.println("4) Display reports");
                System.out.println("5) Exit");

                int ledgerScreenChoice = Console.PromptForByte("\nEnter your selection: ");

                switch (ledgerScreenChoice) {
                    case 1:
                        displayAllEntries();
                        break;
                    case 2:
                        displayDeposits();
                        break;
                    case 3:
                       displayPayments();
                        break;
                    case 4:
                        reportScreen();
                        break;
                    case 5:
                        System.out.println("returning to previous menu...");
                        return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " <----- Error. Please Try Again");
            }
        }while(true);
    }
    public static void displayAllEntries(){
        while(true){
            System.out.println("All entries");
            System.out.printf("| %15s | %15s | %-30s | %15s | %15s | %15s |\n", "Date", "Time", "Description", "Vendor", "Amount", "Balance");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            for (Transaction t : transactions) {
                System.out.printf("| %15s | %15s | %-30s | %15s | %15.2f | %15.2f |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount(),
                        t.getRunningBalance());
            }
            boolean returnToMenu = Console.PromptForYesNo("Would you like to return to previous menu?");
            if (returnToMenu)
                return;
        }


    }
    public static void displayDeposits(){
        while(true){
            System.out.println("All Deposits");
            System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", "Date", "Time", "Description", "Vendor", "Amount");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            for (Transaction t : transactions) {
                if (t.getAmount() > 0)
                    System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                            t.getTime(),
                            t.getDescription(),
                            t.getVendorName(),
                            t.getAmount());
            }
            boolean returnToMenu = Console.PromptForYesNo("Would you like to return to previous menu?");
            if (returnToMenu)
                return;
        }
    }
    public static void displayPayments(){
        while(true){
            System.out.println("All Payments");
            System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", "Date", "Time", "Description", "Vendor", "Amount");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            for (Transaction t : transactions) {
                if (t.getAmount() < 0)
                    System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                            t.getTime(),
                            t.getDescription(),
                            t.getVendorName(),
                            t.getAmount());
            }
            boolean returnToMenu = Console.PromptForYesNo("Would you like to return to previous menu?");
            if (returnToMenu)
                return;
        }
    }

    /**
     * Third level Menu
     * handles all functionality related to filtering reports
     * */
    public static void reportScreen(){
        do{
            try {
                printStarsToTransitionMenu();
                System.out.println("Welcome to Reports menu!");
                System.out.println("1) Month To Date");
                System.out.println("2) Filter By Month");
                System.out.println("3) Previous Month");
                System.out.println("4) Year To Date");
                System.out.println("5) Previous Year");
                System.out.println("6) Search By Vendor");
                System.out.println("0) Back");

                int reportScreenChoice = Console.PromptForByte("\nEnter your selection: ");

                switch (reportScreenChoice) {
                    case 1:
                        monthToDate();
                        break;
                    case 2:
                        filterByMonth();
                        break;
                    case 3:
                        previousMonth();
                        break;
                    case 4:
                        yearToDate();
                        break;
                    case 5:
                        previousYear();
                    case 6:
                        searchByVendor();
                    case 0:
                        return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " <----- Error. Please Try Again");
            }
        }while(true);
    }
    public static void monthToDate(){
        System.out.println("Month To Date");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        LocalDateTime monthNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        String compareMonth = monthNow.format(formatter);

        for(Transaction t: transactions){
            if(t.getDate().contains(compareMonth))
                System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount());
        }
    }
    public static void previousMonth(){
        System.out.println("Previous Month");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        LocalDateTime monthNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        String formatMonth = monthNow.format(formatter);
        int monthNumber = LocalDateTime.now().getMonthValue()-1;
        formatMonth = String.format("%d",monthNumber);

        for(Transaction t: transactions){
            if(t.getDate().contains(formatMonth)){
                System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount());
            }
        }
    }
    public static void filterByMonth(){
        String query = Console.PromptForString("Enter a month you'd like to view transactions for: ");
        for(Transaction t: transactions){
            String[] dateToConvert = t.getDate().split("-");
            String monthToQuery = dateToConvert[1];
            if(query.equalsIgnoreCase(monthToQuery)){
                System.out.println("Transactions for " + query);
                System.out.println("----------------------------------------------------------------------------------------------------------");

                System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount());
            }

        }
    }
    public static void yearToDate(){
        System.out.println("Year To Date");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        LocalDateTime yearNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY");
        String compareYear = yearNow.format(formatter);

        for(Transaction t: transactions){
            if(t.getDate().contains(compareYear))
                System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount());
        }
    }
    public static void previousYear(){
        System.out.println("Previous Year");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        LocalDateTime yearNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY");
        String compareYear = yearNow.format(formatter);
        int yearFormat = LocalDateTime.now().getYear() - 1;
        compareYear = String.format("%d",yearFormat);

        for(Transaction t: transactions){
            if(t.getDate().contains(compareYear))
                System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount());
        }
    }
    public static void searchByVendor(){
        String query = Console.PromptForString("Enter the name of the vendor you wish you view history with:");
        System.out.println("Transactions History: " + query.toUpperCase());
        for(Transaction t: transactions){
            if(query.equalsIgnoreCase(t.getVendorName())){
                System.out.println("----------------------------------------------------------------------------------------------------------");

                System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount());
            }
            }
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