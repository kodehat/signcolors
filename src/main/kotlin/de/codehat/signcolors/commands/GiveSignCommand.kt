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

	companion object {
	    private const val MIN_ARGS = 1
	    private const val MAX_ARGS = 3
	    private const val MIN_SIGN_AMOUNT = 1
	    private const val MAX_SIGN_AMOUNT = 16
	}

    override fun onCommand(sender: CommandSender,
						   command: org.bukkit.command.Command,
						   label: String,
						   args: Array<out String>) {
        if (!sender.hasPermission(Permissions.CMD_GIVE_SIGN)) {
            sender.sendLogoMsg(LanguageKey.NO_PERMISSION)
            return
        }

        if (args.size == MIN_ARGS || args.size < MAX_ARGS) {
            sender.sendLogoMsg("&6/sc &egivesign &c[&e${SignColors.languageConfig
					.get(LanguageKey.PARAMETER_PLAYER)}&c] &c[&e${SignColors.languageConfig
					.get(LanguageKey.PARAMETER_AMOUNT)}&c]")
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
        if (signAmountString.toIntOrNull() == null || signAmountString.toInt() < MIN_SIGN_AMOUNT
				|| signAmountString.toInt() > MAX_SIGN_AMOUNT) {
            sender.sendLogoMsg(LanguageKey.GIVE_SIGN_INVALID_AMOUNT)
            return
        }

        val signAmount = signAmountString.toInt()
        val playerStack = SignColors.instance.coloredSignManager.coloredSignStack
        playerStack.amount = signAmount
        player.inventory.addItem(playerStack)

        val donationSourceSuccessMessage = SignColors.languageConfig.get(LanguageKey.GIVE_SIGN_DONATOR_SUCCESS)
        sender.sendLogoMsg(String.format(donationSourceSuccessMessage!!, signAmount, player.name))

        val donationTargetSuccessMessage = SignColors.languageConfig.get(LanguageKey.GIVE_SIGN_TARGET_SUCCESS)
        player.sendLogoMsg(String.format(donationTargetSuccessMessage!!, signAmount, sender.name))
    }
}
