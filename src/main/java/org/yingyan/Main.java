package org.yingyan;

import org.yingyan.entity.Account;
import org.yingyan.entity.TransactionRecord;
import org.yingyan.service.AccountService;
import org.yingyan.service.impl.AccountServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in); // for user input
    static AccountService accountService = new AccountServiceImpl();


    public static void main(String[] args) {
        accountService.initializeAccounts(); // initialize accounts with sample data
        while (true) {
            showMenu();
            int opt = scanner.nextInt();
            scanner.nextLine();
            switch (opt) { // perform actions based on user selection
                case 1:
                    showAllAccountNames();
                    break;
                case 2:
                    showAccountBalance();
                    break;
                case 3:
                    showAccountTransactionRecord();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    deposit();
                    break;
                case 6:
                    withdrawal();
                    break;
                case 7:
                    removeAccount();
                    break;
                case 8:
                    addAccount();
                    break;
                case 9:
                    showStatistics();
                    break;
            }
            // ask the user if they want to continue
            System.out.println("*********************************");
            System.out.println("Continue?(y/n)");
            if (scanner.next().equalsIgnoreCase("n")) {
                System.out.println("Thank you for using our system!");
                return;
            }
        }
    }

    // extra functionality showing total account numbers and total transaction amount
    private static void showStatistics() {
        System.out.println("**********STATISTICS*************");
        System.out.printf("Total Accounts: %d\n", accountService.getAccountCounts());
        System.out.printf("Total Transactions: %d\n", accountService.getTransactionCounts());
    }

    // main menu options
    public static void showMenu() {
        System.out.println("****************************************");
        System.out.println("********** WELCOME TO MY BANK **********");
        System.out.println("Enter 1: Check list of account names");
        System.out.println("Enter 2: Check account balance");
        System.out.println("Enter 3: Check account transaction record");
        System.out.println("Enter 4: Transfer money");
        System.out.println("Enter 5: Deposit");
        System.out.println("Enter 6: Withdrawal");
        System.out.println("Enter 7: Remove account");
        System.out.println("Enter 8: Add account");
        System.out.println("Enter 9: Show Statistics");
    }

    // list all bank accounts' names in the system
    private static void showAllAccountNames() {
        List<String> names = accountService.getAllAccountNames();
        System.out.println("******** ACCOUNT NAMES **********");
        for (String name : names) {
            System.out.println(name);
        }
    }

    // view an individual bank account with its balance
    private static void showAccountBalance() {
        System.out.println("************* BALANCE ***********");

        System.out.println("Please enter username:");
        String username = scanner.nextLine();
        Account acc = accountService.findAccountByUsername(username);
        if (acc == null) {
            System.out.println("Sorry, username does not exist.");
            return;
        }
        Double balance = accountService.getAccountBalance(acc);
        System.out.printf("The balance of %s is: £%s\n", username, balance);
    }

    // view an individual bank account with its list of transactions
    private static void showAccountTransactionRecord() {
        System.out.println("***** TRANSACTION RECORD ********");
        System.out.println("Please enter username: ");
        String username = scanner.nextLine();
        Account acc = accountService.findAccountByUsername(username);
        if (acc == null) {
            System.out.println("Sorry, username does not exist.");
            return;
        }

        List<TransactionRecord> records = accountService.getAccountTransactionRecord(acc);
        if (records.size() == 0) {
            System.out.printf("No records related to %s's account.\n", username);
        } else {
            records.stream().forEach(System.out::println);
        }
    }

    // create new transactions - transfer record
    private static void transfer() {
        System.out.println("************ TRANSFER ***********");
        System.out.println("Please enter username to pay:");
        String fromUsername = scanner.nextLine();

        // check if username exists
        Account fromAcc = accountService.findAccountByUsername(fromUsername);
        if (fromAcc == null) {
            System.out.println("Sorry, username does not exist.");
            return;
        }

        System.out.println("Please enter username to receive:");
        String toUsername = scanner.nextLine();

        Account toAcc = accountService.findAccountByUsername(toUsername);
        if (toAcc == null) {
            System.out.println("Sorry, username does not exist.");
            return;
        }

        // check if input is valid
        Double amount = getValidAmountInput();
        if (amount < 0) {
            System.out.println("Amount must be bigger than 0.");
            return;
        }

        String result = accountService.transfer(fromAcc, toAcc, amount);
        System.out.println(result);
    }

    // create new transactions - deposit record
    private static void deposit() {
        System.out.println("************* DEPOSIT ***********");
        System.out.println("Please enter username:");
        String username = scanner.nextLine();
        Account acc = accountService.findAccountByUsername(username);
        if (acc == null) {
            System.out.println("Sorry, username does not exist.");
            return;
        }
        Double amount = getValidAmountInput();

        accountService.deposit(acc, amount);
        System.out.printf("£%s deposited into %s's account.\n", amount, username);
    }

    // create new transactions - withdrawal record
    private static void withdrawal() {
        System.out.println("*********** WITHDRAWAL **********");
        System.out.println("Please enter username:");
        String username = scanner.nextLine();

        Account acc = accountService.findAccountByUsername(username);
        if (acc == null) {
            System.out.println("Sorry, username does not exist.");
            return;
        }
        Double amount = getValidAmountInput();

        String result = accountService.withdrawal(acc, amount);
        System.out.println(result);
    }


    // create new bank account
    private static void addAccount() {
        System.out.println("******* CREATING ACCOUNT ********");
        System.out.println("Please enter username:");
        String username = scanner.nextLine();
        Account acc = accountService.findAccountByUsername(username);
        if (acc != null) {
            System.out.println("Sorry, this username already exists.");
            return;
        }
        System.out.println("Please enter balance:");
        Double balance = scanner.nextDouble();
        accountService.addAccount(username, balance);
        System.out.println("Account created successfully.");
    }

    // remove bank account
    private static void removeAccount() {
        System.out.println("******* REMOVING ACCOUNT ********");
        System.out.println("Please enter username:");
        String username = scanner.nextLine();
        Account acc = accountService.findAccountByUsername(username);
        if (acc == null) {
            System.out.println("Sorry, username does not exist.");
            return;
        }
        accountService.removeAccount(acc);
        System.out.printf("%s has been removed.", username);
    }

    // extracted method to validate input for amount
    private static Double getValidAmountInput() {
        try {
            System.out.println("Please enter amount:");
            return scanner.nextDouble();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
            return getValidAmountInput();
        }
    }
}