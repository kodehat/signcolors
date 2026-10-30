// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.Command
import de.codehat.signcolors.configs.TranslationConfigKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import org.bukkit.command.CommandSender

class InfoCommand(
  plugin: SignColors,
) : Command(plugin) {
  override fun onCommand(
    sender: CommandSender,
    command: org.bukkit.command.Command,
    label: String,
    args: Array<out String>,
  ) {
    if (!sender.hasPermission(Permissions.CMD_INFO)) {
      plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_PERMISSION_MISSING)
      return
    }

    with(sender) {
      plugin.sendColoredMessage(
        this,
        "&6+--------------------&r${
          plugin.getTranslation()?.t(TranslationConfigKey.TAG)
        }&r&6--------------------+",
      )
      plugin.sendColoredMessage(
        this,
        " ${plugin.getTranslation()?.t(TranslationConfigKey.INFO_AUTHOR)} &a${
          plugin.description.authors.joinToString(",")
        }",
      )
      plugin.sendColoredMessage(
        this,
        " ${plugin.getTranslation()?.t(TranslationConfigKey.INFO_VERSION)} &a${
          plugin.description.version
        }",
      )
      plugin.sendColoredMessage(
        this,
        " ${plugin.getTranslation()?.t(TranslationConfigKey.COMMAND_HELP)}",
      )
      plugin.sendColoredMessage(
        this,
        "&6+---------------------------------------------------+",
      )
    }
  }
}
