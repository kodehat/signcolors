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

public class HelpCommand extends BaseCommand {

    public HelpCommand(SignColors plugin, LanguageLoader lang) {
        super(plugin, lang);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.help")) {
            Message.sendLogoMsg(sender, lang.getLang("nocmd"));
            return;
        }
        Message.sendMsg(sender, "&6+--------------------[&3SignColors&6]--------------------+");
        Message.sendMsg(sender, "&6/sc &a--- " + lang.getLang("sc"));
        Message.sendMsg(sender, "&6/sc help &a--- " + lang.getLang("schelp"));
        Message.sendMsg(sender, "&6/sc reload &a--- " + lang.getLang("scre"));
        Message.sendMsg(sender, "&6/sc colorsymbol [symbol] &a--- " + lang.getLang("sccs"));
        Message.sendMsg(sender, "&6/sc givesign [player] [amount] &a--- " + lang.getLang("gshelp"));
        Message.sendMsg(sender, "&6/sc colorcodes &a--- " + lang.getLang("colorcodes"));
        Message.sendMsg(sender, "&6+--------------------------------------------------+");
    }

}