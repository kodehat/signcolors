// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
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
      if (sender.hasPermission(Permissions.CMD_CODES)) {
        commands.add(CommandManager.CMD_CODES)
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
      // TODO: Add all player names if arg is empty.
      val playerNames =
        Bukkit
          .getServer()
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
      // TODO: Add all sign material names if arg is empty.
      val signMaterials = SignUtil.getAllSignMaterials().map { it.name }

      StringUtil.copyPartialMatches(args[3], signMaterials, completions)
    }

    completions.sort()
    return completions
  }
}
