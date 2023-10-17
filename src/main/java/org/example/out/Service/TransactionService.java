package org.example.out.Service;

import org.example.out.Models.Transaction;

import java.util.Set;

public interface TransactionService {
    /**
     *  Ищет транзакции по id игрока
     * @param id id игрока
     * @return Set<Transaction> LinkedHashSet транзакций
     */
    Set<Transaction> findTransactionsByPlayerId(Integer id);

    /**
     * Добавляет транзакцию в базу данных
     * @param transaction транзакция
     */
    void save(Transaction transaction);

}
