package de.codehat.signcolors.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.codehat.signcolors.languages.LanguageLoading;

/**
 * SignColors
 * @author CodeHat
 */

public class Message {

	public static LanguageLoading lang = null;

	/**
	 * Sends a message with logo.
	 * @param sender CommadSender.
	 * @param message Message to send.
	 */
	public static void sendLogoMsg(CommandSender sender, String message) {
		sender.sendMessage(replaceColors(lang.getLang("sclogo") + message));
	}

	/**
	 * Sends a blank message.
	 * @param sender CommandSender.
	 * @param message Message to send.
	 */
	public static void sendMsg(CommandSender sender, String message) {
		sender.sendMessage(replaceColors(message));
	}

	/**
	 * Creates a colored string.
	 * @param message Message to translate colorcodes.
	 * @return A translated string.
	 */
	public static String replaceColors(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	/**
	 * Creates a colored string with specific character.
	 * @param cc Character to use.
	 * @param message Message to translate colorcodes.
	 * @return A translated string.
	 */
	public static String replaceColors(char cc, String message) {
		return ChatColor.translateAlternateColorCodes(cc, message);
	}

}
