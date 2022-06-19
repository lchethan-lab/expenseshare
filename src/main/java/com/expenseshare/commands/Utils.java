package com.expenseshare.commands;

import com.expenseshare.controller.ExpenseManager;
import com.expenseshare.exceptions.NoSuchUserException;
import com.expenseshare.model.User;

public class Utils {
    public static double roundOff(double value) {
        return ((long) (value * 100)) / 100.0d; // round off up to two decimal
    }

    public static boolean isApproxEqual(double v1, double v2) {
        return (Math.abs(v1 - v2) / (Math.min(Math.abs(v1), Math.abs(v2)))) < 1e-10; // relative error
    }

    public static User getUser(String userIdentification) throws NoSuchUserException {
        ExpenseManager expenseManager = ExpenseManager.getInstance();
        User user;
        try {
            Long userId = Long.parseLong(userIdentification);
            user = expenseManager.getUser(userId);
        } catch (NumberFormatException e) {
            String userEmail = userIdentification.trim();
            user = expenseManager.getUser(userEmail);
        }

        return user;
    }
}
