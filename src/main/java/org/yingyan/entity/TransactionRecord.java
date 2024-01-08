package org.yingyan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord {
    private String fromUser;
    private String toUser;
    private Double amount;
    private String transactionType; // Transfer, Deposit, Withdrawal
    private Date timestamp;
}
