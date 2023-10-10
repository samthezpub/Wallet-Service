package org.example.out.Dispatchers;

import org.example.out.Dispatchers.Dispatch;
import org.example.out.Dispatchers.Player;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    @DisplayName("CheckIsLoggined_Exception")
    public void checkIsLoggined_mustThrowLoginException__Callable(){
        Player player = new Player();
        player.setLogined(false);

        Assertions.assertThrows(LoginException.class,()-> player.checkIsLogin());
    }

    @Test
    @DisplayName("CheckIsLoggined_Void")
    public void checkIsLoggined_mustReturnVoid__Callable(){
        Player player = new Player();
        player.setLogined(true);

        try {
            player.checkIsLogin();
        } catch (LoginException e) {
            fail("Не должен слать исключение");
        }
    }

    @Test
    @DisplayName("Deposit")
    public void deposit_mustThrowException(){
        Player player = new Player();
        player.setId(50000);
        player.setLogined(true);


            ;
            assertThrows(RuntimeException.class, ()->player.deposit( 200));

    }


    @Test
    @DisplayName("Logout")
    public void logOut_mustReturnVoid__Callable(){
        Player player = new Player();
        player.setId(0);
        player.logOut(player);
        Assertions.assertFalse(player.isLogined());
    }


}
