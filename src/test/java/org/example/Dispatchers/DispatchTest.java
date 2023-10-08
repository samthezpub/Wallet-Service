package org.example.Dispatchers;

import org.example.out.Dispatchers.Dispatch;
import org.example.out.Dispatchers.Player;
import org.example.out.Exceptions.NotFindException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DispatchTest {

    @Test
    @DisplayName("authenticationPlayer_true")
    public void authenticationPlayerMustReturnTrue__Callable(){
//        List<Player> playerList = new ArrayList<>();
//        Player player = new Player();
//        player.setLogin("321321");
//        player.setPassword("231231");
//
//        playerList.add(player);
//
//        Dispatch.setPlayers(playerList);

        try {
            Assertions.assertTrue(Dispatch.authenticationPlayer("321321", "231231"));
        } catch (NotFindException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void authenticationPlayer_MustReturnFalseAndThrowException__Callable(){
//        List<Player> playerList = new ArrayList<>();
//        Player player = new Player();
//        player.setLogin("0");
//        player.setPassword("2");
//
//        playerList.add(player);
//
//        Dispatch.setPlayers(playerList);


        try {
            Assertions.assertFalse(Dispatch.authenticationPlayer("321321", "231231"));
        } catch (NotFindException e) {
            Assertions.assertThrows(NotFindException.class, ()->Dispatch.authenticationPlayer("321321", "231231"));
        }


    }

    @Test // TODO
    public void registerPlayer_mustReturnVoid__Callable(){

//        Dispatch.registerPlayer("321", "123");
//
//        Player player = new Player();
//        player.setLogin("321");
//        player.setPassword("123");
//        Assertions.assertEquals(player, Dispatch.getPlayers().get(0));
    }
}
