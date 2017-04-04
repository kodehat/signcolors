/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        boolean sorting = true;
        if (args.length == 1) {
            List<String> commands = new ArrayList<>();
            if (sender.hasPermission("signcolors.help")) commands.add("help");
            if (sender.hasPermission("signcolors.reload")) commands.add("reload");
            if (sender.hasPermission("signcolors.givesign")) commands.add("givesign");
            if (sender.hasPermission("signcolors.listcodes")) commands.add("colorcodes");
            if (sender.hasPermission("signcolors.upgrade")) commands.add("upgrade");

            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2 && !args[1].isEmpty() && args[0].equalsIgnoreCase("givesign")) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.getName().startsWith(args[1])) completions.add(player.getName());
            }
        }
        if (sorting) Collections.sort(completions);
        return completions;
    }
}
