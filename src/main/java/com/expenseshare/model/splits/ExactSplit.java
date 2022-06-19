package com.expenseshare.model.splits;

import com.expenseshare.model.User;

public class ExactSplit extends Splits {
    public ExactSplit(User user,Double amount) {
        super(user);
        setAmount(amount);
    }
}
