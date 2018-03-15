package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.abstraction.Command
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.command.CommandSender

class HelpCommand: Command() {

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>) {
        if (!sender.hasPermission(Permissions.CMD_HELP.value())) {
            sender.sendLogoMsg("Not allowed")
            return
        }

        sender.sendLogoMsg("""
            |${SignColors.languageConfig.get(LanguageKey.TAG)}
            | &c[] &erequired, &7<> &eoptional
            | &7&l- &r&e/ctb &6join &c[name]
            | &7&l- &r&e/ctb &6leave &c[name]
            | &7&l- &r&e/ctb &6ready
            | &7&l- &r&e/ctb &6list
            """.trimMargin())
    }

}