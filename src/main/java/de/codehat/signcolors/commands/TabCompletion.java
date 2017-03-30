/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("signcolors.help")) commands.add("help");
            if (sender.hasPermission("signcolors.reload")) commands.add("reload");
            if (sender.hasPermission("signcolors.colorsymbol")) commands.add("colorsymbol");
            if (sender.hasPermission("signcolors.givesign")) commands.add("givesign");
            if (sender.hasPermission("signcolors.listcodes")) commands.add("colorcodes");

            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
