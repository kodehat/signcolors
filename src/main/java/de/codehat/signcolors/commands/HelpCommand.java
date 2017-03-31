/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * This class represents the '/sc help' command.
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand(SignColors plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.help")) {
            Message.sendWithLogo(sender, this.getPlugin().getStr("NOCMDACCESS"));
            return;
        }
        Message.send(sender, "&6+--------------------&r" + this.getPlugin().getStr("TAG").trim()
                + "&r&6--------------------+");
        if (sender.hasPermission("signcolors.info"))
            Message.send(sender, "&6/sc &a--- " + this.getPlugin().getStr("SC"));
        if (sender.hasPermission("signcolors.help"))
            Message.send(sender, "&6/sc help &a--- " + this.getPlugin().getStr("SCHELP"));
        if (sender.hasPermission("signcolors.reload"))
            Message.send(sender, "&6/sc reload &a--- " + this.getPlugin().getStr("SCRE"));
        if (sender.hasPermission("signcolors.givesign"))
            Message.send(sender, "&6/sc givesign [player] [amount] &a--- "
                    + this.getPlugin().getStr("GSHELP"));
        if (sender.hasPermission("signcolors.listcodes"))
            Message.send(sender, "&6/sc colorcodes &a--- " + this.getPlugin().getStr("COLORCODES"));
        Message.send(sender, "&6+--------------------------------------------------+");
    }

}