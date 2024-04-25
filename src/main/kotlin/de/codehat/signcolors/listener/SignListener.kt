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
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

class SignListener(private val plugin: SignColors) : Listener {
  companion object {
    private const val ALL_COLOR_CODES = "0123456789abcdefklmnor"
    private const val SPECIAL_SIGN_LINE = "[SignColors]"
    const val SPECIAL_SIGN_INDICATOR = "&6[&3SC&6]"
    private const val SIGN_LINE_FIRST_INDEX = 0
    private const val SIGN_LINE_SECOND_INDEX = 1
    private const val SIGN_LINE_THIRD_INDEX = 2
    private const val SIGN_LINE_LAST_INDEX = 3

    private fun applyColorsOnSign(
      colorCharacter: String,
      event: SignChangeEvent,
      player: Player,
    ) {
      for (line in SIGN_LINE_FIRST_INDEX..SIGN_LINE_LAST_INDEX) {
        if (
          event.getLine(line)!!.isEmpty() ||
          !event.getLine(line)!!.contains(colorCharacter)
        ) {
          continue
        }

        val colorLine = event.getLine(line)!!.split(colorCharacter)
        var newLine = ""
        if (colorLine.isNotEmpty()) newLine = colorLine[0]
        colorLine.forEach {
          if (it == colorLine[0]) return@forEach
          if (it.isEmpty() || ALL_COLOR_CODES.indexOf(it.lowercase()[0]) == -1) {
            newLine += colorCharacter + it
            return@forEach
          }
          val color = ALL_COLOR_CODES.indexOf(it.lowercase()[0])
          if (color == -1 || !checkColorPermissions(player, color)) {
            newLine += colorCharacter + it
          } else {
            val newPartialLine = colorCharacter + it
            newLine +=
              ChatColor.translateAlternateColorCodes(
                colorCharacter[0],
                newPartialLine,
              )
          }
        }
        event.setLine(line, newLine)
      }
    }

    private fun checkColorPermissions(
      player: Player,
      color: Int,
    ): Boolean {
      val colorChar = ALL_COLOR_CODES[color]
      return (color == 0) ||
        player.hasPermission(Permissions.color(colorChar)) ||
        player.hasPermission(Permissions.formatting(colorChar))
    }

    private fun applyLinesOnSpecialSign(
      plugin: SignColors,
      event: SignChangeEvent,
      amount: Int,
      price: Double,
      signType: Material,
    ) {
      with(event) {
        setLine(SIGN_LINE_FIRST_INDEX, SPECIAL_SIGN_INDICATOR.color())
        setLine(
          SIGN_LINE_SECOND_INDEX,
          signType.name,
        )
        setLine(SIGN_LINE_THIRD_INDEX, "&8$amount : $price".color())
        setLine(
          SIGN_LINE_LAST_INDEX,
          plugin.getTranslation()?.t(TranslationConfigKey.BUYSIGN_LINE_FOUR),
        )
      }
    }
  }

  @Suppress("unused")
  @EventHandler
  fun onApplyColorOnSign(event: SignChangeEvent) {
    val player = event.player
    val block = event.block
    val itemInMainHand = player.inventory.itemInMainHand

    if (plugin.coloredSignManager.craftingEnabled) {
      if (!player.hasPermission(Permissions.BYPASS_CRAFTING)) {
        if (itemInMainHand.type == Material.AIR) {
          if (plugin.fixSignPlayers.contains(player)) {
            plugin.fixSignPlayers.remove(player)
            applyColorsOnSign(plugin.pluginConfig.getPrefixCharacter(), event, player)
            plugin.modelManager.signLocationDao.create(block)
          }
        } else {
          if (itemInMainHand.itemMeta!!.hasLore()) {
            // Not checking if present cause not relevant yet
            plugin.fixSignPlayers.remove(player)
            applyColorsOnSign(plugin.pluginConfig.getPrefixCharacter(), event, player)
            plugin.modelManager.signLocationDao.create(block)
          }
        }
      } else {
        applyColorsOnSign(plugin.pluginConfig.getPrefixCharacter(), event, player)
      }
    } else {
      applyColorsOnSign(plugin.pluginConfig.getPrefixCharacter(), event, player)
    }
  }

  @Suppress("unused")
  @EventHandler
  fun onSpecialSignCreate(event: SignChangeEvent) {
    val player = event.player

    if (event.getLine(0).equals(SPECIAL_SIGN_LINE, true)) {
      if (player.hasPermission(Permissions.BUY_SIGN_CREATE)) {
        val amountPriceLine = event.getLine(1)
        val typeLine = event.getLine(2)
        var signAmount = plugin.pluginConfig.getBuySignAmount()!!
        var signPrice = plugin.pluginConfig.getBuySignPrice()!!
        var signType = Material.valueOf(plugin.pluginConfig.getBuySignSignType()!!)
        // Check if line 1 isn't empty and contains the sign amount and the price split with
        // ":"
        if (amountPriceLine!!.isNotEmpty() && amountPriceLine.contains(":")) {
          // [0] = sign amount, [1] = sign price for the specified amount
          val signData = amountPriceLine.split(":")

          // Check if written types are valid for Int and Double
          if (signData[0].toIntOrNull() == null || signData[1].toDoubleOrNull() == null) {
            plugin.sendLogoMessage(
              player,
              TranslationConfigKey.ERROR_BUYSIGN_FORMAT_INCORRECT,
            )
            event.isCancelled = true
            return
          }

          // Retrieve sign amount and price
          signAmount = signData[0].toInt()
          signPrice = signData[1].toDouble()

          // Return and show warning if sign amount or price is lower than zero or equal zero.
          if (signAmount <= 0 || signPrice <= 0) {
            plugin.sendLogoMessage(
              player,
              TranslationConfigKey.ERROR_BUYSIGN_AMOUNT_OR_PRICE_TOO_LOW,
            )
            event.isCancelled = true
            return
          }
        }

        if (typeLine!!.isNotEmpty()) {
          try {
            signType = Material.valueOf(typeLine)
          } catch (e: IllegalArgumentException) {
            plugin.sendLogoMessage(
              player,
              TranslationConfigKey.ERROR_BUYSIGN_INVALID_SIGN_TYPE,
            )
            event.isCancelled = true
            return
          }
        }

        // Apply lines on special sign
        applyLinesOnSpecialSign(plugin, event, signAmount, signPrice, signType)

        // Play the appropriate sound
        SoundUtil.playPlayerSound(plugin, "buySign.creationSound", player)
      }
    }
  }

  @Suppress("unused")
  @EventHandler(priority = EventPriority.HIGH)
  fun checkFirstLineOnSpecialSignCreation(event: SignChangeEvent) {
    val player = event.player
    val firstLine =
      event.getLine(0)!!.trim().replace(ChatColor.COLOR_CHAR.toString(), "&", true)

    // If player has permission to create a special sign, let him create it
    if (
      firstLine == SPECIAL_SIGN_INDICATOR &&
      player.hasPermission(Permissions.BUY_SIGN_CREATE.toString())
    ) {
      return
    }

    // Contains all blocked first lines defined in config
    val blockedLines = plugin.pluginConfig.getBuySignBlockedFirstLines()!!

    // Check if the first line is a blocked line
    if (
      blockedLines.contains(firstLine) &&
      !player.hasPermission(Permissions.BYPASS_BLOCKED_FIRST_LINES.toString())
    ) {
      // Line is blocked, let's cancel the event
      plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_BUYSIGN_FIRST_LINE_BLOCKED)
      event.isCancelled = true
    }
  }
}
