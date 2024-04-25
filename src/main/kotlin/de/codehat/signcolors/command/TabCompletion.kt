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
package de.codehat.signcolors.command

import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.SignUtil
import de.codehat.signcolors.util.hasPermission
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil

class TabCompletion : TabCompleter {
  override fun onTabComplete(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: Array<out String>,
  ): MutableList<String> {
    val completions = ArrayList<String>()
    if (args.size == 1) {
      val commands = ArrayList<String>()
      if (sender.hasPermission(Permissions.CMD_HELP)) {
        commands.add(CommandManager.CMD_HELP)
      }
      if (sender.hasPermission(Permissions.CMD_RELOAD)) {
        commands.add(CommandManager.CMD_RELOAD)
      }
      if (sender.hasPermission(Permissions.CMD_COLOR_CODES)) {
        commands.add(CommandManager.CMD_COLOR_CODES)
      }
      if (sender.hasPermission(Permissions.CMD_GIVE)) {
        commands.add(CommandManager.CMD_GIVE_SIGN)
      }

      StringUtil.copyPartialMatches(args[0], commands, completions)
    } else if (
      args.size == 2 &&
      args[1].isNotEmpty() &&
      args[0].equals(CommandManager.CMD_GIVE_SIGN, true)
    ) {
      val playerNames =
        Bukkit.getServer()
          .onlinePlayers
          .map { it.name }

      StringUtil.copyPartialMatches(args[1], playerNames, completions)
    } else if (
      args.size == 4 &&
      args[3].isNotEmpty() &&
      args[2].isNotEmpty() &&
      args[1].isNotEmpty() &&
      args[0].equals(CommandManager.CMD_GIVE_SIGN, true)
    ) {
      val signMaterials = SignUtil.getAllSignMaterials().map { it.name }

      StringUtil.copyPartialMatches(args[3], signMaterials, completions)
    }

    completions.sort()
    return completions
  }
}
