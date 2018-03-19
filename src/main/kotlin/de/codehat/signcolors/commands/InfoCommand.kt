package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.abstraction.Command
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import de.codehat.signcolors.util.sendColoredMsg
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.command.CommandSender

class InfoCommand: Command() {

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>) {
        if (!sender.hasPermission(Permissions.CMD_INFO)) {
            sender.sendLogoMsg(LanguageKey.NO_PERMISSION)
            return
        }

        with (sender) {
            sendColoredMsg("&6+--------------------&r${SignColors.languageConfig.get(LanguageKey.TAG)}&r&6--------------------+")
            sendColoredMsg(" ${SignColors.languageConfig.get(LanguageKey.INFO_AUTHOR)} &a${SignColors.instance.description.authors.joinToString(",")}")
            sendColoredMsg(" ${SignColors.languageConfig.get(LanguageKey.INFO_VERSION)} &a${SignColors.instance.description.version}")
            sendColoredMsg(" ${SignColors.languageConfig.get(LanguageKey.INFO_CMD_HELP)}")
            sendColoredMsg("&6+----------------------------------------------------+")
        }
    }
}