package com.expenseshare.commands;

import com.expenseshare.controller.ExpenseManager;
import com.expenseshare.exceptions.InvalidExpenseTypeException;
import com.expenseshare.exceptions.NoSuchUserException;
import com.expenseshare.model.expense.ExpenseType;
import com.expenseshare.model.User;
import com.expenseshare.model.expense.Expense;
import com.expenseshare.model.expense.ExpenseFactory;
import com.expenseshare.model.splits.SplitFactory;
import com.expenseshare.model.splits.Splits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseCommand implements Command {
    @Override
    public void execute(String[] cmd) throws IOException {
        Expense expense = null;
        if (cmd.length < 5) {
            System.out.println("usage: " +
                    "add_expense [equal/percent/exact] name createdBy [paidBy] [user val user val user val]");
            return;
        }

        ExpenseType expType;
        try {
            expType = ExpenseType.fromString(cmd[1]);
        } catch (InvalidExpenseTypeException e) {
            System.out.println(e.getMessage());
            return;
        }
        String name = cmd[2];
        Double totalAmount = Double.parseDouble(cmd[3]);
        User paidBy;
        try {
            paidBy = Utils.getUser(cmd[4]);
        } catch (NoSuchUserException e) {
            System.out.println(e.getMessage());
            return;
        }


        try {
            expense = ExpenseFactory.createExpense(expType, name, paidBy, totalAmount);
        } catch (InvalidExpenseTypeException e) {
            e.printStackTrace();
            return;
        }
        if (cmd.length > 5) {
            User createdBy;
            try {
                createdBy = Utils.getUser(cmd[5]);
            } catch (NoSuchUserException e) {
                System.out.println(e.getMessage());
                return;
            }
            expense.setCreatedBy(createdBy);

           int numberOfSplits = cmd.length - 6;
           /*  if (numberOfSplits % 2 != 0) {
                System.out.println("usage: " +
                        "add_expense expense_type name createdBy [paidBy] [user val user val user val]");
            }*/
            List<Splits> splits = new ArrayList<>();
            if (expType.equals(ExpenseType.EQUAL)) {
                for (int i = 0; i < numberOfSplits; i++) {
                    User user;
                    try {
                        user = Utils.getUser(cmd[6 + i]);
                    } catch (NoSuchUserException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    Splits split = null;
                    try {
                        split = SplitFactory.createSplit(expType, user);
                    } catch (InvalidExpenseTypeException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    splits.add(split);
                }
            } else {
                for (int i = 0; i < numberOfSplits; i += 2) {
                    User user;
                    try {
                        user = Utils.getUser(cmd[6 + i]);
                    } catch (NoSuchUserException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    double amountOrPercent = Double.parseDouble(cmd[6 + i + 1]);
                    Splits split = null;
                    try {
                        split = SplitFactory.createSplit(expType, user, amountOrPercent);
                    } catch (InvalidExpenseTypeException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    splits.add(split);
                }
            }expense.setSplits(splits);
        }
        try {
            ExpenseManager.getInstance().addExpense(expense);
        } catch (InvalidExpenseTypeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchUserException e) {
            throw new RuntimeException(e);
        }
    }
}
