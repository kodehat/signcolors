/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
        Message.send(sender, "&6+--------------------[&3SignColors&6]--------------------+");
        Message.send(sender, "&6/sc &a--- " + this.getPlugin().getStr("SC"));
        Message.send(sender, "&6/sc help &a--- " + this.getPlugin().getStr("SCHELP"));
        Message.send(sender, "&6/sc reload &a--- " + this.getPlugin().getStr("SCRE"));
        Message.send(sender, "&6/sc colorsymbol [symbol] &a--- " + this.getPlugin().getStr("SCCS"));
        Message.send(sender, "&6/sc givesign [player] [amount] &a--- "
                + this.getPlugin().getStr("GSHELP"));
        Message.send(sender, "&6/sc colorcodes &a--- " + this.getPlugin().getStr("COLORCODES"));
        Message.send(sender, "&6+--------------------------------------------------+");
    }

}