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

class HelpCommand(plugin: SignColors) : Command(plugin) {
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
      }
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
            ${if (sender.hasPermission(Permissions.CMD_COLOR_CODES)) "| &7&l- &r&6/sc &ecolorcodes" else ""}&r
            """
        .trimMargin(),
    )
  }
}
