// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.command

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.configs.TranslationConfigKey
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandManager(
  private val plugin: SignColors,
) : CommandExecutor {
  private val commands: MutableMap<String, Command> = mutableMapOf()

  companion object {
    const val CMD_PREFIX = "sc"
    const val CMD_INFO = ""
    const val CMD_HELP = "help"
    const val CMD_RELOAD = "reload"
    const val CMD_CODES = "codes"
    const val CMD_GIVE_SIGN = "givesign"
  }

  override fun onCommand(
    sender: CommandSender,
    command: org.bukkit.command.Command,
    label: String,
    args: Array<out String>,
  ): Boolean {
    when {
      args.isEmpty() -> {
        commands[""]?.onCommand(sender, command, label, args)
      }

      commands.containsKey(args[0]) -> {
        commands[args[0]]?.onCommand(sender, command, label, args)
      }

      else -> {
        plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_COMMAND_UNKNOWN)
      }
    }
    return true
  }

  fun registerCommand(
    command: String,
    commandObject: Command,
  ): Boolean = commands.put(command, commandObject) == null
}
