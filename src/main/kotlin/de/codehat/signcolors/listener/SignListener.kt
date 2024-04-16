package de.codehat.signcolors.listener

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.config.ConfigKey
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.SoundUtil
import de.codehat.signcolors.util.color
import de.codehat.signcolors.util.hasPermission
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

class SignListener: Listener {

    companion object {
        private const val ALL_COLOR_CODES = "0123456789abcdefklmnor"
        private const val SPECIAL_SIGN_LINE = "[SignColors]"
		const val SPECIAL_SIGN_INDICATOR = "&6[&3SC&6]"
		private const val SIGN_LINE_FIRST_INDEX = 0
		private const val SIGN_LINE_SECOND_INDEX = 1
		private const val SIGN_LINE_THIRD_INDEX = 2
		private const val SIGN_LINE_LAST_INDEX = 3

        private fun applyColorsOnSign(event: SignChangeEvent, player: Player) {
            val colorCharacter = SignColors.instance.config.getString(ConfigKey.COLOR_CHARACTER.toString())

            for (line in SIGN_LINE_FIRST_INDEX..SIGN_LINE_LAST_INDEX) {
                if (event.getLine(line)!!.isEmpty() || !event.getLine(line)!!.contains(colorCharacter!!)) continue

                val colorLine = event.getLine(line)!!.split(colorCharacter)
                var newLine = ""
                if (colorLine.isNotEmpty()) newLine = colorLine[0]
                colorLine.forEach {
                    if (it == colorLine[0]) return@forEach
                    if (it.isEmpty() || ALL_COLOR_CODES.indexOf(it.toLowerCase()[0]) == -1) {
                        newLine += colorCharacter + it
                        return@forEach
                    }
                    val color = ALL_COLOR_CODES.indexOf(it.toLowerCase()[0])
                    if (color == -1 || !checkColorPermissions(player, color)) {
                        newLine += colorCharacter + it
                    } else {
                        val newPartialLine = colorCharacter + it
                        newLine += ChatColor.translateAlternateColorCodes(colorCharacter[0], newPartialLine)
                    }
                }
                event.setLine(line, newLine)
            }
        }

        private fun checkColorPermissions(player: Player, color: Int): Boolean {
            val colorChar = ALL_COLOR_CODES[color]
            return (color == 0) || player.hasPermission(Permissions.USE_SPECIFIC_COLOR.toString() + colorChar)
                    || player.hasPermission(Permissions.USE_SPECIFIC_FORMAT.toString() + colorChar)
        }

        private fun applyLinesOnSpecialSign(event: SignChangeEvent, amount: Int, price: Double) {
            with(event) {
                setLine(SIGN_LINE_FIRST_INDEX, SPECIAL_SIGN_INDICATOR.color())
                setLine(SIGN_LINE_SECOND_INDEX, SignColors.languageConfig
						.get(LanguageKey.SPECIAL_SIGN_LINE_TWO)!!.color())
                setLine(SIGN_LINE_THIRD_INDEX, "&8$amount : $price".color())
                setLine(SIGN_LINE_LAST_INDEX, SignColors.languageConfig.get(LanguageKey.SPECIAL_SIGN_LINE_FOUR)!!.color())
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onApplyColorOnSign(event: SignChangeEvent) {
        val player = event.player
        val block = event.block
        val itemInMainHand = player.inventory.itemInMainHand

        if (SignColors.instance.coloredSignManager.signCrafting) {
            if (!player.hasPermission(Permissions.BYPASS_SIGN_CRAFTING)) {
                if (itemInMainHand.type == Material.AIR) {
                    if (SignColors.instance.fixSignPlayers.contains(player)) {
                        SignColors.instance.fixSignPlayers.remove(player)
                        applyColorsOnSign(event, player)
                        SignColors.instance.signLocationDao.create(block)
                    }
                } else {
                    if (itemInMainHand.itemMeta!!.hasLore()) {
						// Not checking if present cause not relevant yet
                        SignColors.instance.fixSignPlayers.remove(player)
                        applyColorsOnSign(event, player)
                        SignColors.instance.signLocationDao.create(block)
                    }
                }
            } else {
                applyColorsOnSign(event, player)
            }
        } else {
            applyColorsOnSign(event, player)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onSpecialSignCreate(event: SignChangeEvent) {
        val player = event.player

        if (event.getLine(0).equals(SPECIAL_SIGN_LINE, true)) {
            if (player.hasPermission(Permissions.SPECIAL_SIGN_CREATE)) {
                val dataLine = event.getLine(1)
                // Check if line 1 isn't empty and contains the sign amount and the price split with ":"
                if (dataLine!!.isNotEmpty() && dataLine.contains(":")) {
                    // [0] = sign amount, [1] = sign price for the specified amount
                    val signData = dataLine.split(":")

                    // Check if written types are valid for Int and Double
                    if (signData[0].toIntOrNull() == null || signData[1].toDoubleOrNull() == null) {
                        player.sendLogoMsg(LanguageKey.SPECIAL_SIGN_INCORRECT_DATA_FORMAT)
                        event.isCancelled = true
                        return
                    }

                    // Retrieve sign amount and price
                    val signAmount = signData[0].toInt()
                    val signPrice = signData[1].toDouble()

                    // Return and show warning if sign amount or price is lower than zero or equal zero
                    if (signAmount <= 0 || signPrice <= 0) {
                        player.sendLogoMsg(LanguageKey.SPECIAL_SIGN_AMOUNT_OR_PRICE_TOO_LOW)
                        event.isCancelled = true
                        return
                    }

                    // Apply lines on special sign
                    applyLinesOnSpecialSign(event, signAmount, signPrice)
                } else { // Apply lines on special sign with default values
                    with(SignColors.instance.config) {
                        applyLinesOnSpecialSign(event, getInt(ConfigKey.SIGN_AMOUNT_SPECIAL_SIGN.toString()),
                                getDouble(ConfigKey.PRICE.toString()))
                    }
                }

                // Play the appropriate sound
                SoundUtil.playPlayerSound("sounds.create_special_sign", player)
            }
        }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun checkFirstLineOnSpecialSignCreation(event: SignChangeEvent) {
        val player = event.player
        val firstLine = event.getLine(0)!!.trim().replace(ChatColor.COLOR_CHAR.toString(), "&", true)

        // If player has permission to create a special sign, let him create it
        if (firstLine == SPECIAL_SIGN_INDICATOR && player.hasPermission(Permissions.SPECIAL_SIGN_CREATE.toString())) {
			return
		}

        // Contains all blocked first lines defined in config
        val blockedLines = SignColors.instance.config.getList("blocked_first_lines")!!.filterIsInstance<String>()

        // Check if the first line is a blocked line
        if (blockedLines.contains(firstLine)
				&& !player.hasPermission(Permissions.BYPASS_BLOCKED_FIRST_LINES.toString())) {
            // Line is blocked, let's cancel the event
            player.sendLogoMsg(LanguageKey.BLOCKED_FIRST_LINE_WRITTEN)
            event.isCancelled = true
        }
    }
}
