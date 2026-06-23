package me.coddrago.heroku.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private static final Map<String, CommandBase> COMMANDS = new HashMap<>();

    public static void register(CommandBase command) {
        COMMANDS.put(command.getName().toLowerCase(), command);
    }

    public static CommandBase get(String name) {
        return COMMANDS.get(name.toLowerCase());
    }

    public static Map<String, CommandBase> getAll() {
        return COMMANDS;
    }
}
