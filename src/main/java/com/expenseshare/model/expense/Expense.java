package com.expenseshare.model.expense;

import com.expenseshare.model.splits.Splits;
import com.expenseshare.model.User;

import java.io.IOException;
import java.util.List;

public abstract class Expense {
    private long id;
    private String title;
    private User paidBy;
    private User createdBy;
    private double amount;
    private List<Splits> splits;
    private ExpenseType expenseType;
    private String notes;

    private static long newId = 0;

    public Expense(User paidBy, String title, double amount) {
        newId = newId++;
        setPaidBy(paidBy);
        setAmount(amount);
        setTitle(title);
    }

    public void setSplits(List<Splits> splits) throws IOException {
        this.splits = splits;
        validateSplits();
            calculateExpense();

    }


    public void addSplits(Splits s) throws IOException {
        splits.add(s);
   validateSplits();
            calculateExpense();

    }

    public abstract boolean validateSplits() throws IOException;

    public abstract void calculateExpense();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Splits> getSplits() {
        return splits;
    }


    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
