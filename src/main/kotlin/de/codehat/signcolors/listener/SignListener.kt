package de.codehat.signcolors.listener

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.permission.Permissions
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

class SignListener: Listener {

    companion object {
        const val ALL_COLOR_CODES = "0123456789abcdefklmnor"

        private fun applyColorsOnSign(event: SignChangeEvent, player: Player) {
            val colorCharacter = SignColors.instance.config.getString("color_character")

            for (line in 0..4) {
                if (event.getLine(line).isEmpty() || !event.getLine(line).contains(colorCharacter)) continue

                val colorLine = event.getLine(line).split(colorCharacter)
                var newLine = ""
            }
        }

        private fun checkColorPermissions(player: Player, color: Int): Boolean {
            val colorChar = ALL_COLOR_CODES[color]
            return (color == 0) || player.hasPermission(Permissions.USE_SPECIFIC_COLOR.value() + colorChar)
                || player.hasPermission(Permissions.ALL.value()) || player.hasPermission(Permissions.USE_ALL_COLORS.value())
        }
    }

}