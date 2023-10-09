package org.example.out.Dispatchers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.out.Enums.TransactionTypeEnum;
import org.example.out.Utils.BalanceResult;

import java.util.Date;

@AllArgsConstructor
@Data
public class Transaction {
    private static Integer nextId = 0;

    private Integer id;
    private Integer playerId;
    private TransactionTypeEnum type;
    private BalanceResult accounts;
    private BalanceResult balanceAfterOperation;
    private Date date;

    public Transaction(Integer playerId, TransactionTypeEnum type, BalanceResult accounts, BalanceResult balanceAfterOperation) {
        id = nextId++;
        this.playerId = playerId;
        this.type = type;
        this.accounts = accounts;
        this.balanceAfterOperation = balanceAfterOperation;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return  "\nid= " + id +
                ", Тип: " + type +
                ",\n Баланс перед операцией: " + accounts.getBalance() +
                ", Кредит перед операцией: " + accounts.getCreditBalance() +
                ",\n Баланс после операции: " + balanceAfterOperation.getBalance() +
                ", Кредит после операции: " + balanceAfterOperation.getCreditBalance() +
                ",\n Точная дата: " + date;
    }
}
