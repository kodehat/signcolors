package de.codehat.signcolors.commands

import de.codehat.signcolors.command.Command
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.command.CommandSender

class MigrateDatabaseCommand: Command() {

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>) {
        if (!sender.hasPermission(Permissions.CMD_MIGRATE_DATABASE)) {
            sender.sendLogoMsg(LanguageKey.NO_PERMISSION)
            return
        }
    }
}