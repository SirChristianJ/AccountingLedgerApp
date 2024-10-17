# AccountingLedgerApp Overview
**YearUp Java Development Capstone 1**
**The primary functionalities required for this capstone were the following:**
# Table of Contents

1. [AccountingLedgerApp](#accountingledgerapp)
2. [YearUp Java Development Capstone 1](#yearup-java-development-capstone-1)
3. [Primary Functionalities](#primary-functionalities)
   - [Opening Menu Options](#1-opening-menu-should-prompt-a-user-with-a-menu-consisting-of-4-options)
   - [Ledger Viewing Options](#2-viewing-the-ledger-should-then-prompt-the-user-with-a-second-level-menu-consisting-of-the-following-options)
   - [Report Viewing Options](#3-selecting-to-display-reports-then-prompts-the-user-with-a-third-level-menu)
4. [The language and classes used](#about-the-project)
5. [Challenges Faced and Realizations](#challenges/-what-i-learned)
6. [Room for improvement](#improvements-to-be-made)







   

## 1. Opening menu should prompt a user with a menu consisting of 4 options:
!(/StaticAssets/capstonewalkthrough1.jpg)








  
## 2. Viewing the ledger should then prompt the user with a second level menu consisting of the following options:
 - Display All Entries
 - Display All Deposits
 - Display All Payments
 - Display Reports
 - Return to previous Menu









## 3. Selecting to display reports then prompts the user with a third level menu:
 - MonthToDate transactions
 - View Previous Month transactions
 - Filter transactions by month 
 - YearToDate transactions
 - Previous Year transactions
 - Filter transactions by vendor
 - Return to previous Menu









# About The Project
- Built with: Java , 3 Classes were created:
1. Transaction class - This class would maintain all the basic information included with a transaction
2. User class - This class was originally intended to pass user info into the Accounts class to simultaneously create an account once the user is created. But at the end I just opted for just using it in Accounts to pass the users name throughout the menus.
3. Accounts class - This class was suppose to tie everything together referencing a user and an arraylist of transactions. I initially intended for the class to be able to create multiple accounts to add to the flexibility of the application but because my data for the transactions were from a single CSV file, I would hypothetically have to create a new individual CSV file for each new Account in order to track the data correctly. Realizing this approach would make more sense if using a database, I opted for using the accounts class to encapsulate all the methods that were originally in the main file to make the main file shorter.
- Additional Console class which was used as utility for prompting user questions via Java's Scanner Class from previous exercises









# Challenges/ What I learned
As stated in the about section, my curiosity led me to attempt to add an extra layer of flexibility to the project by allowing the option to create multiple users but as I would proceed to get deep into implementing this approach I ran into some challenges:
1. I created an ArrayList of transactions within the Accounts class. The transactions ArrayList is initialized by reading a pre exisiting CSV and parsing through the data. This meant that the single CSV maintained the transactions, if I were to tie this to an Account instance, I'd have to create multiple CSV files to track the transactions per user. It was either this or modify the existing CSV to also keep track of an Account ID associated with a transaction and then that will allow the CSV to stay as one file. I plan on implementing the second approach soon just for practice as the first just wasn't practical.
2. I had no issues implementing most of the required funcitonality however, after I implemented them I then came to the realization that I could've made the program more flexible by checking whether a transaction was made under a specific user prior by using the Account ID mentioned in challenege 1, this will allow the program to check if the transaction is the first transaction, let the balance reflect that if a payment is the first transaction, the user has no money so they'll be denied a payment until an initial deposit is made so the runningBalance can be set.









# Improvements To Be Made
1. Prompt user for User fields
2. create an account with user info
3. Create an arraylist of accounts to relate a user with an account ID, add newly created account
4. Write newly created Accounts to an Account CSV that tracks basic account info for validation purposes
5. When creating new transactions, add a field to track the accountID of the initiator
6. AccountID can then be used to filter transaction based on user information inserted in step 1
7. Query if that Account exists (which it should because step 1 prompts user info, step 2 creates an account, step 3 adds the account to the account list and step 4 should write that information to the Account CSV) if it does add the transaction. 
