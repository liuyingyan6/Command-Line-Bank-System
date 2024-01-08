package org.yingyan.service.impl;

import org.yingyan.entity.Account;
import org.yingyan.entity.TransactionRecord;
import org.yingyan.service.AccountService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the AccountService interface.
 */
public class AccountServiceImpl implements AccountService {

    // List to store accounts
    private List<Account> accounts = new ArrayList<>();
    // Store statistics info
    private static int accountCounts = 0;
    private static int transactionCounts = 0;

    /**
     * Initializes accounts with sample data.
     */
    @Override
    public void initializeAccounts() {
        Account account1 = new Account("Taylor Smith", 0.0, new ArrayList<>());
        Account account2 = new Account("Jane", 0.0, new ArrayList<>());
        accounts.add(account1);
        accounts.add(account2);
        deposit(account1, 1000.00);
        deposit(account2, 8888.0);
        withdrawal(account1, 100.0);
        withdrawal(account2, 2000.0);
        transfer(account1, account2, 100.0);
        accountCounts = accounts.size();
        transactionCounts += account1.getTransactionRecord().size() + account2.getTransactionRecord().size();
    }


    /**
     * Finds an account by username.
     *
     * @param username The username to search for.
     * @return The found account or null if not found.
     */
    @Override
    public Account findAccountByUsername(String username) {
        return accounts.stream()
                .filter(acc -> acc.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getTransactionCounts() {
        return transactionCounts;
    }

    @Override
    public int getAccountCounts() {
        return accountCounts;
    }

    /**
     * Retrieves a list of all account usernames.
     *
     * @return List of account usernames.
     */
    @Override
    public List<String> getAllAccountNames() {
        List<String> names = new ArrayList<>();
        for (Account acc : accounts) {
            names.add(acc.getUsername());
        }
        return names;
    }

    /**
     * Gets the balance of an account.
     *
     * @param account The account to retrieve the balance for.
     * @return The account balance.
     */
    @Override
    public Double getAccountBalance(Account account) {
        return account.getBalance();
    }

    /**
     * Gets the transaction records for an account.
     *
     * @param account The account to retrieve transaction records for.
     * @return List of transaction records.
     */
    @Override
    public List<TransactionRecord> getAccountTransactionRecord(Account account) {
        return account.getTransactionRecord();
    }

    /**
     * Transfers money from one account to another.
     *
     * @param fromAccount The account from which money is transferred.
     * @param toAccount   The account to which money is transferred.
     * @param amount      The amount of money to transfer.
     * @return A string indicating the result of the transaction.
     */
    @Override
    public String transfer(Account fromAccount, Account toAccount, Double amount) {
        if (fromAccount.getBalance() < amount) {
            return "Sorry, the payer doesn't have enough balance";
        }

        TransactionRecord newTransaction = new TransactionRecord(fromAccount.getUsername(), toAccount.getUsername(), amount, "Transfer", new Date());

        updateBalanceAndTransactionRecords(fromAccount, newTransaction, fromAccount.getBalance() - amount);

        updateBalanceAndTransactionRecords(toAccount, newTransaction, toAccount.getBalance() + amount);
        transactionCounts += 2;

        return "Transaction finished";
    }

    /**
     * Updates the balance and transaction records for an account.
     *
     * @param account        The account to update.
     * @param newTransaction The new transaction record.
     * @param balance        The new account balance.
     */
    private static void updateBalanceAndTransactionRecords(Account account, TransactionRecord newTransaction, double balance) {
        List<TransactionRecord> toUserTransactionRecords = new ArrayList<>(account.getTransactionRecord());
        toUserTransactionRecords.add(newTransaction);
        account.setTransactionRecord(toUserTransactionRecords);
        account.setBalance(balance);
    }


    /**
     * Removes an account from the list.
     *
     * @param account The account to remove.
     */
    @Override
    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    /**
     * Adds a new account to the list.
     *
     * @param username The username for the new account.
     * @param balance  The initial balance for the new account.
     */
    @Override
    public void addAccount(String username, Double balance) {
        accounts.add(new Account(username, balance, new ArrayList<>()));
    }

    /**
     * Processes a withdrawal for an account.
     *
     * @param account The account from which to withdraw money.
     * @param amount  The amount of money to withdraw.
     * @return A string indicating the result of the withdrawal.
     */
    @Override
    public String withdrawal(Account account, Double amount) {
        if (account.getBalance() < amount) {
            return "Sorry, the account doesn't have enough balance";
        } else {
            TransactionRecord withdrawalRecord = new TransactionRecord(account.getUsername(), null, amount, "Withdrawal", new Date());
            updateBalanceAndTransactionRecords(account, withdrawalRecord, account.getBalance() - amount);
            transactionCounts ++;
        }
        return "Withdrawn successfully.";
    }

    /**
     * Processes a deposit for an account.
     *
     * @param account The account to which to deposit money.
     * @param amount  The amount of money to deposit.
     */
    @Override
    public void deposit(Account account, Double amount) {
        TransactionRecord depositRecord = new TransactionRecord(null, account.getUsername(), amount, "Deposit", new Date());
        updateBalanceAndTransactionRecords(account, depositRecord, account.getBalance() + amount);
        transactionCounts ++;
    }
}
