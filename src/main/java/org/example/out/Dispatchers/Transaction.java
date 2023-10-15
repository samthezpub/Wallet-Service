package org.example.out.Dispatchers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.out.Enums.TransactionTypeEnum;
import org.example.out.Utils.BalanceResult;

import java.util.Date;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (!accounts.equals(that.accounts)) return false;
        if (!balanceAfterOperation.equals(that.balanceAfterOperation)) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        result = 31 * result + accounts.hashCode();
        result = 31 * result + balanceAfterOperation.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

}
