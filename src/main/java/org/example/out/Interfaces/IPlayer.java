package org.example.out.Interfaces;

import org.example.out.Dispatchers.Transaction;
import org.example.out.Utils.BalanceResult;

import java.util.List;

public interface IPlayer {
    BalanceResult getBalance();
    List<Transaction> getTransactions();
    void deposit(double value);
    void withdraw(double value);
    void takeCredit(double value);

    void register(String login, String password);
    void logIn(String login, String password);
    void logOut();


}
