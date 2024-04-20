/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2022 CodeHat
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.Command
import de.codehat.signcolors.configs.TranslationConfigKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class ColorcodesCommand(plugin: SignColors) : Command(plugin) {
  override fun onCommand(
    sender: CommandSender,
    command: org.bukkit.command.Command,
    label: String,
    args: Array<out String>,
  ) {
    if (!sender.hasPermission(Permissions.CMD_COLOR_CODES)) {
      plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_PERMISSION_MISSING)
      return
    }

    plugin.sendColoredMessage(
      sender,
      "&6+----&6&o[&3&o${
        plugin.getTranslation()?.t(TranslationConfigKey.COLOR_CODES_COLORS)
      }&6&o]&6----+",
    )
    sender.sendMessage(
      "" +
        ChatColor.BLACK +
        "&0 " +
        ChatColor.DARK_BLUE +
        " &1 " +
        ChatColor.DARK_GREEN +
        " &2 " +
        ChatColor.DARK_AQUA +
        " &3",
    )
    sender.sendMessage(
      "" +
        ChatColor.DARK_RED +
        "&4 " +
        ChatColor.DARK_PURPLE +
        " &5 " +
        ChatColor.GOLD +
        " &6 " +
        ChatColor.GRAY +
        " &7",
    )
    sender.sendMessage(
      "" +
        ChatColor.DARK_GRAY +
        "&8 " +
        ChatColor.BLUE +
        " &9 " +
        ChatColor.GREEN +
        " &a " +
        ChatColor.AQUA +
        " &b",
    )
    sender.sendMessage(
      "" +
        ChatColor.RED +
        "&c " +
        ChatColor.LIGHT_PURPLE +
        " &d " +
        ChatColor.YELLOW +
        " &e " +
        ChatColor.WHITE +
        " &f",
    )

    plugin.sendColoredMessage(
      sender,
      "&6+---&6&o[&3&o${
        plugin.getTranslation()?.t(TranslationConfigKey.COLOR_CODES_FORMATS)
      }&6&o]&6---+",
    )
    sender.sendMessage("" + ChatColor.RESET + "&k " + ChatColor.MAGIC + "Magic")
    sender.sendMessage("&r Reset")
    sender.sendMessage(
      "" +
        ChatColor.BOLD +
        "&l " +
        ChatColor.RESET +
        ChatColor.STRIKETHROUGH +
        " &m" +
        ChatColor.RESET,
    )
    sender.sendMessage(
      "" +
        ChatColor.UNDERLINE +
        "&n " +
        ChatColor.RESET +
        ChatColor.ITALIC +
        " &o " +
        ChatColor.RESET,
    )
    plugin.sendColoredMessage(sender, "&6+----------------+")
  }
}
