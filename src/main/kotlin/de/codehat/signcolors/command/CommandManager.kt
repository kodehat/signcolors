package de.codehat.signcolors.command

import de.codehat.signcolors.command.abstraction.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandManager: CommandExecutor {

    private val commands: MutableMap<String, Command> = mutableMapOf()

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>): Boolean {
        val firstArgument = args[0]
        when {
            args.isEmpty() -> commands[""]?.onCommand(sender, command, label, args)
            commands.containsKey(firstArgument) -> commands[firstArgument]?.onCommand(sender, command, label, args)
            else -> sender.sendMessage("Not found!")
        }
        return true
    }

    fun registerCommand(command: String, commandObject: Command): Boolean {
        return commands.put(command, commandObject) == null
    }
}