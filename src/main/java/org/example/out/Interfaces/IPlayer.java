package org.example.out.Interfaces;

import org.example.out.Dispatchers.Dispatch;
import org.example.out.Dispatchers.Player;
import org.example.out.Dispatchers.Transaction;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Utils.BalanceResult;

import java.util.Set;

public interface IPlayer {


    void deposit(Dispatch dispatch, double value) throws TransactionException;

    void withdraw(Dispatch dispatch, double value);

    void takeCredit(Dispatch dispatch, double value);

    void register(Dispatch dispatch, String login, String password) throws LoginException;

    void logIn(Dispatch dispatch, String login, String password) throws NotFindException;

    void logOut(Dispatch dispatch, Player player);

    BalanceResult getBalance(Dispatch dispatch);

    Set<Transaction> getTransactions(Dispatch dispatch);

}
