package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Accounts {
    static final String fileName = "transaction.csv";
    static ArrayList<Transaction> transactions = initializeTransactions();
    private int accountID;
    private User user;
    //private ArrayList<Transaction> transactions;

    public Accounts(){
        this.accountID = 0;
        this.user = null;
    }

    public Accounts(int accountID, User user, ArrayList<Transaction> transactions) {
        this.accountID = accountID;
        this.user = user;
        this.transactions = transactions;
    }

    public static User createUser(){
        String holder = Console.PromptForString("Enter the full name of the account holder: ");
        String username = Console.PromptForString("Enter a username: ");
        String email = Console.PromptForString("Enter your email: ");
        String phoneNumber = Console.PromptForString("Enter your phone number: ");

        User user = new User(holder, username, email, phoneNumber);
        return user;
    }

    public Accounts createAccount(){
        accountID++;
        User user = Accounts.createUser();
        user.toFormat();
        return new Accounts(accountID,user,transactions);
    }

    public int getAccountID() {
        return accountID;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "\naccountID=" + accountID +
                ", \nuser=" + user.toString() +
                ", \ntransactions=" + transactions +
                '}';
    }

    /*public void accountScreen(){
        printStarsToTransitionMenu();
        System.out.println("Welcome to Accounting Ledger Application!");
        System.out.println("1) Validate user info");
        System.out.println("2) Create user account");

        int choice = Console.PromptForInt("Enter a selection:");
        if(choice==1)
            Accounts.validateUser();
        else if (choice==2) {
            Accounts acounts = new Accounts(accountID++,Accounts.createUser(), Accounts.initializeTransactions());
            System.out.println(acounts.toString());
        }
        else
            System.out.println("Invalid option");


    }*/

    /** Initializes my arraylist of transactions,
     * reads initial data from data file
     */
    /*public static ArrayList<Transaction> initializeTransactionsWithNewAccount(){
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
    }*/
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

    /**
     *      First level menu functions
     * */

    /**
     *      addDeposit() method prompts users to enter required fields necessary for a deposit
     *      to be successfully created. That information is then used to create a new instance of a transaction
     *      that new transaction is then add to the existing transaction ArrayList and written to a CSV file
     * */
    public void addDeposit() {
        boolean isAnotherDeposit = false;
        do{
            try {
                printStarsToTransitionMenu();
                boolean isAddDeposit = Console.PromptForYesNo("Would you like to add a deposit? ");
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
                String timeStampFormatter = dateTime.format(formatter);
                String[] timeStamp  =  timeStampFormatter.split(" ");   //splits date and time into 2 separate fields

                //      handles what to do if a user would like to make another deposit afterward
                if (isAddDeposit) {
                    String transactionDescription = Console.PromptForString("Enter a description for this transaction: ");
                    String transactionVendor = Console.PromptForString("Enter the name of the vendor making this deposit: ");
                    double transactionPrice = Console.PromptForDouble("Enter a price: ");
                    double transactionBalance = 0;
                    for (Transaction t : transactions)
                        transactionBalance = t.getRunningBalance();
                    //      when new transaction is made, the runningBalance must be added to the current
                    //      transaction price to reflect the change in balance after
                    Transaction transaction = new Transaction(timeStamp[0],timeStamp[1], transactionDescription,transactionVendor,  transactionPrice,transactionBalance+transactionPrice );
                    transactions.add(transaction);
                    writeTransactionToCSV();//      Acts as a save data/write data as the transaction ArrayList depends on the CSV data
                }
                isAnotherDeposit = Console.PromptForYesNo("\nWould you like to make another deposit " + getUser().getFullName() + "?");
                if(!isAnotherDeposit) return;

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + "<---- Make sure your input is numeric.");

            }
        }while (true);
    }
    /**
     *      makePayment() method is essentially the same as the addDeposit method, only difference is payments
     *      are subtracted from the running balance, so they must be written to the CSV differently to reflect that fact
     * */
    public void makePayment(){
        boolean isAnotherPayment = false;
        do{
            try {
                printStarsToTransitionMenu();
                boolean isAddPayment = Console.PromptForYesNo("Would you like to make a payment? ");
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
                String timeStampFormatter = dateTime.format(formatter);
                String[] timeStamp  =  timeStampFormatter.split(" ");

                if (isAddPayment) {
                    String transactionDescription = Console.PromptForString("Enter a description for this transaction: ");
                    String transactionVendor = Console.PromptForString("Enter the name of the vendor receiving this payment: ");
                    double transactionPrice = Console.PromptForDouble("Enter a price: ");
                    double transactionBalance = 0;
                    for(Transaction t: transactions)
                        transactionBalance = t.getRunningBalance();
                    //      transaction price is multiplied by -1 to convert it to a negative number to reflect money spent,
                    //      the running balance then reflects the difference after the transaction price is subtracted
                    Transaction transaction = new Transaction(timeStamp[0],timeStamp[1],transactionDescription,transactionVendor, transactionPrice*-1.0,transactionBalance-transactionPrice);
                    transactions.add(transaction);
                    writeTransactionToCSV();
                }
                isAnotherPayment = Console.PromptForYesNo("\nWould you like to make another payment " + getUser().getFullName() + "?");


            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + "<---- Make sure your input is numeric.");

            }
        }while (isAnotherPayment);
    }
    /**
     *      writeTransactionToCSV() acts as a save file and write data file,
     *      each transaction is formatted and written/saved with each call to the method
     * */
    public static void writeTransactionToCSV() {
        try {

            FileWriter fileWriter = new FileWriter(fileName );
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
     *      Second level menu
     *      handles all functionality related to filtering the ledger
     */
    public void ledger(){
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
    /**
     *      displays a filtered result based on user query
     *      extends second level menu
     * */
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
                if (t.getAmount() > 0) //   if the amount is not negative (reflecting deposits) then format it and display the following
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
                if (t.getAmount() < 0)//    if amount is negative (money spent, reflecting a payment) then format and display the following.
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
     *      Third level Menu
     *      handles all functionality related to filtering reports
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
                        break;
                    case 6:
                        searchByVendor();
                        break;
                    case 0:
                        return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " <----- Error. Please Try Again");
            }
        }while(true);
    }
    /**
     *      monthToDate() returns all entries of the current month up to the current date
     * */
    public static void monthToDate(){
        System.out.println("Month To Date");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        LocalDateTime monthNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        String compareMonth = monthNow.format(formatter);//format the current month

        for(Transaction t: transactions){
            if(t.getDate().contains(compareMonth)) //if a transaction date contains that formatted month, then display the following
                System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount());
        }
    }
    /**
     *      previousMonth() returns all entries from the previous month, this is done by
     *      using the current month and subtracting 1
     * */
    public static void previousMonth(){
        System.out.println("Previous Month");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        LocalDateTime monthNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        String formatMonth = monthNow.format(formatter);
        int monthNumber = LocalDateTime.now().getMonthValue()-1; //     same idea as monthToDate except subtract one from the current month
        formatMonth = String.format("%d",monthNumber);

        for(Transaction t: transactions){
            if(t.getDate().contains(formatMonth)){//    if transaction date contains the previous month (current month - 1), display the following
                System.out.printf("| %15s | %15s | %-30s | %15s | %15s |\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendorName(),
                        t.getAmount());
            }
        }
    }
    /**
     *      filterByMonth() is the first option of the third level menu,
    *       where the user and enter a query and that query is then checked
    *       then displayed if matched
    * */
    public static void filterByMonth(){
        String query = Console.PromptForString("Enter a month you'd like to view transactions for: ");
        for(Transaction t: transactions){
            String[] dateToConvert = t.getDate().split("-");//      split transaction date into 3 parts: year, month, day then add to array
            String monthToQuery = dateToConvert[1];//   grab the month which is the 2nd position, index 1 of the array
            if(query.equalsIgnoreCase(monthToQuery)){ //    compare the query, if it matches, display the following
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
    /**
     *      yearToDate() is the equivalent of monthToDate() but returning all entries of the current year
     * */
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
    /**
     *      previousYear() is also the equivalent of previousMonth(), but for filtering out and displaying the previous year
    * */
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
    /**
     *      searchByVendor() is a modified version of filterByMonth, difference being instead of filtering by month,
     *      the vendor is used as a conditional query
     * */
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
    /**
     *      A hardcoded Smiley face is used to transition to and from different level menus,
     *      this was used to compensate for the fact I could not figure out how to clear the console
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