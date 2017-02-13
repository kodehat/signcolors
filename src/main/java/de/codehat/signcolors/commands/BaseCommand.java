/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand {

    protected SignColors plugin;
    protected LanguageLoader lang;

    public BaseCommand(SignColors plugin, LanguageLoader lang) {
        this.plugin = plugin;
        this.lang = lang;
    }

    public abstract void onCommand(CommandSender sender, Command cmd, String label, String[] args);
}