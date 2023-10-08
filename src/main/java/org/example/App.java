package org.example;

import org.example.out.Dispatchers.Player;
import org.example.out.Exceptions.NotFindException;

/**
 * Счётчик потраченных часов: 8
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Player player = new Player();
        player.register("123123", "320123");
        player.logIn("123123", "320123");
        player.deposit(300);
        System.out.println(player.getBalance().getBalance());
        player.withdraw(200);
        System.out.println(player.getBalance().getBalance());


    }
}
