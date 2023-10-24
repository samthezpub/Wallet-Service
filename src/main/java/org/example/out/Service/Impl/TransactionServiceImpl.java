package org.example.out.Service.Impl;

import org.example.out.Repository.TransactionDAO;
import org.example.out.Models.Transaction;
import org.example.out.Service.TransactionService;

import java.util.Date;
import java.util.Set;

public class TransactionServiceImpl implements TransactionService {
    private static TransactionDAO transactionDAO;

    public TransactionServiceImpl() {
    }

    public TransactionServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    /**
     *  Ищет транзакции по id игрока
     * @param id id игрока
     * @return Set<Transaction> LinkedHashSet транзакций
     */
    @Override
    public Set<Transaction> findTransactionsByPlayerId(Integer id) {
        return transactionDAO.findTransactionsByPlayerId(id);
    }

    /**
     * Добавляет транзакцию в базу данных
     * @param transaction транзакция
     */
    @Override
    public void save(Transaction transaction) {
        Date date = new Date();

        transaction.setDate(date);
        transactionDAO.save(transaction);
    }

    public static void setTransactionDAO(TransactionDAO transactionDAO) {
        TransactionServiceImpl.transactionDAO = transactionDAO;
    }
}
