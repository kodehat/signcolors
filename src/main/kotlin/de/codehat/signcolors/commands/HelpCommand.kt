package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.abstraction.Command
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.sendColoredMsg
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.command.CommandSender

class HelpCommand: Command() {

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>) {
        if (!sender.hasPermission(Permissions.CMD_HELP.value())) {
            sender.sendLogoMsg(LanguageKey.NO_PERMISSION)
            return
        }

        sender.sendColoredMsg("""
            |${SignColors.languageConfig.get(LanguageKey.TAG)} &aHelp page
            | &c[] &arequired, &7<> &aoptional
            | &7&l- &r&6/sc &ehelp
            | &7&l- &r&6/sc &ereload
            | &7&l- &r&6/sc &egivesign [player] [amount]
            | &7&l- &r&6/sc &ecolorcodes
            """.trimMargin())
    }

}