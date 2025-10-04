package com.securevault.onepass.data;

public class PasswordItem {
    private final int id;
    private final String passwordName;

    public PasswordItem(int id, String passwordName) {
        this.id = id;
        this.passwordName = passwordName;
    }

    public int getId() {
        return id;
    }

    public String getPasswordName() {
        return passwordName;
    }
}