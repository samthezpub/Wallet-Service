package org.example;

import org.example.in.Menu.Menu;

/**
 * Счётчик потраченных часов: 8
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Menu.register();
        Menu.login();
        Menu.deposit();
        Menu.takeCredit();
        Menu.withdrawPlayerBalance();

    }
}
