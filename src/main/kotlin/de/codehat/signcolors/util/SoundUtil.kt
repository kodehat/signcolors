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
package de.codehat.signcolors.util

import de.codehat.signcolors.SignColors
import org.bukkit.Sound
import org.bukkit.entity.Player

class SoundUtil {
    companion object {
        internal fun playPlayerSound(configPath: String, player: Player) {
            val config = SignColors.instance.config

            if (!config.getBoolean("$configPath.enabled")) return

            val sound: Sound?
            try {
                sound = Sound.valueOf(config.getString("$configPath.type")!!)
            } catch (e: IllegalArgumentException) {
                ErrorUtil.report(e)
                with(SignColors.instance.logger) {
                    warning("The value for the sound at '$configPath.type' doesn't exist.")
                    warning("Please set it to a valid type or no sound is played!")
                }
                return
            }

            player.playSound(
                player.location,
                sound,
                config.getDouble("$configPath.volume").toFloat(),
                config.getDouble("$configPath.pitch").toFloat()
            )
        }
    }
}
