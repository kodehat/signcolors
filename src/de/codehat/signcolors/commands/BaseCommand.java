/*
 * Copyright (c) 2015 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoading;

public abstract class BaseCommand {

	protected SignColors plugin;
	protected LanguageLoading lang;

	public BaseCommand(SignColors plugin, LanguageLoading lang) {
		this.plugin = plugin;
		this.lang = lang;
	}

	public abstract void onCommand(CommandSender sender, Command cmd, String label, String[] args);
}