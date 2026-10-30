// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.util

import de.codehat.signcolors.SignColors
import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.Locale

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

      val soundName = plugin.pluginConfig.getConfigurationData()?.getString("$configPath.type")
      val soundKey = soundName?.lowercase(Locale.ROOT)?.let(NamespacedKey::fromString)
      val sound =
        soundKey?.let(Registry.SOUNDS::get) ?: soundName?.let { name ->
          try {
            Sound::class.java.getField(name).get(null) as? Sound
          } catch (_: ReflectiveOperationException) {
            null
          }
        }
      if (sound == null) {
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
