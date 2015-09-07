package de.codehat.signcolors.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoading;
import de.codehat.signcolors.util.Message;

/**
 * SignColors
 * @author CodeHat
 */

public class ReloadCommand extends BaseCommand {

	public ReloadCommand(SignColors plugin, LanguageLoading lang) {
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
		this.lang.loadLanguages();
		this.plugin.setupSigns();
		this.plugin.loadDatabase();
		Message.sendLogoMsg(sender, lang.getLang("configre"));
		return;
	}
}