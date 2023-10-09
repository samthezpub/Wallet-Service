package org.example.out.Dispatchers;

import lombok.Data;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Interfaces.IPlayer;
import org.example.out.Utils.BalanceResult;

import java.util.Set;

@Data
public class Player implements IPlayer {
    private static Integer nextId = 0;

    private Integer id;
    private BalanceResult accounts;

    private String login;
    private String password;
    private boolean isLogined;

    public Player() {
        id = nextId++;

        this.accounts = new BalanceResult(0,0);
        this.isLogined = false;
    }




    /**
     * Метод, проверяющий авторизован ли пользователь
     *
     * @throws LoginException если IsLoggined = false
     */
    public void checkIsLogin() throws LoginException {
        if (!isLogined) {
            throw new LoginException("Вы не вошли в аккаунт!");
        }
    }

    /**
     * Метод, добавляющий деньги
     *
     * @param value количество денег, которое мы хотим добавить
     * @throws LoginException если isLoggined false шлёт исключение
     */
    @Override
    public void deposit(double value) {
        try {
            checkIsLogin();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        accounts = Dispatch.deposit(this, value);
    }

    /**
     * Снимает деньги с баланса
     *
     * @param value Сколько денег снять
     * @throws TransactionException если на балансе недостаточно денег, шлёт исключение
     * @throws LoginException       если пользователь не авторизовался шлёт исключение
     */
    @Override
    public void withdraw(double value) {
        try {
            checkIsLogin();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        try {
            accounts = Dispatch.withdrawPlayerBalance(this, value);
        } catch (TransactionException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Добавляет деньги к балансу, помечает операцию как CREDIT
     *
     * @param value количество денег, которые мы хотим кредитовать
     * @throws LoginException если пользователь не логирован
     *
     */
    @Override
    public void takeCredit(double value) {
        try {
            checkIsLogin();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        try {
            accounts = Dispatch.takeCredit(this, value);

        } catch (TransactionException e) {
            System.err.println(e.getMessage());
        }


    }

    @Override
    public void register(String login, String password) {
        Dispatch.registerPlayer(this, login, password);
    }


    @Override
    public void logIn(String login, String password) {
        try {
            isLogined = Dispatch.authenticationPlayer(login, password);
        } catch (NotFindException e) {
            isLogined = false;
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void logOut() {
        isLogined = false;
    }

    @Override
    public BalanceResult getBalance() {

        return Dispatch.getBalance(this);
    }

    @Override
    public Set<Transaction> getTransactions() {
        try {
            checkIsLogin();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        return Dispatch.getTransactionsByPlayerId(this.id);
    }


}
