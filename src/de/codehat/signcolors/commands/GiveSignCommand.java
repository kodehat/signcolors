package de.codehat.signcolors.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoading;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;

/**
 * SignColors
 * @author CodeHat
 */

public class GiveSignCommand extends BaseCommand {

	public GiveSignCommand(SignColors plugin, LanguageLoading lang) {
		super(plugin, lang);
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("signcolors.givesign")) {
			Message.sendLogoMsg(sender, lang.getLang("nocmd"));
			return;
		}
		if (args.length == 1) {
			Message.sendLogoMsg(sender, "&a/sc givesign &c[player] &c[amount]");
			return;
		} else {
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p == null) {
				Message.sendLogoMsg(sender, lang.getLang("pnoton"));
				return;
			} else {
				if (p.getInventory().firstEmpty() == -1) {
					Message.sendMsg(sender, lang.getLang("notenspace"));
					return;
				} else {
					if (!Utils.isInteger(args[2]) || Integer.valueOf(args[2]) < 0 || Integer.valueOf(args[2]) > 64) {
						Message.sendLogoMsg(sender, lang.getLang("invamaount"));
						return;
					} else {
						p.getInventory().addItem(this.plugin.getSign(Integer.valueOf(args[2])));
						Message.sendLogoMsg(sender, String.format(lang.getLang("givesign"), p.getName(), String.valueOf(Integer.valueOf(args[2]))));
					}
				}
				return;
			}
		}
	}
}
