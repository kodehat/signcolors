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

public class InfoCommand extends AbstractCommand {

    public InfoCommand(SignColors plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.info")) {
            Message.sendWithLogo(sender, lang.getLang("nocmd"));
            return;
        }
        Message.send(sender, "&6+--------------------[&3SignColors&6]--------------------+");
        Message.send(sender, "&6" + lang.getLang("sciauthor") + " &aCodeHat");
        Message.send(sender, "&6Version:&a " + this.getPlugin().getDescription().getVersion());
        Message.send(sender, "&6" + lang.getLang("scicmd") + " &a" + lang.getLang("scicmdh"));
        Message.send(sender, "&6+--------------------------------------------------+");
    }
}
