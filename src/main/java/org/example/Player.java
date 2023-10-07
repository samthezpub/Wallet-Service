package org.example;

import lombok.Data;
import org.example.Enum.TransactionTypeEnum;
import org.example.Exception.LoginException;
import org.example.Exception.TransactionException;
import org.example.Interface.IPlayer;

import java.util.List;

@Data
public class Player implements IPlayer {
    private double balance;
    private List<Transaction> transactions;

    private String login;
    private String password;
    private boolean isLogined = false;

    public Player() {
    }

    private void checkIsLogin() {
        try {
            if (!isLogined) {
                throw new LoginException("Вы не вошли в аккаунт!");
            }
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deposit(double value) {
        checkIsLogin();
        balance = balance + value;
    }

    /**
     * Снимает деньги с баланса
     * @param value Сколько денег снять
     * @throws TransactionException если на балансе недостаточно денег, шлёт исключение
     * @throws LoginException если пользователь не авторизовался шлёт исключение
     * @see checkLogin() проверяет авторизирован ли пользователь
     */
    @Override
    public void withdraw(double value) {
        checkIsLogin();
        try {
            if (balance - value >= 0) {
                balance = balance - value;
                new Transaction(TransactionTypeEnum.WITHDRAW, value);
            } else {
                throw new TransactionException("Недостаточно средств на балансе!");
            }
        } catch (TransactionException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public double takeCredit(double value) {
        checkIsLogin();
        return 0;
    }

    @Override
    public void register(String login, String password) {
        Dispatch.registerPlayer(login, password);
    }


    @Override
    public void logIn(String login, String password) {
        isLogined = Dispatch.authenticationPlayer(login, password);
    }

    @Override
    public void logOut() {
        isLogined = false;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }


}
