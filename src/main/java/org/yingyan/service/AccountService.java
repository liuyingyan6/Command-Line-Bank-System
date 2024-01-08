package org.yingyan.service;

import org.yingyan.entity.Account;
import org.yingyan.entity.TransactionRecord;

import java.util.List;

public interface AccountService {
    void initializeAccounts();

    List<String> getAllAccountNames();

    Double getAccountBalance(Account account);

    List<TransactionRecord> getAccountTransactionRecord(Account account);

    String transfer(Account fromAccount, Account toAccount, Double amount);

    void removeAccount(Account account);

    void addAccount(String username, Double balance);

    String withdrawal(Account account, Double amount);

    void deposit(Account account, Double amount);

    Account findAccountByUsername(String username);

    int getTransactionCounts();

    int getAccountCounts();
}
