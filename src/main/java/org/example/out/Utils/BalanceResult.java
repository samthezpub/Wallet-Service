package org.example.out.Utils;

public class BalanceResult {
    private double balance;
    private double creditBalance;

    public BalanceResult(double balance, double creditBalance) {
        this.balance = balance;
        this.creditBalance = creditBalance;
    }

    @Override
    public String toString() {
        return "Баланс:\n" +
                "Баланс:" + balance +
                ", \nКредит=" + creditBalance;
    }

    public double getBalance() {
        return balance;
    }

    public double getCreditBalance() {
        return creditBalance;
    }
}
