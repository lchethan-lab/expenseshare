package com.expenseshare.model.expense;

import com.expenseshare.commands.Utils;
import com.expenseshare.model.splits.PercentSplit;
import com.expenseshare.model.splits.Splits;
import com.expenseshare.model.User;

import java.io.IOException;
import java.util.List;

public class PercentExpense extends Expense {

    public PercentExpense(User paidBy, String title, double amount) {
        super(paidBy, title, amount);
        this.setExpenseType(ExpenseType.PERCENT);
    }

    public boolean validateSplits() throws IOException {
        for (Splits split : getSplits()) {
            if (!(split instanceof PercentSplit)) {
                return false;
            }
        }

        double totalPercent = 100;
        double sumSplitPercent = 0;
        for (Splits split : getSplits()) {
            PercentSplit percentSplit = (PercentSplit) split;
            sumSplitPercent += percentSplit.getPercent();
        }

        if (totalPercent != sumSplitPercent) {
            throw new IOException("Illegal spilt");
        }

        return true;
    }

    @Override
    public void calculateExpense() {
        List<Splits> splits = getSplits();
        double sum = 0;
        double amount;
        for (Splits s : splits) {
            PercentSplit ps = (PercentSplit) s;
            amount = Utils.roundOff(getAmount() * ps.getPercent() / 100.0d);
            s.setAmount(amount);
            sum += s.getAmount();
        }

        if (!Utils.isApproxEqual(sum, getAmount())) {
            splits.get(0).setAmount(splits.get(0).getAmount() + getAmount() - sum);
        }
    }
}

