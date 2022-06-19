package com.expenseshare.controller;

import com.expenseshare.exceptions.InvalidExpenseTypeException;
import com.expenseshare.exceptions.NoSuchUserException;
import com.expenseshare.model.expense.ExpenseType;
import com.expenseshare.model.User;
import com.expenseshare.model.expense.Expense;
import com.expenseshare.model.expense.ExpenseFactory;
import com.expenseshare.model.splits.Splits;

import java.io.IOException;
import java.util.*;

//add_expense exact test 30 0 0 0 5 1 15 2 10
//add_expense equal test 30 0 0 0 1 2
//add_expense percent test 30 0 0 0 50 1 25 2 25
public class ExpenseManager {
    private Map<Long, Expense> expenses;
    private Map<Long, User> users;
    private Map<String, User> userByEmail;
    private Map<User, Map<User, Double>> balances;

    private static ExpenseManager INSTANCE;

    private ExpenseManager() {
        expenses = new HashMap<Long, Expense>();
        users = new HashMap<Long, User>();
        userByEmail = new HashMap<String, User>();
        balances = new HashMap<User, Map<User, Double>>();
    }


    public static ExpenseManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ExpenseManager();
        return INSTANCE;
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
        if (user.getEmail() != null || !user.getEmail().isEmpty())
            userByEmail.put(user.getEmail(), user);
        Map<User, Double> userBalanceMap = new HashMap<>();
        balances.put(user, userBalanceMap);
        System.out.println(user.toString() + " added successfully!");
    }

    public void addExpense(String name,
                           ExpenseType type,
                           User createdBy,
                           User paidBy,
                           List<Splits> splits,
                           double totalAmount) throws InvalidExpenseTypeException, NoSuchUserException, IOException {
        Expense e = ExpenseFactory.createExpense(type, name, paidBy, totalAmount);
        expenses.put(e.getId(), e);
        createdBy.getExpenses().add(e);
        e.setPaidBy(paidBy);
        e.setSplits(splits);

        if (!balances.containsKey(paidBy))
            throw new NoSuchUserException("Please add the user before adding their expenses");

        Map<User, Double> userBalances;
        for (Splits s : splits) {
            User paidTo = s.getUser();
            if (paidBy.equals(paidTo))
                continue;

            userBalances = balances.get(paidBy);
            userBalances.put(paidTo, s.getAmount() + userBalances.getOrDefault(paidTo, 0.0d));
            balances.put(paidBy, userBalances);

            if (!balances.containsKey(paidTo))
                throw new NoSuchUserException("Please add the user before adding their expenses");
            userBalances = balances.get(paidTo);
            userBalances.put(paidBy, s.getAmount() + userBalances.getOrDefault(paidBy, 0.0d));
            balances.put(paidTo, userBalances);
        }
    }

    public void addExpense(Expense e) throws InvalidExpenseTypeException, NoSuchUserException, IOException {

        expenses.put(e.getId(), e);
        e.getCreatedBy().getExpenses().add(e);
        User paidBy = e.getPaidBy();


        if (!balances.containsKey(paidBy))
            throw new NoSuchUserException("Please add the user before adding their expenses");

        Map<User, Double> userBalances;
        for (Splits s : e.getSplits()) {
            User paidTo = s.getUser();
            if (paidBy.equals(paidTo))
                continue;

            userBalances = balances.get(paidBy);
            userBalances.put(paidTo, -1 *s.getAmount() + userBalances.getOrDefault(paidTo, 0.0d));
            balances.put(paidBy, userBalances);

            if (!balances.containsKey(paidTo))
                throw new NoSuchUserException("Please add the user before adding their expenses");
            userBalances = balances.get(paidTo);
            userBalances.put(paidBy, s.getAmount() + userBalances.getOrDefault(paidBy, 0.0d));
            balances.put(paidTo, userBalances);
        }
    }

    // display test balances of all users
    public void showAllBalances() {
        for (User user1 : balances.keySet()) {
            showBalance(user1);
        }
    }

    public void showBalance(User user) {
        Map<User, Double> userBalances = balances.get(user);
        boolean hadBalance = false;
        for (User user1 : userBalances.keySet()) {
            double amount = userBalances.get(user1);
            // print user1 owes amount to user2
            if (amount > 0) {
                System.out.println(user.getName() + " owes " + amount + " to " + user1.getName());
                hadBalance = true;
            } else if (amount < 0) {
                System.out.println(user.getName() + " takes " + (-amount) + " from " + user1.getName());
                hadBalance = true;
            }
        }
        if (!hadBalance) {
            System.out.println(user.getName() + " has no dues!");
        }
    }

    public void showBalance(Long userId) throws NoSuchUserException {
        showBalance(getUser(userId));
    }

    public List<Expense> getUserExpenses(User user) {
        return user.getExpenses();
    }

    public List<Expense> getUserExpenses(Long userId) throws NoSuchUserException {
        return getUserExpenses(getUser(userId));
    }

    public User getUser(Long uid) throws NoSuchUserException {
        if (!users.containsKey(uid))
            throw new NoSuchUserException("User with uid=" + uid + " doesn't exist!");
        return users.get(uid);
    }

    public User getUser(String email) throws NoSuchUserException {
        if (!userByEmail.containsKey(email))
            throw new NoSuchUserException("User with email=" + email + " doesn't exist!");
        return userByEmail.get(email);
    }
}
//add_expense equal 30 1 1 [1 10 2 10 3 10]