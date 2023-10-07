package org.example.Dispatchers;

import org.example.Exceptions.LoginException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.fail;

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
    public void logIn_mustReturnVoid__Callable() {
//        PowerMockito.mock(Dispatch.class);
//
//        BDDMockito.given(Dispatch.authenticationPlayer("123123", "123123")).willReturn(true);
//        ;
//        player.logIn("123123","123123");
//        Assertions.assertEquals(true, player.isLogined());
    }
}
