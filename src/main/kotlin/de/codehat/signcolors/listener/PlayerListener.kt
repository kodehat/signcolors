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
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.SoundUtil
import de.codehat.signcolors.util.color
import de.codehat.signcolors.util.hasPermission
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.ChatColor
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack

class PlayerListener : Listener {

    @Suppress("unused")
    @EventHandler
    fun onPlayerJoinShowUpdateMessage(event: PlayerJoinEvent) {
        val player = event.player

        if (
            SignColors.instance.updateAvailablePair.first &&
                player.hasPermission(Permissions.SHOW_UPDATE_MESSAGE)
        ) {
            val version = SignColors.instance.updateAvailablePair.second
            val playerMessage = SignColors.languageConfig.get(LanguageKey.NEW_VERSION_AVAILABLE)
            player.sendLogoMsg(String.format(playerMessage!!, version))
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onPlayerLeaveRemoveFromFixPlayers(event: PlayerQuitEvent) {
        val player = event.player

        SignColors.instance.fixSignPlayers.remove(player) // Return value is ignorable
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
                if (player.hasPermission(Permissions.SPECIAL_SIGN_USE)) {

                    // Check if Vault is installed
                    if (!SignColors.isVaultAvailable().first) {
                        player.sendLogoMsg(LanguageKey.VAULT_MISSING)
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
                        player.sendLogoMsg(LanguageKey.SPECIAL_SIGN_INCORRECT_DATA_FORMAT_PLAYER)
                        event.isCancelled = true
                        return
                    }

                    // Retrieve sign amount and price
                    val signAmount = signData[0].trim().toInt()
                    val signPrice = signData[1].trim().toDouble()

                    // Check if the player has enough money to buy signs
                    if (SignColors.isVaultAvailable().second!!.getBalance(player) < signPrice) {
                        player.sendLogoMsg(LanguageKey.NOT_ENOUGH_MONEY)
                        return
                    }

                    // Check if the inventory of the player isn't full
                    if (player.inventory.firstEmpty() == -1) {
                        player.sendLogoMsg(LanguageKey.NOT_ENOUGH_SPACE)
                        return
                    }

                    // Withdraw the costs for the signs
                    SignColors.isVaultAvailable().second!!.withdrawPlayer(player, signPrice)

                    // Copy the colored sign stack and set the defined amount
                    val stackForPlayer =
                        ItemStack(SignColors.instance.coloredSignManager.coloredSignStack)
                    stackForPlayer.amount = signAmount

                    // Add the signs to the player's inventory and update it
                    player.inventory.addItem(stackForPlayer)
                    player.updateInventory()

                    // Play the appropriate sound
                    SoundUtil.playPlayerSound("sounds.receive_signs_from_special_sign", player)

                    val successMessage =
                        String.format(
                            SignColors.languageConfig.get(
                                LanguageKey.SPECIAL_SIGN_RECEIVED_SIGNS
                            )!!,
                            signPrice,
                            signAmount
                        )
                    player.sendLogoMsg(successMessage)
                } else {
                    player.sendLogoMsg(LanguageKey.NO_PERMISSION)
                }
            }
        }
    }
}
