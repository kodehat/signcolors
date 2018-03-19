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
                sound = Sound.valueOf(config.getString("$configPath.type"))
            } catch (e: IllegalArgumentException) {
                with(SignColors.instance.logger) {
                    warning("The value for the sound at '$configPath.type' doesn't exist.")
                    warning("Please set it to a valid type or no sound is played!")
                }
                return
            }

            player.playSound(player.location,
                    sound,
                    config.getDouble("$configPath.volume").toFloat(),
                    config.getDouble("$configPath.pitch").toFloat()
            )
        }
    }
}