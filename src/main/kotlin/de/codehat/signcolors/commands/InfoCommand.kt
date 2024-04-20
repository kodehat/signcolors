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
import org.bukkit.command.CommandSender

class InfoCommand(plugin: SignColors) : Command(plugin) {
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
