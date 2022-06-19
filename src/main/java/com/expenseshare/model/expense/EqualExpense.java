package com.expenseshare.model.expense;

import com.expenseshare.commands.Utils;
import com.expenseshare.model.User;
import com.expenseshare.model.splits.EqualSplit;
import com.expenseshare.model.splits.PercentSplit;
import com.expenseshare.model.splits.Splits;

import java.util.List;

public class EqualExpense extends Expense {


    public EqualExpense(User paidBy, String title, double amount) {
        super(paidBy, title, amount);
        this.setExpenseType(ExpenseType.EQUAL);
    }

    public boolean validateSplits() {
        for (Splits split : getSplits()) {
            if (!(split instanceof EqualSplit)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void calculateExpense() {

        List<Splits> splits = getSplits();
        int numUsers = getSplits().size();
        double sum = 0;
        double amount;
        for (Splits s : splits) {
            amount = Utils.roundOff(getAmount() / numUsers);
            s.setAmount(amount);
            sum += s.getAmount();
        }

        if (!Utils.isApproxEqual(sum, getAmount())) {
            splits.get(0).setAmount(splits.get(0).getAmount() + getAmount() - sum);
        }
    }
}
