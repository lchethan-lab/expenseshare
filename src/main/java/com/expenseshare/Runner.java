package com.expenseshare;

import com.expenseshare.commands.CommandFactory;
import com.expenseshare.controller.ExpenseManager;
import com.expenseshare.model.User;

import java.io.IOException;
import java.util.Scanner;

public class Runner {
    public static void main(String args[]) {
        ExpenseManager expenseManager = ExpenseManager.getInstance();

        expenseManager.addUser(new User("chethan", "chethan@gmail.com"));
        expenseManager.addUser(new User("chethan1", "chethan1@gmail.com"));
        expenseManager.addUser(new User("chethan2", "chethan2@gamil.com"));

        Scanner sc = new Scanner(System.in);
        while (true) {
            String[] cmd = sc.nextLine().split(" ");
            try {
                CommandFactory.getInstance().execute(cmd);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
