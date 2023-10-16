package org.example.out.Service;

import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Models.Transaction;
import org.example.out.Utils.BalanceResult;

import java.util.Set;

public interface PlayerService {
    void deposit(int id,double value) throws TransactionException;

    void withdraw(int id, double value) throws TransactionException;

    void takeCredit(int id, double value) throws TransactionException;

    void register(String login, String password) throws LoginException;

    int logIn(String login, String password) throws NotFindException;



    BalanceResult getBalance(int id);

}
