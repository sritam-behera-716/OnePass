package com.securevault.onepass.data;

public class PasswordItem {
    private final String passwordName;

    public PasswordItem(String passwordName) {
        this.passwordName = passwordName;
    }

    public String getPasswordName() {
        return passwordName;
    }
}