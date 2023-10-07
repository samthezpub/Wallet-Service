package org.example;

import lombok.Data;
import org.example.Exception.NotFindException;

import java.util.List;

@Data
public class Dispatch {
    private static List<Player> players;


    public static void registerPlayer(String login, String password) {

    }

    public static boolean authenticationPlayer(String login, String password) {
        try {
            for (Player player : players) {
                if (player.getLogin() == login && player.getPassword() == password) {
                    return true;
                }
            }

            throw new NotFindException("Пользователь не найден!");
        } catch (NotFindException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
