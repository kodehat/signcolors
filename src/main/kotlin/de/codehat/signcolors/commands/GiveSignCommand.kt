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
import de.codehat.signcolors.util.SignUtil
import de.codehat.signcolors.util.color
import de.codehat.signcolors.util.hasPermission
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender

class GiveSignCommand(plugin: SignColors) : Command(plugin) {
  companion object {
    private const val MIN_ARGS = 1
    private const val MAX_ARGS = 4
    private const val MIN_SIGN_AMOUNT = 1
    private const val MAX_SIGN_AMOUNT = 16
  }

  override fun onCommand(
    sender: CommandSender,
    command: org.bukkit.command.Command,
    label: String,
    args: Array<out String>,
  ) {
    if (!sender.hasPermission(Permissions.CMD_GIVE)) {
      plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_PERMISSION_MISSING)
      return
    }

    if (args.size == MIN_ARGS || args.size < MAX_ARGS) {
      plugin.sendLogoMessage(
        sender,
        "&6/sc &egivesign &c[&e${
          plugin.getTranslation()?.t(TranslationConfigKey.PARAMETER_PLAYER)
        }&c] &c[&e${
          plugin.getTranslation()?.t(TranslationConfigKey.PARAMETER_AMOUNT)
        }&c] &c[&e${
          plugin.getTranslation()?.t(TranslationConfigKey.PARAMETER_SIGN_MATERIAL)
        }&c]".color(),
      )
      return
    }

    val player = Bukkit.getPlayerExact(args[1])

    if (player == null) {
      plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_PLAYER_NOT_AVAILABLE)
      return
    }

    if (player.inventory.firstEmpty() == -1) {
      plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_NOT_ENOUGH_SPACE)
      return
    }

    val signAmountString = args[2]
    if (
      signAmountString.toIntOrNull() == null ||
      signAmountString.toInt() < MIN_SIGN_AMOUNT ||
      signAmountString.toInt() > MAX_SIGN_AMOUNT
    ) {
      plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_GIVE_AMOUNT_INVALID)
      return
    }
    val signAmount = signAmountString.toInt()

    val signMaterialName = args[3]
    if (!SignUtil.getAllSignMaterials().map { it.name.uppercase() }.contains(signMaterialName.uppercase())) {
      plugin.sendLogoMessage(sender, TranslationConfigKey.ERROR_SIGN_MATERIAL_INVALID)
      return
    }
    val signMaterial = Material.valueOf(signMaterialName)

    val playerStack = plugin.coloredSignManager.coloredSignStacks[signMaterial]!!
    playerStack.amount = signAmount
    player.inventory.addItem(playerStack)

    plugin.sendLogoMessage(
      sender,
      TranslationConfigKey.GIVE_DONOR_SUCCESS,
      signAmount,
      player.name,
    )

    plugin.sendLogoMessage(
      sender,
      TranslationConfigKey.GIVE_TARGET_SUCCESS,
      signAmount,
      sender.name,
    )
  }
}
