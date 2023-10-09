package org.example.out.Common;

import lombok.Data;
import org.example.out.Dispatchers.Transaction;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
public class Audit {
    private static List<Transaction> transactions = new ArrayList<>();

    public static void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }



    public static Set<Transaction> findTransactionsByPlayerId(Integer playerId){
        Set<Transaction> playerTransactions = new LinkedHashSet<>();

        for (int i = 0; i < transactions.size(); i++){
            Transaction transaction = transactions.get(i);
            if (transaction.getPlayerId() == playerId){
                playerTransactions.add(transaction);
            }
        }

        return playerTransactions;
    }

    public static List<Transaction> getTransactions() {
        return transactions;
    }
    public static void setTransactions(List<Transaction> transactions) {
        Audit.transactions = transactions;
    }
}
