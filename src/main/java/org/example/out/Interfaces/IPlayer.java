package org.example.out.Interfaces;

import org.example.out.Dispatchers.Transaction;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Utils.BalanceResult;

import java.util.Set;

public interface IPlayer {
    BalanceResult getBalance();
    Set<Transaction> getTransactions();
    void deposit(double value) throws TransactionException;
    void withdraw(double value);
    void takeCredit(double value);

    void register(String login, String password) throws LoginException;
    void logIn(String login, String password);
    void logOut();


}
