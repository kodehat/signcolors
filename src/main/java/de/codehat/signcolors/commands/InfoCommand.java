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
 * This class represents the '/sc' command.
 */
public class InfoCommand extends AbstractCommand {

    public InfoCommand(SignColors plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.info")) {
            Message.sendWithLogo(sender, this.getPlugin().getStr("NOCMDACCESS"));
            return;
        }
        Message.send(sender, "&6+--------------------&r" + this.getPlugin().getStr("TAG").trim()
                + "&r&6--------------------+");
        Message.send(sender, " &6" + this.getPlugin().getStr("SCINFOAUTHOR") + " &aCodeHat");
        Message.send(sender, " &6Version:&a " + this.getPlugin().getDescription().getVersion());
        Message.send(sender, " &6" + this.getPlugin().getStr("SCINFOCMD") + " &a"
                + this.getPlugin().getStr("SCINFOCMDH"));
        Message.send(sender, "&6+--------------------------------------------------+");
    }
}
