package de.codehat.signcolors.command

import org.bukkit.command.CommandSender

abstract class Command {

    abstract fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>)
}