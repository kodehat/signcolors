package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.Command
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class GiveSignCommand: Command() {

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>) {
        if (!sender.hasPermission(Permissions.CMD_GIVE_SIGN)) {
            sender.sendLogoMsg(LanguageKey.NO_PERMISSION)
            return
        }

        if (args.size == 1 || args.size < 3) {
            sender.sendLogoMsg("&6/sc &egivesign &c[&e${SignColors.languageConfig.get(LanguageKey.PARAMETER_PLAYER)}&c] &c[&e${SignColors.languageConfig.get(LanguageKey.PARAMETER_AMOUNT)}&c]")
            return
        }

        val player = Bukkit.getPlayerExact(args[1])

        if (player == null) {
            sender.sendLogoMsg(LanguageKey.PLAYER_NOT_AVAILABLE)
            return
        }

        if (player.inventory.firstEmpty() == -1) {
            sender.sendLogoMsg(LanguageKey.NOT_ENOUGH_SPACE)
            return
        }

        val signAmountString = args[2]
        if (signAmountString.toIntOrNull() == null || signAmountString.toInt() < 1 || signAmountString.toInt() > 16) {
            sender.sendLogoMsg(LanguageKey.GIVE_SIGN_INVALID_AMOUNT)
            return
        }

        val signAmount = signAmountString.toInt()
        val playerStack = SignColors.instance.coloredSignManager.coloredSignStack
        playerStack.amount = signAmount
        player.inventory.addItem(playerStack)

        val donatorSuccessMessage = SignColors.languageConfig.get(LanguageKey.GIVE_SIGN_DONATOR_SUCCESS)
        sender.sendLogoMsg(String.format(donatorSuccessMessage, signAmount, player.name))

        val targetSuccessMessage = SignColors.languageConfig.get(LanguageKey.GIVE_SIGN_TARGET_SUCCESS)
        player.sendLogoMsg(String.format(targetSuccessMessage, signAmount, sender.name))
    }
}