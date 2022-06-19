package com.expenseshare.commands;

import com.expenseshare.controller.ExpenseManager;
import com.expenseshare.exceptions.NoSuchUserException;
import com.expenseshare.model.User;

import java.io.IOException;

public class ShowCommand implements Command {
    @Override
    public void execute(String[] cmd) throws IOException {
        ExpenseManager expenseManager = ExpenseManager.getInstance();
        if (cmd.length > 1) {
            // show for user
            User user;
            try {
                user = Utils.getUser(cmd[1]);
                expenseManager.showBalance(user);
            } catch (NoSuchUserException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // show for all
            expenseManager.showAllBalances();
        }
    }
}
