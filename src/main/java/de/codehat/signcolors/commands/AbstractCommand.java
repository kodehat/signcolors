/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand {

    private SignColors plugin;

    AbstractCommand(SignColors plugin) {
        this.plugin = plugin;
    }

    /**
     * Get the plugin instance.
     *
     * @return The plugin instance.
     */
    public SignColors getPlugin() {
        return this.plugin;
    }

    public abstract void onCommand(CommandSender sender, Command cmd, String label, String[] args);
}