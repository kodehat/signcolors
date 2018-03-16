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
            ${ if (sender.hasPermission(Permissions.CMD_INFO.value())) "| &7&l- &r&6/sc" else ""}
            ${ if (sender.hasPermission(Permissions.CMD_HELP.value())) "| &7&l- &r&6/sc &ehelp" else ""}
            ${ if (sender.hasPermission(Permissions.CMD_RELOAD.value())) "| &7&l- &r&6/sc &ereload" else ""}
            ${ if (sender.hasPermission(Permissions.CMD_GIVE_SIGN.value())) "| &7&l- &r&6/sc &egivesign &c[&eplayer&c] &c[&eamount&c]" else ""}
            ${ if (sender.hasPermission(Permissions.CMD_COLOR_CODES.value())) "| &7&l- &r&6/sc &ecolorcodes" else ""}
            """.trimMargin())
    }

}