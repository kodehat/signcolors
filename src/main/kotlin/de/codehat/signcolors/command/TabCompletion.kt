package de.codehat.signcolors.command

import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil
import java.util.*

class TabCompletion: TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        val completions = ArrayList<String>()
        val sorting = true
        if (args.size == 1) {
            val commands = ArrayList<String>()
            if (sender.hasPermission(Permissions.CMD_HELP)) commands.add(CommandManager.CMD_HELP)
            if (sender.hasPermission(Permissions.CMD_RELOAD)) commands.add(CommandManager.CMD_RELOAD)
            if (sender.hasPermission(Permissions.CMD_COLOR_CODES)) commands.add(CommandManager.CMD_COLOR_CODES)
            if (sender.hasPermission(Permissions.CMD_GIVE_SIGN)) commands.add(CommandManager.CMD_GIVE_SIGN)
            if (sender.hasPermission(Permissions.CMD_MIGRATE_DATABASE)) commands.add(CommandManager.CMD_MIGRATE_DATABASE)

            StringUtil.copyPartialMatches(args[0], commands, completions)
        } else if (args.size == 2 && !args[1].isEmpty() && args[0].equals(CommandManager.CMD_GIVE_SIGN, true)) {
            for (player in Bukkit.getServer().onlinePlayers) {
                if (player.name.startsWith(args[1])) completions.add(player.name)
            }
        }
        if (sorting) completions.sort()
        return completions
    }
}