package org.example.out.Models;

import lombok.Data;

import org.example.out.Utils.BalanceResult;

@Data
public class Player
{

    private Integer id;
    private BalanceResult accounts;

    private String login;
    private String password;

    public Player() {
        this.accounts = new BalanceResult(0,0);
    }

    public Player(Integer id, BalanceResult accounts, String login, String password) {
        this.id = id;
        this.accounts = accounts;
        this.login = login;
        this.password = password;
    }

}
