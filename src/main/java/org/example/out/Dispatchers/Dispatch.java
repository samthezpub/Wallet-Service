package org.example.out.Dispatchers;

import lombok.Data;
import org.example.out.Common.Audit;
import org.example.out.Enums.TransactionTypeEnum;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Utils.BalanceResult;

import java.util.*;

@Data
public class Dispatch {
    private static List<Player> players = new ArrayList<>();

    public static Set<Transaction> getTransactionsByPlayerId(Integer id) {
        return Audit.findTransactionsByPlayerId(id);
    }

    private static void addTransaction(Transaction transaction) {
            Audit.addTransaction(transaction);
    }

    public static void addPlayer(int id, BalanceResult account, String login, String password) {
        Player player = new Player(id, account, login, password);
        players.add(player);
    }

    private static int findPlayerById(int id) throws NotFindException {
        for (Player player : players) {
            if (player.getId().equals(id)) {
                return players.indexOf(player);
            }
        }

        throw new NotFindException("Пользователь не найден!");
    }

    public static void registerPlayer(String login, String password) throws LoginException {
        for (Player player : players) {
            if (player.getLogin().equals(login)) {
                throw new LoginException("Пользователь с таким логином уже существует!");
            }
        }

        addPlayer(players.size(), new BalanceResult(0, 0), login, password);
    }


    public static Player authenticationPlayer(String login, String password) throws NotFindException {
        for (Player playerIterable : players) {
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


    public static BalanceResult deposit(int player_id, double value) throws TransactionException {
        if (value <= 0) {
            throw new TransactionException("Нельзя использовать отрицательные значения и 0");
        }

        int elementPosition;
        try {
            elementPosition = findPlayerById(player_id);
        } catch (NotFindException e) {
            throw new RuntimeException(e.getMessage());

        }
        BalanceResult playerAccounts = players.get(elementPosition).getAccounts();

        BalanceResult result = new BalanceResult(
                players.get(elementPosition).getAccounts().getBalance() + value,
                players.get(elementPosition).getAccounts().getCreditBalance());
        players.get(elementPosition).setAccounts(result);

        Transaction transaction = new Transaction(player_id, TransactionTypeEnum.DEPOSIT, playerAccounts, result);
        addTransaction(transaction);
    return result;

    }

    public static BalanceResult withdrawPlayerBalance(int player_id, double value) throws TransactionException {
        int elementPosition;

        try {
            elementPosition = findPlayerById(player_id);
        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }

        BalanceResult playerAccounts = players.get(elementPosition).getAccounts();

        if (value <= 0) {
            throw new TransactionException("Нельзя использовать отрицательные значения и 0");
        }
        if (playerAccounts.getBalance() - value < 0) {
            throw new TransactionException("На балансе недостаточно средств!");
        }




        BalanceResult result = new BalanceResult(
                players.get(elementPosition).getAccounts().getBalance() - value,
                players.get(elementPosition).getAccounts().getCreditBalance());

        players.get(elementPosition).setAccounts(result);

        Transaction transaction = new Transaction(player_id, TransactionTypeEnum.WITHDRAW, playerAccounts, result);
        addTransaction(transaction);
        return result;
    }

    public static BalanceResult takeCredit(int player_id, double value) throws TransactionException {
        if (value <= 0) {
            throw new TransactionException("Нельзя использовать отрицательные значения и 0");
        }

        int elementPosition;

        try {
            elementPosition = findPlayerById(player_id);
        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }
        BalanceResult playerAccounts = players.get(elementPosition).getAccounts();

        BalanceResult result = new BalanceResult(
                players.get(elementPosition).getAccounts().getBalance() + value,
                players.get(elementPosition).getAccounts().getCreditBalance() + value);

        players.get(elementPosition).setAccounts(result);

        Transaction transaction = new Transaction(player_id, TransactionTypeEnum.CREDIT, playerAccounts, result);
        addTransaction(transaction);
        return result;

    }

    public static BalanceResult getBalance(int playerId) {
        int elementPosition;
        try {
            elementPosition = findPlayerById(playerId);
        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }

        return players.get(elementPosition).getAccounts();
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(List<Player> players) {
        Dispatch.players = players;
    }

    public static void logoutPlayer(int player_id) {
        for (Player playerIterable : players) {
            if (playerIterable.getId().equals(player_id)) {
                players.remove(playerIterable);

                players.add(playerIterable);
                break;
            }
        }
    }
}
