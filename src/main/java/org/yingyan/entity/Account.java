package org.yingyan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String username;
    private Double balance;
    private List<TransactionRecord> transactionRecord;
}
