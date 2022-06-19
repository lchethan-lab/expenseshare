package com.expenseshare.model.splits;

import com.expenseshare.exceptions.InvalidExpenseTypeException;
import com.expenseshare.model.User;
import com.expenseshare.model.expense.ExpenseType;
import com.expenseshare.model.splits.EqualSplit;
import com.expenseshare.model.splits.ExactSplit;
import com.expenseshare.model.splits.PercentSplit;
import com.expenseshare.model.splits.Splits;

import java.io.IOException;

public class SplitFactory {

    public static Splits createSplit(ExpenseType type, User user, double amountOrPercent) throws InvalidExpenseTypeException {
        switch (type) {
            case EXACT:
                return new ExactSplit(user, amountOrPercent);
            case PERCENT:
                return new PercentSplit(user, amountOrPercent);
            case EQUAL:
                return new EqualSplit(user);
            default:
                throw new InvalidExpenseTypeException("Invalid Expense Type");
        }
    }

    public static Splits createSplit(ExpenseType type, User user) throws InvalidExpenseTypeException, IOException {
        if (!type.equals(ExpenseType.EQUAL)) {
            throw new IOException(type.toString() + "needs the amount!");
        }
        return createSplit(type, user, 0.0d);
    }
}
