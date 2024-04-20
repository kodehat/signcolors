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
    internal fun playPlayerSound(
      plugin: SignColors,
      configPath: String,
      player: Player,
    ) {
      if (!plugin.pluginConfig.getConfigurationData()?.getBoolean("$configPath.enabled")!!) {
        return
      }

      val sound: Sound?
      try {
        sound =
          Sound.valueOf(
            plugin.pluginConfig.getConfigurationData()?.getString("$configPath.type")!!,
          )
      } catch (e: IllegalArgumentException) {
        ErrorUtil.report(plugin, e)
        with(plugin.logger) {
          warning("The value for the sound at '$configPath.type' doesn't exist.")
          warning("Please set it to a valid type or no sound is played!")
        }
        return
      }

      player.playSound(
        player.location,
        sound,
        plugin.pluginConfig
          .getConfigurationData()
          ?.getDouble("$configPath.volume")
          ?.toFloat() ?: 1.0.toFloat(),
        plugin.pluginConfig
          .getConfigurationData()
          ?.getDouble("$configPath.pitch")
          ?.toFloat() ?: 1.0.toFloat(),
      )
    }
  }
}
