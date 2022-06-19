package com.expenseshare.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory implements Command {
    Map<String, Command> commandMap;

    private static CommandFactory Instance;

    private CommandFactory() {
        commandMap = new HashMap<>();
        commandMap.put("add_user", new AddUserCommand());
        commandMap.put("add_expense", new AddExpenseCommand());
        commandMap.put("show", new ShowCommand());
    }

    public static CommandFactory getInstance() {
        if (Instance == null)
            Instance = new CommandFactory();
        return Instance;
    }

    @Override
    public void execute(String[] cmd) {
        try {
            commandMap.get(cmd[0]).execute(cmd);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
