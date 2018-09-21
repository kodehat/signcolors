package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.Command
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import de.codehat.signcolors.util.sendColoredMsg
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class ColorcodesCommand: Command() {

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>) {
        if (!sender.hasPermission(Permissions.CMD_COLOR_CODES)) {
            sender.sendLogoMsg(LanguageKey.NO_PERMISSION)
            return
        }

        sender.sendColoredMsg("&6+----&6&o[&3&o${SignColors.languageConfig.get(LanguageKey.COLOR_CODES_COLORS)}&6&o]&6----+")
        sender.sendMessage("" + ChatColor.BLACK + "&0 " + ChatColor.DARK_BLUE + " &1 " + ChatColor.DARK_GREEN
                + " &2 " + ChatColor.DARK_AQUA + " &3")
        sender.sendMessage("" + ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + " &5 " + ChatColor.GOLD
                + " &6 " + ChatColor.GRAY + " &7")
        sender.sendMessage("" + ChatColor.DARK_GRAY + "&8 " + ChatColor.BLUE + " &9 " + ChatColor.GREEN + " &a "
                + ChatColor.AQUA + " &b")
        sender.sendMessage("" + ChatColor.RED + "&c " + ChatColor.LIGHT_PURPLE + " &d " + ChatColor.YELLOW + " &e "
                + ChatColor.WHITE + " &f")

        sender.sendColoredMsg("&6+---&6&o[&3&o${SignColors.languageConfig.get(LanguageKey.COLOR_CODES_FORMATS)}&6&o]&6---+")
        sender.sendMessage("" + ChatColor.RESET + "&k " + ChatColor.MAGIC + "Magic")
        sender.sendMessage("&r Reset")
        sender.sendMessage("" + ChatColor.BOLD + "&l " + ChatColor.RESET + ChatColor.STRIKETHROUGH + " &m"
                + ChatColor.RESET)
        sender.sendMessage("" + ChatColor.UNDERLINE + "&n " + ChatColor.RESET + ChatColor.ITALIC + " &o "
                + ChatColor.RESET)
        sender.sendColoredMsg("&6+----------------+")
    }
}