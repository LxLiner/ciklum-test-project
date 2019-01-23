package com.lxliner.ciklumtestproject.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordStrengthChecker {

    public boolean isWeakPassword(String password) {
        return getPasswordStrength(password) != 4;
    }

    public int getPasswordStrength(String password) {
        if (password.length() == 0) {
            return 0;
        }

        if (password.length() < 6) {
            return 1;
        }

        if (password.equals(password.toLowerCase()) || password.equals(password.toUpperCase()) || !password.matches(".*\\d.*")) {
            return 2;
        }

        if (password.matches("[A-Za-z0-9]*")) {
            return 3;
        }

        return 4;
    }
}
