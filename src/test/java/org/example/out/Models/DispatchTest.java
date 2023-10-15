package org.example.out.Models;

import org.example.out.Common.Dispatch;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Utils.BalanceResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DispatchTest {

    private final static List<Player> players = new ArrayList<>();


    @Test
    @DisplayName("deposit_mustReturnBankAccount")
    public void deposit_givenPlayerIdAndValue_mustReturnBankAccount() {
        Player player1 = new Player(0, new BalanceResult(0, 0), "first", "firstPassword");
        List<Player> players = new ArrayList();
        players.add(player1);
        Dispatch.setPlayers(players);

        BalanceResult expected = new BalanceResult(150, 0);


        BalanceResult actual = null;
        try {
            actual = Dispatch.deposit(0, 150);
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expected.getBalance(), actual.getBalance());
        Assertions.assertEquals(expected.getCreditBalance(), actual.getCreditBalance());

    }

    @Test
    @DisplayName("deposit_mustThrowTransactionException")
    public void deposit_givenPlayerIdAndValue_mustThrowTransactionException() {
        Assertions.assertThrows(TransactionException.class, () -> Dispatch.deposit(0, -200));
    }

    @Test
    @DisplayName("deposit_mustThrowRuntimeException")
    public void deposit_givenPlayerIdAndValue_mustThrowRuntimeException() {

        Assertions.assertThrows(RuntimeException.class, () -> Dispatch.deposit(9999, 20));
    }


    @Test
    @DisplayName("withdrawPlayerBalance_mustReturnBalanceResult")
    public void withdrawPlayerBalance_givenPlayerIdAndValue_mustReturnBalanceResult() {
        Player player1 = new Player(0, new BalanceResult(100, 0), "first", "firstPassword");
        List<Player> players = new ArrayList();
        players.add(player1);
        Dispatch.setPlayers(players);

        BalanceResult expected = new BalanceResult(0, 0);


        BalanceResult actual = null;
        try {
            actual = Dispatch.withdrawPlayerBalance(0, 100);
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expected.getBalance(), actual.getBalance());
        Assertions.assertEquals(expected.getCreditBalance(), actual.getCreditBalance());
    }

    @Test
    @DisplayName("withdrawPlayerBalance_mustThrowTransactionException")
    public void withdrawPlayerBalance_givenPlayerIdAndValue_mustThrowTransactionException() {
        Assertions.assertThrows(TransactionException.class, () -> Dispatch.withdrawPlayerBalance(0, 9999999));
        Assertions.assertThrows(TransactionException.class, () -> Dispatch.withdrawPlayerBalance(0, -200));

    }

    @Test
    @DisplayName("takeCredit_mustReturnBalanceResult")
    public void takeCredit_givenPlayerIdAndValue_mustReturnBalanceResult() {
        Player player1 = new Player(0, new BalanceResult(100, 0), "first", "firstPassword");
        List<Player> players = new ArrayList();
        players.add(player1);
        Dispatch.setPlayers(players);
        BalanceResult expected = new BalanceResult(150, 50);


        BalanceResult actual = null;
        try {
            actual = Dispatch.takeCredit(0, 50);
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expected.getBalance(), actual.getBalance());
        Assertions.assertEquals(expected.getCreditBalance(), actual.getCreditBalance());

    }

    @Test
    @DisplayName("takeCredit_mustThrowTransactionException")

    public void takeCredit_givenPlayerIdAndValue_mustThrowTransactionException() {
        Assertions.assertThrows(TransactionException.class, () -> Dispatch.takeCredit(0, -200));
    }


    @Test
    public void getBalance_givenPlayerId_mustReturnPlayerAccounts() {
        Player player1 = new Player(0, new BalanceResult(100, 20), "first", "firstPassword");
        List<Player> players = new ArrayList();
        players.add(player1);
        Dispatch.setPlayers(players);

        BalanceResult expected = new BalanceResult(100, 20);

        BalanceResult actual = null;
        actual = Dispatch.getBalance(0);

        Assertions.assertEquals(expected.getBalance(), actual.getBalance());
        Assertions.assertEquals(expected.getCreditBalance(), actual.getCreditBalance());
    }

//    @Test
//    @DisplayName("getTransactionsByPlayerId_mustReturnSetWithTransactions")
//    public void findTransactionsByPlayerId_givenPlayerId_mustReturnSetWithTransactions(){
//        Player player1 = new Player(10, new BalanceResult(0, 20), "32", "12");
//        List<Player> players = new ArrayList();
//        players.add(player1);
//        Dispatch.setPlayers(players);
//        player1.setLogined(true);
//        try {
//            player1.deposit(100);
//        } catch (TransactionException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Ожидаемое множество транзакций
//        Set<Transaction> expected = new LinkedHashSet<>();
//        Transaction transaction = new Transaction(10, TransactionTypeEnum.DEPOSIT,
//                new BalanceResult(0,20), new BalanceResult(100,20));
//        transaction.setId(2);
//        transaction.setPlayerId(10);
//        expected.add(transaction);
//
//// Получение фактического множества транзакций
//        Set<Transaction> actual = Dispatch.getTransactionsByPlayerId(10);
//
//// Сравниваем множества транзакций
//        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
//
//    }


    @Test
    @DisplayName("registerPlayer_mustThrowLoginException")
    public void registerPlayer_givenLoginAndPassword_mustThrowLoginException() {
        Player player1 = new Player(0, new BalanceResult(100, 20), "first", "firstPassword");
        List<Player> players = new ArrayList();
        players.add(player1);
        Dispatch.setPlayers(players);

        assertThrows(LoginException.class, () -> Dispatch.registerPlayer("first", "13213"));
    }

    @Test
    @DisplayName("registerPlayer_mustRegisterUser")
    public void registerPlayer_givenLoginAndPassword_mustRegisterUser() {
        try {
            Dispatch.registerPlayer("123", "123");
        } catch (LoginException e) {
            Assertions.fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("authenticationPlayer_mustReturnPlayer")
    public void authenticationPlayer_givenLoginAndPassword_mustReturnPlayer() {
        Player expected = new Player(0, new BalanceResult(100, 20), "first", "firstPassword");
        expected.setLogined(true);
        List<Player> players = new ArrayList();
        players.add(expected);
        Dispatch.setPlayers(players);

        Player actual = null;
        try {
            actual = Dispatch.authenticationPlayer("first", "firstPassword");
        } catch (NotFindException e) {
            Assertions.fail();
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("authenticationPlayer_mustThrowNotFindException")
    public void authenticationPlayer_givenLoginAndPassword_mustThrowNotFindException() {
        Player expected = new Player(0, new BalanceResult(100, 20), "first", "firstPassword");
        expected.setLogined(true);
        List<Player> players = new ArrayList();
        players.add(expected);
        Dispatch.setPlayers(players);


        assertThrows(NotFindException.class, () -> Dispatch.authenticationPlayer("notCorrect", "notCorrect"));
    }


}
