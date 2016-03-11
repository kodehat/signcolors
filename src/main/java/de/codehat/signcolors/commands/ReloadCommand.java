/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(SignColors plugin, LanguageLoader lang) {
        super(plugin, lang);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.reload")) {
            Message.sendLogoMsg(sender, lang.getLang("nocmd"));
            return;
        }
        this.plugin.reloadConfig();
        this.plugin.setupLogger();
        this.plugin.startMetrics();
        this.plugin.setupLanguage();
        this.lang.loadLanguage();
        this.plugin.setupSigns();
        this.plugin.loadDatabase();
        Message.sendLogoMsg(sender, lang.getLang("configre"));
    }
}