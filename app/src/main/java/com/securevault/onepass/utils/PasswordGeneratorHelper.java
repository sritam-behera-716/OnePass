package com.securevault.onepass.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGeneratorHelper {
    private static final String UPPERCASE = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghjkmnpqrstuvwxyz";
    private static final String DIGITS = "23456789";
    private static final String SYMBOLS = "!@#$%&*+-=?@_";

    private final SecureRandom random = new SecureRandom();

    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase, boolean includeSymbols, boolean includeDigits) {

        StringBuilder characterPool = new StringBuilder();
        if (includeUppercase) characterPool.append(UPPERCASE);
        if (includeLowercase) characterPool.append(LOWERCASE);
        if (includeDigits) characterPool.append(DIGITS);
        if (includeSymbols) characterPool.append(SYMBOLS);

        StringBuilder password = new StringBuilder();
        List<Character> guaranteedChars = new ArrayList<>();

        if (includeUppercase) {
            guaranteedChars.add(getRandomChar(UPPERCASE));
        }
        if (includeLowercase) {
            guaranteedChars.add(getRandomChar(LOWERCASE));
        }
        if (includeDigits) {
            guaranteedChars.add(getRandomChar(DIGITS));
        }
        if (includeSymbols) {
            guaranteedChars.add(getRandomChar(SYMBOLS));
        }

        Collections.shuffle(guaranteedChars, random);
        for (char c : guaranteedChars) {
            password.append(c);
        }

        while (password.length() < length) {
            char randomChar = characterPool.charAt(random.nextInt(characterPool.length()));
            password.append(randomChar);
        }

        String finalPassword = shuffleString(password.toString());
        finalPassword = avoidConsecutiveDuplicates(finalPassword);

        return finalPassword;
    }

    private char getRandomChar(String characterSet) {
        return characterSet.charAt(random.nextInt(characterSet.length()));
    }

    private String shuffleString(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }

        Collections.shuffle(characters, random);

        StringBuilder shuffled = new StringBuilder();
        for (char c : characters) {
            shuffled.append(c);
        }

        return shuffled.toString();
    }


    private String avoidConsecutiveDuplicates(String password) {
        if (password.length() < 2) return password;

        char[] chars = password.toCharArray();
        boolean hasDuplicates = false;

        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == chars[i - 1]) {
                hasDuplicates = true;
                break;
            }
        }

        if (!hasDuplicates) return password;

        StringBuilder newPassword = new StringBuilder();
        newPassword.append(chars[0]);

        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == chars[i - 1]) {
                char replacement = getReplacementChar(chars[i]);
                newPassword.append(replacement);
            } else {
                newPassword.append(chars[i]);
            }
        }

        return newPassword.toString();
    }

    private char getReplacementChar(char original) {
        if (UPPERCASE.indexOf(original) >= 0) {
            return getRandomChar(UPPERCASE.replace(String.valueOf(original), ""));
        } else if (LOWERCASE.indexOf(original) >= 0) {
            return getRandomChar(LOWERCASE.replace(String.valueOf(original), ""));
        } else if (DIGITS.indexOf(original) >= 0) {
            return getRandomChar(DIGITS.replace(String.valueOf(original), ""));
        } else if (SYMBOLS.indexOf(original) >= 0) {
            return getRandomChar(SYMBOLS.replace(String.valueOf(original), ""));
        }
        return original;
    }
}