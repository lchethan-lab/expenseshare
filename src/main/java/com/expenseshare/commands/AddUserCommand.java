package com.expenseshare.commands;

import com.expenseshare.controller.ExpenseManager;
import com.expenseshare.model.User;

import java.io.IOException;

public class AddUserCommand implements Command {
    @Override
    public void execute(String[] cmd) throws IOException {
        ExpenseManager expenseManager = ExpenseManager.getInstance();
        if (cmd.length == 3) {
            User u = new User(cmd[1], cmd[2]);
            expenseManager.addUser(u);
        } else {
            System.out.println("unknown format, usage:add_user <username> <email_id>");
        }

    }
}
