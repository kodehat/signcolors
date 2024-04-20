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
package de.codehat.signcolors.listener

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.configs.TranslationConfigKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.SoundUtil
import de.codehat.signcolors.util.color
import de.codehat.signcolors.util.hasPermission
import org.bukkit.ChatColor
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack

class PlayerListener(private val plugin: SignColors) : Listener {
  @Suppress("unused")
  @EventHandler
  fun onPlayerJoinShowUpdateMessage(event: PlayerJoinEvent) {
    val player = event.player

    if (plugin.updateAvailablePair.first && player.hasPermission(Permissions.UPDATE_MESSAGE)) {
      val version = plugin.updateAvailablePair.second
      plugin.sendLogoMessage(player, TranslationConfigKey.VERSION_NEW_AVAILABLE, version)
    }
  }

  @Suppress("unused")
  @EventHandler
  fun onPlayerLeaveRemoveFromFixPlayers(event: PlayerQuitEvent) {
    val player = event.player

    plugin.fixSignPlayers.remove(player) // Return value is ignorable
  }

  @Suppress("unused")
  @EventHandler
  fun onPlayerUseSpecialSign(event: PlayerInteractEvent) {
    val player = event.player

    if (event.action != Action.RIGHT_CLICK_BLOCK) return

    if (event.clickedBlock!!.state is Sign) {
      val clickedSign = event.clickedBlock!!.state as Sign
      val indicatorLine = clickedSign.getTargetSide(player).getLine(0)
      val dataLine = clickedSign.getTargetSide(player).getLine(2)

      if (indicatorLine == SignListener.SPECIAL_SIGN_INDICATOR.color()) {
        if (player.hasPermission(Permissions.BUY_SIGN_USE)) {
          // Check if Vault is installed
          if (!plugin.isVaultAvailable().first) {
            plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_VAULT_MISSING)
            event.isCancelled = true
            return
          }

          // [0] = sign amount, [1] = sign price for the specified amount
          val signData = ChatColor.stripColor(dataLine)!!.split(":")

          // Check if written types are valid for Int and Double
          if (
            signData[0].trim().toIntOrNull() == null ||
            signData[1].trim().toDoubleOrNull() == null
          ) {
            plugin.sendLogoMessage(
              player,
              TranslationConfigKey.ERROR_BUYSIGN_PLAYER_INCORRECT,
            )
            event.isCancelled = true
            return
          }

          // Retrieve sign amount and price
          val signAmount = signData[0].trim().toInt()
          val signPrice = signData[1].trim().toDouble()

          // Check if the player has enough money to buy signs
          if (plugin.isVaultAvailable().second!!.getBalance(player) < signPrice) {
            plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_NOT_ENOUGH_MONEY)
            return
          }

          // Check if the inventory of the player isn't full
          if (player.inventory.firstEmpty() == -1) {
            plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_NOT_ENOUGH_SPACE)
            return
          }

          // Withdraw the costs for the signs
          plugin.isVaultAvailable().second!!.withdrawPlayer(player, signPrice)

          // Copy the colored sign stack and set the defined amount
          val stackForPlayer = ItemStack(plugin.coloredSignManager.coloredSignStack)
          stackForPlayer.amount = signAmount

          // Add the signs to the player's inventory and update it
          player.inventory.addItem(stackForPlayer)
          // player.updateInventory()

          // Play the appropriate sound
          SoundUtil.playPlayerSound(plugin, "buySign.receivingSound", player)

          val successMessage =
            plugin
              .getTranslation()
              ?.t(
                TranslationConfigKey.BUYSIGN_SIGN_AMOUNT_RECEIVED,
                signPrice,
                signAmount,
              )
          plugin.sendLogoMessage(player, successMessage!!)
        } else {
          plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_PERMISSION_MISSING)
        }
      }
    }
  }
}
