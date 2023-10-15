package org.example.out.Common;

import lombok.Data;
import org.example.out.Models.Player;
import org.example.out.Models.Transaction;
import org.example.out.Repository.PlayerDAO;
import org.example.out.Repository.TransactionDAO;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Utils.BalanceResult;

import java.util.*;

@Data
public class Dispatch {

    private PlayerDAO playerDAO = new PlayerDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();


    public Set<Transaction> getTransactionsByPlayerId(Integer id) {
        return transactionDAO.findTransactionsByPlayerId(id);
    }

    private void addTransaction(Transaction transaction) {
        transactionDAO.save(transaction);
    }

    public void addPlayer(int id, BalanceResult account, String login, String password) {
        Player player = new Player(id, account, login, password);
        playerDAO.save(player);
    }

    private Optional<Player> findPlayerById(int id) {
        return playerDAO.get(id);
    }

    public void registerPlayer(String login, String password) throws LoginException {
        List<Player> players = playerDAO.getAll();
        for (Player player : players) {
            if (player.getLogin().equals(login)) {
                throw new LoginException("Пользователь с таким логином уже существует!");
            }
        }
        // TODO id sequence
        addPlayer(players.size(), new BalanceResult(0, 0), login, password);
    }


    public Player authenticationPlayer(String login, String password) throws NotFindException {
        List<Player> playersBD = playerDAO.getAll();
        for (Player playerIterable : playersBD) {
            if (playerIterable.getLogin().equals(login) && playerIterable.getPassword().equals(password)) {
                Player findedPlayer = new Player(playerIterable.getId(),
                        playerIterable.getAccounts(),
                        playerIterable.getLogin(),
                        playerIterable.getPassword());

                findedPlayer.setLogined(true);
                return findedPlayer;
            }
        }

        throw new NotFindException("Пользователь не найден!");
    }

    // Метод для получения данных об аккаунте из списка по id пользователя


    public void deposit(int player_id, double value) throws TransactionException {
        if (value <= 0) {
            throw new TransactionException("Нельзя использовать отрицательные значения и 0");
        }


        try {
            Player player = playerDAO.get(player_id).orElseThrow(() -> new NotFindException("Пользователь не найден"));

            BalanceResult result = new BalanceResult(
                    player.getAccounts().getBalance() + value,
                    player.getAccounts().getCreditBalance());

            Transaction transaction = new Transaction(player_id,0 , player.getAccounts(), result);
            player.setAccounts(result);




            playerDAO.update(player);
            addTransaction(transaction);
        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }


    }

    public void withdrawPlayerBalance(int player_id, double value) throws TransactionException {
        try {
            Player player = findPlayerById(player_id).orElseThrow(() -> new NotFindException("Пользователь не найден!"));

            if (value <= 0) {
                throw new TransactionException("Нельзя использовать отрицательные значения и 0");
            }
            if (player.getAccounts().getBalance() - value < 0) {
                throw new TransactionException("На балансе недостаточно средств!");
            }

            BalanceResult playerAccounts = player.getAccounts();

            BalanceResult result = new BalanceResult(
                    player.getAccounts().getBalance() - value,
                    player.getAccounts().getCreditBalance());

            player.setAccounts(result);

            playerDAO.update(player);

            Transaction transaction = new Transaction(player_id, 1, playerAccounts, result);
            addTransaction(transaction);


        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }


    }

    public void takeCredit(int player_id, double value) throws TransactionException {
        if (value <= 0) {
            throw new TransactionException("Нельзя использовать отрицательные значения и 0");
        }

        try {
            Player player = findPlayerById(player_id).orElseThrow(() -> new NotFindException("Пользователь не найден!"));

            BalanceResult playerAccounts = player.getAccounts();

            BalanceResult result = new BalanceResult(
                    player.getAccounts().getBalance() + value,
                    player.getAccounts().getCreditBalance() + value);

            player.setAccounts(result);

            playerDAO.update(player);

            Transaction transaction = new Transaction(player_id, 2, playerAccounts, result);
            addTransaction(transaction);
        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }
    }

    public BalanceResult getBalance(int playerId) {
        try {
            Player player = findPlayerById(playerId).orElseThrow(() -> new NotFindException("Пользователь не найден!"));

            return player.getAccounts();
        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }


    }


    public void logoutPlayer(int player_id) {
        try {
            Player player = playerDAO.get(player_id).orElseThrow(() -> new NotFindException("Пользователь не найден!"));
            player.setLogined(false);

            playerDAO.update(player);
        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }
    }
}
