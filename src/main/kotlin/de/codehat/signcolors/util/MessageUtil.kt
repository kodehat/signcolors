package de.codehat.signcolors.util

import de.codehat.signcolors.language.LanguageKey
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

fun String.color(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun CommandSender.sendColoredMsg(message: String) {
    this.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', message))
}

fun CommandSender.sendLogoMsg(message: String) {
    this.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',
            de.codehat.signcolors.SignColors.languageConfig.get(de.codehat.signcolors.language.LanguageKey.TAG) + " " + message))
}

fun CommandSender.sendLogoMsg(languageKey: LanguageKey) {
    this.sendLogoMsg(de.codehat.signcolors.SignColors.languageConfig.get(languageKey))
}