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

public class ColorSymbolCommand extends BaseCommand {

    public ColorSymbolCommand(SignColors plugin, LanguageLoader lang) {
        super(plugin, lang);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.colorsymbol")) {
            Message.sendLogoMsg(sender, lang.getLang("nocmd"));
            return;
        }
        if (args.length == 1) {
            Message.sendLogoMsg(sender, "&a/sc colorsymbol &c[symbol]");
        } else {
            if (args[1].length() == 1) {
                this.plugin.getConfig().set("colorsymbol", args[1]);
                this.plugin.saveConfig();
                Message.sendLogoMsg(sender, lang.getLang("csch") + " &c" + args[1]);
            } else {
                Message.sendLogoMsg(sender, lang.getLang("cschtma"));
            }
        }
    }
}