package org.example.out.Models;

import lombok.Data;
import org.example.out.Utils.BalanceResult;

import java.util.Date;

@Data
public class Transaction {
    private static Integer nextId = 0;

    private Integer id;
    private Integer playerId;
    private int typeId;
    private BalanceResult accounts;
    private BalanceResult balanceAfterOperation;
    private Date date;

    public Transaction(Integer playerId, int typeId, BalanceResult accounts, BalanceResult balanceAfterOperation) {
        this.playerId = playerId;
        this.typeId = typeId;
        this.accounts = accounts;
        this.balanceAfterOperation = balanceAfterOperation;
        this.date = new Date();
    }

    @Override
    public String toString() {
        String typeIdString = "";
        switch (typeId){
            case 0:
                typeIdString = "DEPOSIT";
                break;
            case 1:
                typeIdString = "WITHDRAW";
                break;
            case 2:
                typeIdString = "CREDIT";
                break;
        }
        return  "\nid= " + id +
                ", Тип: " + typeIdString +
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
        if (typeId != that.typeId) return false;
        if (!accounts.equals(that.accounts)) return false;
        if (!balanceAfterOperation.equals(that.balanceAfterOperation)) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + accounts.hashCode();
        result = 31 * result + balanceAfterOperation.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

}
