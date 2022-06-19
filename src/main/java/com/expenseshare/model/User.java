package com.expenseshare.model;

import com.expenseshare.model.expense.Expense;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id = 0;
    String name;
    String email;
    String phoneNumber;
    private List<Expense> expenses;
    static long auto_inc_id = 0;

    public User(String name, String email) {
        setId(auto_inc_id++);
        setEmail(email);
        setName(name);
        expenses=new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
