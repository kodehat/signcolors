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
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack

class PlayerListener(
  private val plugin: SignColors,
) : Listener {
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
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  fun onPlayerUseSpecialSign(event: PlayerInteractEvent) {
    if (event.action != Action.RIGHT_CLICK_BLOCK || event.clickedBlock?.state !is Sign) return
    plugin.server.scheduler.runTask(plugin, Runnable {
      if (event.useInteractedBlock() != Event.Result.DENY && event.player.isOnline) processSpecialSign(event)
    })
  }

  private fun processSpecialSign(event: PlayerInteractEvent) {
    val player = event.player

    if (event.clickedBlock!!.state is Sign) {
      val clickedSign = event.clickedBlock!!.state as Sign
      val indicatorLine = clickedSign.getTargetSide(player).getLine(0)
      val typeLine = clickedSign.getTargetSide(player).getLine(1)
      val amountPriceLine = clickedSign.getTargetSide(player).getLine(2)

      if (indicatorLine == SignListener.SPECIAL_SIGN_INDICATOR.color()) {
        if (player.hasPermission(Permissions.BUY_SIGN_USE)) {
          // Check if Vault is installed
          if (!plugin.isVaultAvailable().first) {
            plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_VAULT_MISSING)
            return
          }

          val amountPriceData = ChatColor.stripColor(amountPriceLine)!!.split(":")
          val signAmount = amountPriceData.getOrNull(0)?.trim()?.toIntOrNull()
          val signPrice = amountPriceData.getOrNull(1)?.trim()?.toDoubleOrNull()
          val signMaterial = Material.getMaterial(typeLine)
          val template = plugin.coloredSignManager.coloredSignStacks[signMaterial]
          if (
            amountPriceData.size != 2 || signAmount == null || signAmount <= 0 ||
            signPrice == null || !signPrice.isFinite() || signPrice <= 0 || template == null
          ) {
            plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_BUYSIGN_PLAYER_INCORRECT)
            return
          }

          val economy = plugin.isVaultAvailable().second!!
          if (economy.getBalance(player) < signPrice) {
            plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_NOT_ENOUGH_MONEY)
            return
          }

          val stackLimit = minOf(template.maxStackSize, player.inventory.maxStackSize)
          val capacity = player.inventory.storageContents.sumOf { stack ->
            when {
              stack == null || stack.type == Material.AIR -> stackLimit.toLong()
              stack.isSimilar(template) -> (stackLimit - stack.amount).coerceAtLeast(0).toLong()
              else -> 0L
            }
          }
          if (capacity < signAmount.toLong()) {
            plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_NOT_ENOUGH_SPACE)
            return
          }

          if (!economy.withdrawPlayer(player, signPrice).transactionSuccess()) {
            plugin.sendLogoMessage(player, "&cThe payment failed. Please contact an administrator.".color())
            return
          }

          val stackForPlayer = ItemStack(template).apply { amount = signAmount }
          player.inventory.addItem(stackForPlayer).values.forEach { leftover ->
            player.world.dropItemNaturally(player.location, leftover)
          }

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
