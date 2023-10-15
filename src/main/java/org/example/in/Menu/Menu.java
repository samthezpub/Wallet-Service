package org.example.in.Menu;

import org.example.in.Exceptions.InputException;
import org.example.out.Dispatchers.Dispatch;
import org.example.out.Dispatchers.Player;
import org.example.out.Dispatchers.Transaction;
import org.example.out.Exceptions.LoginException;
import org.example.out.Exceptions.NotFindException;
import org.example.out.Exceptions.TransactionException;
import org.example.out.Utils.BalanceResult;

import java.util.Scanner;
import java.util.Set;

public class Menu {
    private static Player player = new Player();
    private static Dispatch dispatch = new Dispatch();
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
            player.register(dispatch,login, password);
        } catch (LoginException e) {
            System.err.println(e.getMessage());
            menuNotLoggined();
            return;
        }

        System.out.println("Отлично! Мы вас зарегистрировали!");
        System.out.println("Ваш логин: " + login);

        menuNotLoggined();
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

        try {
            player.logIn(dispatch,login, password);

            menuLoggined();
        } catch (NotFindException e) {
            System.err.println(e.getMessage());
            menuNotLoggined();
        }
        System.out.println("Вы успешно авторизированы, можете выполнять операции");


    }

    public static void deposit() {
        double value = 0;
        System.out.println("Вы хотите вложить деньги");
        System.out.println("Сколько хотите вложить?");

        try {
            value = Double.parseDouble(scanner.next());
            try {
                player.deposit(dispatch,value);
                getBalance();
                return;
            } catch (TransactionException e) {
                System.err.println(e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.err.println("Вы ввели недопустимое значение!");
        } finally {
            menuLoggined();
        }

    }

    public static void withdrawPlayerBalance() {
        double value = 0;
        System.out.println("Вы хотите снять деньги");
        System.out.println("Сколько хотите снять?");

        try {
            value = Double.parseDouble(scanner.next());
            player.withdraw(dispatch, value);

            getBalance();
        } catch (NumberFormatException e) {
            System.err.println("Вы ввели недопустимое значение!");
        } finally {
            menuLoggined();
        }


    }

    public static void takeCredit() {
        double value = 0;
        System.out.println("Вы хотите взять деньги в кредит");
        System.out.println("Сколько хотите взять?");

        try {
            value = Double.parseDouble(scanner.next());
            player.takeCredit(dispatch, value);
            getBalance();
            menuLoggined();
        } catch (NumberFormatException e) {
            System.err.println("Вы ввели недопустимое значение!");
            menuLoggined();
        }
    }

    private static void logout() {
        player.logOut(dispatch, player);
        System.out.println("Вы вышли из аккаунта!");
        System.out.println("Всего хорошего!");
        menuNotLoggined();
    }

    public static void getTransactions() {
        Set<Transaction> transtactionsSet = player.getTransactions(dispatch);

        if (transtactionsSet.size() == 0) {
            System.out.println("Тут пока нет транзакций!");
        } else {
            for (Transaction transaction : transtactionsSet) {
                System.out.println(transaction.toString());
            }
        }

        menuLoggined();
    }

    public static void getBalance() {
        BalanceResult balanceResult = player.getBalance(dispatch);

        System.out.println("Ваш баланс");
        System.out.println("Общий: " + balanceResult.getBalance());
        System.out.println("Кредитный: " + balanceResult.getCreditBalance());
        System.out.println("Ваши деньги: " + (balanceResult.getBalance() - balanceResult.getCreditBalance()));

        menuLoggined();
    }


    public static void menuNotLoggined() {
        String playerChoose = "";
        boolean isRunning = true;
        while (playerChoose.isEmpty()) {
            System.out.println("Добро пожаловать в меню!");
            System.out.println("Выберите один из элементов меню");
            System.out.println("1 | Регистрация");
            System.out.println("2 | Аунтефикация");

            playerChoose = scanner.nextLine();
        }

        switch (playerChoose) {
            case "1":
                register();
                break;
            case "2":
                login();
                break;
            default:
                break;
        }
    }


    public static void menuLoggined() {
        final int MENU_ELEMENTS = 6;


        System.out.println("Выберите элемент меню");
        System.out.println("1 | Выйти");
        System.out.println("2 | Депозит");
        System.out.println("3 | Снять деньги");
        System.out.println("4 | Взять в кредит");
        System.out.println("5 | Посмотреть баланс");
        System.out.println("6 | Просмотреть транзакции");



        String userChoose = scanner.nextLine();

        while (userChoose.isEmpty() || Integer.parseInt(userChoose) < 1 || Integer.parseInt(userChoose) > MENU_ELEMENTS){
            userChoose = scanner.nextLine();
        }

        switch (userChoose){
            case "1":
                logout();
                break;
            case "2":
                deposit();
                break;
            case "3":
                withdrawPlayerBalance();
                break;
            case "4":
                takeCredit();
                break;
            case "5":
                getBalance();
                break;
            case "6":
                getTransactions();
                break;
            default:
                break;
        }
    }


}
