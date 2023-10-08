package org.example.Dispatchers;

import org.example.out.Dispatchers.Dispatch;
import org.example.out.Dispatchers.Player;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    @BeforeEach
    public void before() {
        player = new Player();
    }

    @Test
    @DisplayName("CheckIsLoggined_Exception")
    public void checkIsLoggined_mustThrowLoginException__Callable(){
        player.setLogined(false);

        Assertions.assertThrows(LoginException.class,()-> player.checkIsLogin());
    }

    @Test
    @DisplayName("CheckIsLoggined_Void")
    public void checkIsLoggined_mustReturnVoid__Callable(){
        player.setLogined(true);

        try {
            player.checkIsLogin();
        } catch (LoginException e) {
            fail("Не должен слать исключение");
        }
    }

    @Test
    @DisplayName("Deposit")
    public void deposit_mustReturnVoid__Callable(){
        player.setLogined(true);
        player.deposit(200);


        Assertions.assertEquals(200.0, player.getBalance());
    }

    @Test
    @DisplayName("GetBalance")
    public void getBalance_mustReturnInt__Callable(){
        player.setLogined(true);
        player.getBalance();

        Assertions.assertEquals(0.0, player.getBalance());
    }

    @Test
    @DisplayName("Logout")
    public void logOut_mustReturnVoid__Callable(){
        player.logOut();
        Assertions.assertFalse(player.isLogined());
    }

    @Test // TODO
    @DisplayName("login_void")
    public void logIn_mustReturnVoid__Callable() {
//        List<Player> playerList = new ArrayList<>();
//        Player testPlayer = new Player();
//
//        testPlayer.setLogin("123123");
//        testPlayer.setPassword("123123");
//
//        playerList.add(testPlayer);
//        Dispatch.setPlayers(playerList);
//
//        try {
//            player.logIn("123123", "123123");
//        } catch (NotFindException e) {
//            throw new RuntimeException(e);
//        }
//
//        assertTrue(player.isLogined());
    }

    @Test
    @DisplayName("login_exception")
    public void logIn_mustThrowException__Callable() {
//        List<Player> playerList = new ArrayList<>();
//        Player testPlayer = new Player();
//
//        testPlayer.setLogin("123123");
//        testPlayer.setPassword("123123");
//
//        playerList.add(testPlayer);
//        Dispatch.setPlayers(playerList);
//
//
//        try {
//            player.logIn("1231234", "1231234");
//        } catch (NotFindException e) {
//            assertFalse(player.isLogined());
//        }
//
//

    }
}
