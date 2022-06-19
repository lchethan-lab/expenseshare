package com.expenseshare.model.splits;
import com.expenseshare.model.User;

public abstract class Splits {
    private User user;
    private Double amount;

    public Splits(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setUser(User user) {
        this.user = user;

    }

    public Double getAmount() {
        return amount;
    }
}
