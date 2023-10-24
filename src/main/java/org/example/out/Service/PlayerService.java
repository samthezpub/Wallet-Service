package org.example.out.Service;

import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Models.Transaction;
import org.example.out.Utils.BalanceResult;

import java.util.Set;

public interface PlayerService {
    /**
     * Добавляет деньги на счёт пользователя
     * @param id - id игрока
     * @param value - сколько вывести
     * @throws TransactionException
     */
    void deposit(int id,double value) throws TransactionException;

    /**
     * Выводит деньги со счёта пользователя
     * @param id - id игрока
     * @param value - сколько вывести
     * @throws TransactionException
     */

    void withdraw(int id, double value) throws TransactionException;


    /**
     * Добавляет в creditMoney игрока заданное значение
     * @param id id игрока
     * @param value сколько вывести
     * @throws TransactionException
     */
    void takeCredit(int id, double value) throws TransactionException;

    /**
     * Добавляет запись в базе данных, регистрирует пустого Player
     * @param login логин
     * @param password пароль
     * @throws LoginException
     * @see org.example.out.Models.Player
     */
    void register(String login, String password) throws LoginException;

    /**
     * Проверяем есть ли пользователь в базе данных
     * @param login логин
     * @param password пароль
     * @return playerId полученный id
     * @throws NotFindException если пользователь не найден
     */
    int logIn(String login, String password) throws NotFindException;


    /**
     * Получает Accounts указанного игрока по id
     * @param id - игрок
     * @return BalanceResult
     */
    BalanceResult getBalance(int id);

}
