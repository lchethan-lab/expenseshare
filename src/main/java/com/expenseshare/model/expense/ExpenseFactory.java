package com.expenseshare.model.expense;

import com.expenseshare.exceptions.InvalidExpenseTypeException;
import com.expenseshare.model.User;

public class ExpenseFactory {
    public static Expense createExpense(ExpenseType type,
                                        String name,
                                        User createdBy,
                                        double totalAmount) throws InvalidExpenseTypeException {
        switch(type) {
            case EXACT:
                return new ExactExpense( createdBy,name, totalAmount);
            case PERCENT:
                return new PercentExpense( createdBy,name, totalAmount);
            case EQUAL:
                return new EqualExpense( createdBy,name, totalAmount);
            default:
                throw new InvalidExpenseTypeException(" Invalid ExpenseType ");
        }
    }
}
