package com.expenseshare.commands;

import java.io.IOException;

public interface Command {
    void execute(String[] cmd) throws IOException;
}
