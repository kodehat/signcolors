/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class represents the '/sc givesign' command.
 */
public class GiveSignCommand extends AbstractCommand {

    public GiveSignCommand(SignColors plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.givesign")) {
            Message.sendWithLogo(sender, this.getPlugin().getStr("NOCMDACCESS"));
            return;
        }
        if (args.length == 1 || args.length > 3) {
            Message.sendWithLogo(sender, "&a/sc givesign &c[player] &c[amount]");
        } else {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                Message.sendWithLogo(sender, this.getPlugin().getStr("PNOTON"));
            } else {
                if (player.getInventory().firstEmpty() == -1) {
                    Message.send(sender, this.getPlugin().getStr("NOTENSPACE"));
                } else {
                    if (!Utils.isInteger(args[2]) || Integer.valueOf(args[2]) < 1 || Integer.valueOf(args[2]) > 64) {
                        Message.sendWithLogo(sender, this.getPlugin().getStr("INVAMOUNT"));
                    } else {
                        player.getInventory().addItem(this.getPlugin().getSignManager().getSign(Integer.valueOf(args[2])));
                        Message.sendWithLogo(sender, String.format(this.getPlugin().getStr("GIVESIGN"),
                                player.getName(), String.valueOf(Integer.valueOf(args[2]))));
                        Message.sendWithLogo(player, String.format(this.getPlugin().getStr("GETSIGN"),
                                String.valueOf(Integer.valueOf(args[2]))));
                    }
                }
            }
        }
    }
}
