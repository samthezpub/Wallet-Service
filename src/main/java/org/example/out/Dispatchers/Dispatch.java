package org.example.out.Dispatchers;

import lombok.Data;
import org.example.out.Common.Audit;
import org.example.out.Enums.TransactionTypeEnum;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Utils.BalanceResult;

import java.util.*;

@Data
public class Dispatch {
    private static Set<Player> players = new HashSet<>();

    public static List<Transaction> getTransactionsByPlayerId(Integer id) {
        return Audit.getTransactions(id);
    }

    private static void addTransaction(Player player, Transaction transaction) {
        Audit.addTransaction(transaction);
    }

    public static void registerPlayer(Player player, String login, String password) {
        player.setLogin(login);
        player.setPassword(password);
        players.add(player);
    }

    public static boolean authenticationPlayer(String login, String password) throws NotFindException {
        for (Player player : players) {
            if (player.getLogin() == login && player.getPassword() == password) {
                return true;
            }
        }

        throw new NotFindException("Пользователь не найден!");
    }

    public static BalanceResult deposit(Player player, double value) {
        BalanceResult playerBalance = player.getAccounts();

        BalanceResult result = new BalanceResult(playerBalance.getBalance()+value, playerBalance.getCreditBalance());
        return result;
    }

    public static BalanceResult withdrawPlayerBalance(Player player, double value) throws TransactionException {
        if (player.getAccounts().getBalance() - value < 0) {
            throw new TransactionException("На балансе недостаточно средств!");
        }

        BalanceResult result = new BalanceResult(player.getBalance().getBalance() - value, player.getBalance().getCreditBalance());

        Transaction transaction = new Transaction(player.getId(), TransactionTypeEnum.WITHDRAW, player.getAccounts(), result);
        Audit.addTransaction(transaction);
        return result;
    }

    public static BalanceResult takeCredit(Player player, double value) throws TransactionException {
        BalanceResult result = new BalanceResult(player.getAccounts().getBalance()+ value, value);

        Transaction transaction = new Transaction(player.getId(), TransactionTypeEnum.CREDIT, player.getAccounts(), result);
        Audit.addTransaction(transaction);

        return result;
    }

    public static BalanceResult getBalance(Player player) {
        return player.getAccounts();
    }

    public static Set<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(Set<Player> players) {
        Dispatch.players = players;
    }
}
