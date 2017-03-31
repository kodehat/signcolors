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

public class ColorSymbolCommand extends AbstractCommand {

    public ColorSymbolCommand(SignColors plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.colorsymbol")) {
            Message.sendWithLogo(sender, lang.getLang("nocmd"));
            return;
        }
        if (args.length == 1) {
            Message.sendWithLogo(sender, "&a/sc colorsymbol &c[symbol]");
        } else {
            if (args[1].length() == 1) {
                this.getPlugin().getConfig().set("colorsymbol", args[1]);
                this.getPlugin().saveConfig();
                Message.sendWithLogo(sender, lang.getLang("csch") + " &c" + args[1]);
            } else {
                Message.sendWithLogo(sender, lang.getLang("cschtma"));
            }
        }
    }
}