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

    private Integer id;
    private BalanceResult accounts;

    private String login;
    private String password;
    private boolean isLogined;

    public Player() {

        this.accounts = new BalanceResult(0,0);
    }

    public Player(Integer id, BalanceResult accounts, String login, String password) {
        this.id = id;
        this.accounts = accounts;
        this.login = login;
        this.password = password;
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
     * @throws RuntimeException если isLoggined false шлёт исключение
     * @throws TransactionException если value <= 0
     */
    @Override
    public void deposit(double value) throws TransactionException {
        try {
            checkIsLogin();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        this.accounts = Dispatch.deposit(this.id, value);
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
            accounts = Dispatch.withdrawPlayerBalance(this.id, value);
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
            accounts = Dispatch.takeCredit(this.id, value);

        } catch (TransactionException e) {
            System.err.println(e.getMessage());
        }


    }

    @Override
    public void register(String login, String password) throws LoginException {
        Dispatch.registerPlayer(login, password);
    }


    @Override
    public void logIn(String login, String password) throws NotFindException {

       Player result = Dispatch.authenticationPlayer(login, password);
       this.id = result.getId();
       this.accounts = result.getAccounts();
       this.login = result.getLogin();
       this.password = result.getPassword();
       this.isLogined = result.isLogined();
    }

    @Override
    public void logOut(Player player) {
        Dispatch.logoutPlayer(this.id);
    }

    @Override
    public BalanceResult getBalance() {

        return Dispatch.getBalance(this.id);
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
