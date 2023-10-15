package org.example.out.Common;

import org.example.out.Models.Transaction;
import org.example.out.Enums.TransactionTypeEnum;
import org.example.out.Utils.BalanceResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class AuditTest {
    
    @BeforeEach
    public void beforeEach() {
        // Очищаем список транзакций перед каждым тестом
        Audit.getTransactions().clear();
    }

    @Test
    @DisplayName("addTransaction_mustAddTransactionToList")
    public void addTransaction_mustAddTransactionToList() {
        // Создаем новую транзакцию
        Transaction newTransaction = new Transaction(0, TransactionTypeEnum.DEPOSIT,
                new BalanceResult(100, 0), new BalanceResult(200, 0));

        // Добавляем эту транзакцию с помощью метода addTransaction
        Audit.addTransaction(newTransaction);

        // Получаем список всех транзакций
        List<Transaction> allTransactions = Audit.getTransactions();

        // Проверяем, что новая транзакция содержится в списке
        Assertions.assertTrue(allTransactions.contains(newTransaction));
    }

    @Test
    @DisplayName("findTransactionsByPlayerId_mustReturnSetWithTransactions")
    public void findTransactionsByPlayerId_mustReturnSetWithTransactions() {
        // Создаем несколько транзакций с разными playerId
        Transaction transaction1 = new Transaction(0, TransactionTypeEnum.DEPOSIT,
                new BalanceResult(100, 0), new BalanceResult(200, 0));
        transaction1.setPlayerId(1);

        Transaction transaction2 = new Transaction(1, TransactionTypeEnum.WITHDRAW,
                new BalanceResult(300, 200), new BalanceResult(200, 100));
        transaction2.setPlayerId(1);

        Transaction transaction3 = new Transaction(2, TransactionTypeEnum.DEPOSIT,
                new BalanceResult(500, 500), new BalanceResult(600, 700));
        transaction3.setPlayerId(2);

        // Добавляем транзакции в список
        Audit.addTransaction(transaction1);
        Audit.addTransaction(transaction2);
        Audit.addTransaction(transaction3);

        // Вызываем метод findTransactionsByPlayerId для playerId = 1
        Set<Transaction> player1Transactions = Audit.findTransactionsByPlayerId(1);

        // Проверяем, что полученное множество содержит только транзакции с playerId = 1
        Assertions.assertTrue(player1Transactions.contains(transaction1));
        Assertions.assertTrue(player1Transactions.contains(transaction2));
        Assertions.assertFalse(player1Transactions.contains(transaction3));
    }
}
