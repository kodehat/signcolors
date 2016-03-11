/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.util.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ColorCodesCommand extends BaseCommand {

    public ColorCodesCommand(SignColors plugin, LanguageLoader lang) {
        super(plugin, lang);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.listcodes")) {
            Message.sendLogoMsg(sender, lang.getLang("nocmd"));
            return;
        }
        Message.sendMsg(sender, "&6+----&6&o[&3&o" + lang.getLang("colorlist") + "&6&o]&6----+");
        sender.sendMessage(ChatColor.BLACK + "&0 " + ChatColor.DARK_BLUE + " &1 " + ChatColor.DARK_GREEN
                + " &2 " + ChatColor.DARK_AQUA + " &3");
        sender.sendMessage(ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + " &5 " + ChatColor.GOLD
                + " &6 " + ChatColor.GRAY + " &7");
        sender.sendMessage(ChatColor.DARK_GRAY + "&8 " + ChatColor.BLUE + " &9 " + ChatColor.GREEN + " &a "
                + ChatColor.AQUA + " &b");
        sender.sendMessage(ChatColor.RED + "&c " + ChatColor.LIGHT_PURPLE + " &d " + ChatColor.YELLOW + " &e "
                + ChatColor.WHITE + " &f");

        Message.sendMsg(sender, "&6+---&6&o[&3&o" + lang.getLang("formatlist") + "&6&o]&6---+");
        sender.sendMessage(ChatColor.RESET + "&k " + ChatColor.MAGIC + "Magic");
        sender.sendMessage("&r Reset");
        sender.sendMessage(ChatColor.BOLD + "&l " + ChatColor.RESET + ChatColor.STRIKETHROUGH + " &m"
                + ChatColor.RESET);
        sender.sendMessage(ChatColor.UNDERLINE + "&n " + ChatColor.RESET + ChatColor.ITALIC + " &o "
                + ChatColor.RESET);
        Message.sendMsg(sender, "&6+----------------+");
    }

}
