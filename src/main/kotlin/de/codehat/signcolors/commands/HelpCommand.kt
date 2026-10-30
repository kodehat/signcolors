// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.Command
import de.codehat.signcolors.configs.TranslationConfigKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import org.bukkit.command.CommandSender

class HelpCommand(
  plugin: SignColors,
) : Command(plugin) {
  override fun onCommand(
    sender: CommandSender,
    command: org.bukkit.command.Command,
    label: String,
    args: Array<out String>,
  ) {
    if (!sender.hasPermission(Permissions.CMD_HELP)) {
      plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_PERMISSION_MISSING)
      return
    }

    plugin.sendColoredMessage(
      sender,
      """
            |${plugin.getTranslation()?.t(TranslationConfigKey.TAG)} ${
        plugin.getTranslation()?.t(TranslationConfigKey.COMMAND_HELP_PAGE)
      }&r
            | &c[] ${plugin.getTranslation()?.t(TranslationConfigKey.PARAMETER_REQUIRED)}, &7<> ${
        plugin.getTranslation()?.t(TranslationConfigKey.PARAMETER_OPTIONAL)
      }&r
            ${if (sender.hasPermission(Permissions.CMD_INFO)) "| &7&l- &r&6/sc" else ""}&r
            ${if (sender.hasPermission(Permissions.CMD_HELP)) "| &7&l- &r&6/sc &ehelp" else ""}&r
            ${if (sender.hasPermission(Permissions.CMD_RELOAD)) "| &7&l- &r&6/sc &ereload" else ""}&r
            ${
        if (sender.hasPermission(Permissions.CMD_GIVE)) {
          "| &7&l- &r&6/sc &egivesign &c[&e${
            plugin.getTranslation()?.t(TranslationConfigKey.PARAMETER_PLAYER)
          }&c] &c[&e${
            plugin.getTranslation()?.t(TranslationConfigKey.PARAMETER_AMOUNT)
          }&c] &c[&e${
            plugin.getTranslation()?.t(TranslationConfigKey.PARAMETER_SIGN_MATERIAL)
          }&c]"
        } else {
          ""
        }
      }&r
            ${if (sender.hasPermission(Permissions.CMD_CODES)) "| &7&l- &r&6/sc &ecodes" else ""}&r
      """.trimMargin(),
    )
  }
}
