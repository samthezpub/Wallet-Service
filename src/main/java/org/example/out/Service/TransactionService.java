package org.example.out.Service;

import org.example.out.Models.Transaction;

import java.util.Set;

public interface TransactionService {
    Set<Transaction> findTransactionsByPlayerId(Integer id);
    void save(Transaction transaction);

}
