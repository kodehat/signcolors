/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {

    public static String signcolorsLogo;

    /**
     * Sends a message with logo.
     *
     * @param sender  CommadSender.
     * @param message Message to send.
     */
    public static void sendWithLogo(CommandSender sender, String message) {
        sender.sendMessage(replaceColors(signcolorsLogo + message));
    }

    /**
     * Sends a blank message.
     *
     * @param sender  CommandSender.
     * @param message Message to send.
     */
    public static void send(CommandSender sender, String message) {
        sender.sendMessage(replaceColors(message));
    }

    /**
     * Creates a colored string.
     *
     * @param message Message to translate colorcodes.
     * @return A translated string.
     */
    public static String replaceColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Creates a colored string with specific character.
     *
     * @param cc      Character to use.
     * @param message Message to translate colorcodes.
     * @return A translated string.
     */
    public static String replaceColors(char cc, String message) {
        return ChatColor.translateAlternateColorCodes(cc, message);
    }

}
