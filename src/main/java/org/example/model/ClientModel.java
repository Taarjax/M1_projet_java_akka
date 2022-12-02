package org.example.model;

public class ClientModel {
    //Déclaration des variables
    private long accountId;
    private long userId;
    private long balance;

    // Constructeur de la classe
    public ClientModel(long accountId, long userId, long balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }


    // GETTER AND SETTER
    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getBalance() {
        return balance;
    }
}
