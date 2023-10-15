package org.example.out.Models;

public class TransactionType {
    private int id;
    private String name;

    public TransactionType() {
    }

    public TransactionType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
