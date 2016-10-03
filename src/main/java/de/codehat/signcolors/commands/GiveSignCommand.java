/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSignCommand extends BaseCommand {

    public GiveSignCommand(SignColors plugin, LanguageLoader lang) {
        super(plugin, lang);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.givesign")) {
            Message.sendLogoMsg(sender, lang.getLang("nocmd"));
        }
        if (args.length == 1) {
            Message.sendLogoMsg(sender, "&a/sc givesign &c[player] &c[amount]");
        } else {
            Player p = Bukkit.getPlayerExact(args[1]);
            if (p == null) {
                Message.sendLogoMsg(sender, lang.getLang("pnoton"));
            } else {
                if (p.getInventory().firstEmpty() == -1) {
                    Message.sendMsg(sender, lang.getLang("notenspace"));
                } else {
                    if (!Utils.isInteger(args[2]) || Integer.valueOf(args[2]) < 1 || Integer.valueOf(args[2]) > 64) {
                        Message.sendLogoMsg(sender, lang.getLang("invamount"));
                    } else {
                        p.getInventory().addItem(this.plugin.getSignManager().getSign(Integer.valueOf(args[2])));
                        Message.sendLogoMsg(sender, String.format(lang.getLang("givesign"), p.getName(), String.valueOf(Integer.valueOf(args[2]))));
                    }
                }
            }
        }
    }
}
