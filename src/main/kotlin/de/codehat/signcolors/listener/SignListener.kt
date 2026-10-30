// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
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
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

class SignListener(
  private val plugin: SignColors,
) : Listener {
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

        event.setLine(
          line,
          colorSignLine(event.getLine(line)!!, colorCharacter) { color ->
            checkColorPermissions(player, color)
          },
        )
      }
    }

    internal fun colorSignLine(
      line: String,
      colorCharacter: String,
      hasPermission: (Int) -> Boolean,
    ): String {
      val parts = line.split(colorCharacter)
      val result = StringBuilder(parts.first())
      for (part in parts.drop(1)) {
        val color = part.firstOrNull()?.lowercaseChar()?.let(ALL_COLOR_CODES::indexOf) ?: -1
        val segment = colorCharacter + part
        if (color >= 0 && hasPermission(color)) {
          result.append(ChatColor.translateAlternateColorCodes(colorCharacter[0], segment))
        } else {
          result.append(segment)
        }
      }
      return result.toString()
    }

    private fun checkColorPermissions(
      player: Player,
      color: Int,
    ): Boolean = hasColorPermission(color) { player.hasPermission(it) }

    internal fun hasColorPermission(
      color: Int,
      hasPermission: (String) -> Boolean,
    ): Boolean {
      val colorChar = ALL_COLOR_CODES[color]
      return hasPermission(Permissions.color(colorChar)) || hasPermission(Permissions.formatting(colorChar))
    }

    internal fun parseBuySignAmountPrice(
      line: String,
      defaultAmount: Int?,
      defaultPrice: Double?,
    ): Pair<Int, Double>? {
      val parts = if (line.isBlank()) null else line.split(":")
      if (parts != null && parts.size != 2) return null
      val amount = if (parts == null) defaultAmount else parts[0].trim().toIntOrNull()
      val price = if (parts == null) defaultPrice else parts[1].trim().toDoubleOrNull()
      if (amount == null || amount <= 0 || price == null || !price.isFinite() || price <= 0) return null
      return amount to price
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
  @EventHandler(ignoreCancelled = true)
  fun onApplyColorOnSign(event: SignChangeEvent) {
    val player = event.player
    val sign = event.block.state as? Sign
    if (
      !plugin.coloredSignManager.craftingEnabled ||
      player.hasPermission(Permissions.BYPASS_CRAFTING) ||
      (sign != null && plugin.coloredSignMarker.isMarked(sign))
    ) {
      applyColorsOnSign(plugin.pluginConfig.getPrefixCharacter(), event, player)
    }
  }

  @Suppress("unused")
  @EventHandler(ignoreCancelled = true)
  fun onSpecialSignCreate(event: SignChangeEvent) {
    val player = event.player

    if (event.getLine(0).equals(SPECIAL_SIGN_LINE, true) && player.hasPermission(Permissions.BUY_SIGN_CREATE)) {
      val amountPrice =
        parseBuySignAmountPrice(
          event.getLine(1).orEmpty(),
          plugin.pluginConfig.getBuySignAmount(),
          plugin.pluginConfig.getBuySignPrice(),
        )
      if (amountPrice == null) {
        plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_BUYSIGN_FORMAT_INCORRECT)
        event.isCancelled = true
        return
      }
      val (signAmount, signPrice) = amountPrice
      val typeName =
        event.getLine(2).orEmpty().trim().ifEmpty {
          plugin.pluginConfig.getBuySignSignType().orEmpty()
        }
      val signType = Material.getMaterial(typeName)
      if (signType == null || signType !in plugin.coloredSignManager.coloredSignStacks) {
        plugin.sendLogoMessage(player, TranslationConfigKey.ERROR_BUYSIGN_INVALID_SIGN_TYPE)
        event.isCancelled = true
        return
      }

      // Apply lines on special sign
      applyLinesOnSpecialSign(plugin, event, signAmount, signPrice, signType)

      // Wait until all listeners have had a chance to cancel the sign change.
      plugin.server.scheduler.runTask(
        plugin,
        Runnable {
          if (event.isCancelled) return@Runnable
          val sign = event.block.state as? Sign ?: return@Runnable
          if (sign.getSide(event.side).getLine(0) != SPECIAL_SIGN_INDICATOR.color()) return@Runnable
          sign.isWaxed = true
          if (sign.update()) SoundUtil.playPlayerSound(plugin, "buySign.creationSound", player)
        },
      )
    }
  }

  @Suppress("unused")
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
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
