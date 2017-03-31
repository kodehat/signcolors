/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class CommandManager implements CommandExecutor {

    private SignColors plugin;
    private HashMap<String, AbstractCommand> commandDatabase = new HashMap<>();

    public CommandManager(SignColors plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(String command, AbstractCommand object) {
        commandDatabase.put(command, object);
    }

    private boolean exists(String name) {
        return commandDatabase.containsKey(name);
    }

    private AbstractCommand getExecutor(String name) {
        return commandDatabase.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            // Execute info command
            this.getExecutor("").onCommand(sender, cmd, label, args);
        } else if (exists(args[0])) {
            // Execute one of the other commands
            this.getExecutor(args[0]).onCommand(sender, cmd, label, args);
        } else {
            Message.sendWithLogo(sender, this.plugin.getStr("UNKNOWNCMD"));
            Message.sendWithLogo(sender, this.plugin.getStr("UNKNOWNCMDH"));
        }
        return true;
    }
}