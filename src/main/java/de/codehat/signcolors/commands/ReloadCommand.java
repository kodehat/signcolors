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

public class ReloadCommand extends AbstractCommand {

    public ReloadCommand(SignColors plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.reload")) {
            Message.sendWithLogo(sender, lang.getLang("nocmd"));
            return;
        }
        this.getPlugin().getSignManager().oldSignAmount = this.getPlugin().getConfig().getInt("signamount.crafting");
        this.getPlugin().reloadConfig();
        this.lang.setupLanguage();
        this.lang.loadLanguage();
        this.getPlugin().getSignManager().removeRecipe();
        this.getPlugin().getSignManager().setupColoredSigns();
        this.getPlugin().loadDatabase();
        Message.sendWithLogo(sender, lang.getLang("configre"));
    }
}