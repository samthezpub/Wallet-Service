package org.example.in.Menu;

import org.example.in.Exceptions.InputException;
import org.example.out.Dispatchers.Dispatch;
import org.example.out.Dispatchers.Player;
import org.example.out.Dispatchers.Transaction;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;

import java.util.Scanner;
import java.util.Set;

public class Menu {
    private static Player player = new Player();
    private static Scanner scanner = new Scanner(System.in);

    public static void register() {

        String login = "";
        String password = "";


        System.out.println("Нужно зарегестрироваться!");
        System.out.print("Введите логин: ");


        try {
            // Присваеваем login
            login = scanner.nextLine();
            if (login.isEmpty()) {
                throw new InputException("Вы ввели недопустимый логин!");
            }

            System.out.print("Введите пароль: ");

            //Присваиваем значение password
            password = scanner.nextLine();
            if (password.isEmpty()) {
                throw new InputException("Вы ввели недопустимый пароль!");
            }

        } catch (InputException e) {
            System.err.println(e.getMessage());
            System.out.println("Давайте попробуем заново...");


            // Даём возможность ещё раз ввести логин и пароль
            register();
            return;
        }


        try {
            player.register(login, password);
        } catch (LoginException e) {
            System.err.println(e.getMessage());
            register();
            return;
        }

        System.out.println("Отлично! Мы вас зарегистрировали!");
        System.out.println("Ваш логин: " + login);
    }

    public static void login() {
        String login = "";
        String password = "";
        System.out.println("Теперь надо авторизоваться");

        System.out.println("Введите ваш логин:");
        try {
            login = scanner.nextLine();
            if (login.isEmpty()) {
                throw new InputException("Вы ввели недопустимый логин!");
            }

            System.out.println("Введите ваш пароль:");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                throw new InputException("Вы ввели недопустимый пароль!");
            }
        } catch (InputException e) {
            System.err.println(e.getMessage());

            login();
            return;
        }

        player.logIn(login, password);
        System.out.println("Вы успешно авторизированы, можете выполнять операции");
    }

    public static void deposit() {
        double value = 0;
        System.out.println("Вы хотите вложить деньги");
        System.out.println("Сколько хотите вложить?");

        try {
            value = Double.parseDouble(scanner.next());
            try {
                player.deposit(value);
            } catch (TransactionException e) {
                System.err.println(e.getMessage());
            }

            getBalance();
        } catch (NumberFormatException e) {
            System.err.println("Вы ввели недопустимое значение!");
        }finally {
            menu();
        }
    }

    public static void withdrawPlayerBalance() {
        double value = 0;
        System.out.println("Вы хотите снять деньги");
        System.out.println("Сколько хотите снять?");

        try {
            value = Double.parseDouble(scanner.next());
            player.withdraw(value);

            getBalance();
        } catch (NumberFormatException e) {
            System.err.println("Вы ввели недопустимое значение!");
        } finally {
            menu();
        }


    }

    public static void takeCredit() {
        double value = 0;
        System.out.println("Вы хотите взять деньги в кредит");
        System.out.println("Сколько хотите взять?");

        try {
            value = Double.parseDouble(scanner.next());
            player.takeCredit(value);
            getBalance();
        } catch (NumberFormatException e) {
            System.err.println("Вы ввели недопустимое значение!");
        } finally {
            menu();
        }


    }

    public static void getTransactions() {
        Set<Transaction> transtactionsSet = player.getTransactions();

        if (transtactionsSet.size() == 0) {
            System.out.println("Тут пока нет транзакций!");
        } else {
            for (Transaction transaction : transtactionsSet) {
                System.out.println(transaction.toString());
            }
        }

    }

    public static void getBalance(){
        System.out.println("Ваш баланс");
        System.out.println("Общий: " + player.getAccounts().getBalance());
        System.out.println("Кредитный: " + player.getAccounts().getCreditBalance());
        System.out.println("Ваши деньги: " + (player.getAccounts().getBalance() - player.getAccounts().getCreditBalance()));

        return;
    }
    public static void menu() {

        boolean isRunning = true;

        System.out.println("\n");
        System.out.println("Добро пожаловать в меню!");
//        while (isRunning) {
//            System.out.println("");
//        }
    }
}
