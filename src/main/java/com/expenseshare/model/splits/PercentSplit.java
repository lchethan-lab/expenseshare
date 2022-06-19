package com.expenseshare.model.splits;

import com.expenseshare.model.User;

public class PercentSplit extends Splits {
    private double percent;

    public PercentSplit(User user, double percent) {
        super(user);
        setPercent(percent);
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent){
        this.percent = percent;
    }
}
