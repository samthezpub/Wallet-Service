package org.example.out.Service.Impl;

import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Models.Player;
import org.example.out.Models.Transaction;
import org.example.out.Repository.PlayerDAO;
import org.example.out.Repository.TransactionDAO;
import org.example.out.Service.PlayerService;
import org.example.out.Utils.BalanceResult;

import java.util.List;
import java.util.Set;

public class PlayerServiceImpl implements PlayerService {
    private static PlayerDAO playerDAO;


    /**
     * Добавляет деньги на счёт пользователя
     * @param id - id игрока
     * @param value - сколько вывести
     * @throws TransactionException
     */
    @Override
    public void deposit(int id, double value) throws TransactionException {
        if (value <= 0){
            throw new TransactionException("Вы указали недопустимую сумму!");
        }

        Player player = playerDAO.get(id).get();

        BalanceResult playerAccounts = playerDAO.get(id).get().getAccounts();
        BalanceResult result = new BalanceResult(playerAccounts.getBalance() + value, playerAccounts.getCreditBalance());

        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        transactionService.save(new Transaction(id, 0, playerAccounts, result));

        player.setAccounts(result);
        playerDAO.update(player);
    }

    /**
     * Выводит деньги со счёта пользователя
     * @param id - id игрока
     * @param value - сколько вывести
     * @throws TransactionException
     */
    @Override
    public void withdraw(int id, double value) throws TransactionException {


        if (value <= 0){
            throw new TransactionException("Вы указали недопустимую сумму!");
        }

        Player player = playerDAO.get(id).get();
        if (player.getAccounts().getBalance() - value < 0){
            throw new TransactionException("Недостаточно денег на балансе!");
        }

        BalanceResult playerAccounts = player.getAccounts();

        BalanceResult result = new BalanceResult(playerAccounts.getBalance()-value, playerAccounts.getCreditBalance());
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        transactionService.save(new Transaction(id, 1, playerAccounts, result));

        player.setAccounts(result);
        playerDAO.update(player);

    }

    /**
     * Добавляет в creditMoney игрока заданное значение
     * @param id id игрока
     * @param value сколько вывести
     * @throws TransactionException
     */
    @Override
    public void takeCredit(int id, double value) throws TransactionException {
        if (value <= 0){
            throw new TransactionException("Вы указали недопустимую сумму!");
        }

        Player player = playerDAO.get(id).get();

        BalanceResult playerAccounts = playerDAO.get(id).get().getAccounts();
        BalanceResult result = new BalanceResult(playerAccounts.getBalance() + value, playerAccounts.getCreditBalance() + value);

        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        transactionService.save(new Transaction(id, 2, playerAccounts, result));

        player.setAccounts(result);
        playerDAO.update(player);
    }

    /**
     * Добавляет запись в базе данных, регистрирует пустого Player
     * @param login логин
     * @param password пароль
     * @throws LoginException если логин уже существует
     * @see org.example.out.Models.Player
     */
    @Override
    public void register(String login, String password) throws LoginException {
        List<Player> players = playerDAO.getAll();
        for (Player player : players) {
            if (player.getLogin().equals(login)) {
                throw new LoginException("Пользователь с таким логином уже существует!");
            }
        }

        Player player = new Player();
        player.setLogin(login);
        player.setPassword(password);


        playerDAO.save(player);
    }

    /**
     * Проверяем есть ли пользователь в базе данных
     * @param login логин
     * @param password пароль
     * @return playerId полученный id
     * @throws NotFindException если пользователь не найден
     */
    @Override
    public int logIn(String login, String password) throws NotFindException {
        List<Player> playerList = playerDAO.getAll();
        for (Player player:playerList){
            if (player.getLogin().equals(login) && player.getPassword().equals(password)){
                return player.getId();
            }
        }
        throw new NotFindException("Пользователь не найден!");
    }


    /**
     * Получает Accounts указанного игрока по id
     * @param id - игрок
     * @return BalanceResult
     */
    @Override
    public BalanceResult getBalance(int id) {
        BalanceResult balanceResult = playerDAO.get(id).get().getAccounts();

        return balanceResult;
    }


    public static void setPlayerDAO(PlayerDAO playerDAO) {
        PlayerServiceImpl.playerDAO = playerDAO;
    }
}
