package de.codehat.signcolors.command

import de.codehat.signcolors.command.abstraction.Command
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandManager: CommandExecutor {

    private val commands: MutableMap<String, Command> = mutableMapOf()

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>): Boolean {
        when {
            args.isEmpty() -> commands[""]?.onCommand(sender, command, label, args)
            commands.containsKey(args[0]) -> commands[args[0]]?.onCommand(sender, command, label, args)
            else -> sender.sendLogoMsg(LanguageKey.UNKNOWN_CMD)
        }
        return true
    }

    fun registerCommand(command: String, commandObject: Command): Boolean {
        return commands.put(command, commandObject) == null
    }
}