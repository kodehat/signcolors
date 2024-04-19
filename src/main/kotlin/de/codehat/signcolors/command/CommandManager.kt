/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2022 CodeHat
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.codehat.signcolors.command

import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandManager : CommandExecutor {

    private val commands: MutableMap<String, Command> = mutableMapOf()

    companion object {
        const val CMD_PREFIX = "sc"
        const val CMD_INFO = ""
        const val CMD_HELP = "help"
        const val CMD_RELOAD = "reload"
        const val CMD_COLOR_CODES = "colorcodes"
        const val CMD_GIVE_SIGN = "givesign"
        const val CMD_MIGRATE_DATABASE = "migratedb"
    }

    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        when {
            args.isEmpty() -> commands[""]?.onCommand(sender, command, label, args)
            commands.containsKey(args[0]) ->
                commands[args[0]]?.onCommand(sender, command, label, args)
            else -> sender.sendLogoMsg(LanguageKey.UNKNOWN_CMD)
        }
        return true
    }

    fun registerCommand(command: String, commandObject: Command): Boolean {
        return commands.put(command, commandObject) == null
    }
}
