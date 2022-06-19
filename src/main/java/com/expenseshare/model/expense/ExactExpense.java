package com.expenseshare.model.expense;

import com.expenseshare.commands.Utils;
import com.expenseshare.model.User;
import com.expenseshare.model.splits.ExactSplit;
import com.expenseshare.model.splits.PercentSplit;
import com.expenseshare.model.splits.Splits;

import java.util.List;

public class ExactExpense extends Expense {


    public ExactExpense(User paidBy, String title, double amount) {
        super( paidBy, title, amount);
        this.setExpenseType(ExpenseType.EXACT);
    }

    public boolean validateSplits() {
        for (Splits split : getSplits()) {
            if (!(split instanceof ExactSplit)) {
                return false;
            }
        }
        double totalAmount = getAmount();
        double sumSplitAmount = 0;
        for (Splits split : getSplits()) {
            ExactSplit exactSplit = (ExactSplit) split;
            sumSplitAmount += exactSplit.getAmount();
        }

        if (totalAmount != sumSplitAmount) {
            return false;
        }

        return true;
    }

    @Override
    public void calculateExpense() {
        List<Splits> splits = getSplits();
        double sum = 0;
        for(Splits s : splits) {
            sum += s.getAmount();
        }

        if(!Utils.isApproxEqual(sum, getAmount())) {
            splits.get(0).setAmount(splits.get(0).getAmount() + getAmount() - sum);
        }
    }

}
