package de.codehat.signcolors.command

import de.codehat.signcolors.permission.Permissions
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
            if (sender.hasPermission(Permissions.CMD_HELP.value())) commands.add("help")
            if (sender.hasPermission("signcolors.reload")) commands.add("reload")
            if (sender.hasPermission("signcolors.givesign")) commands.add("givesign")
            if (sender.hasPermission(Permissions.CMD_COLORCODES.value())) commands.add("colorcodes")
            if (sender.hasPermission("signcolors.upgrade")) commands.add("upgrade")

            StringUtil.copyPartialMatches(args[0], commands, completions)
        } else if (args.size == 2 && !args[1].isEmpty() && args[0].equals("givesign", true)) {
            for (player in Bukkit.getServer().onlinePlayers) {
                if (player.name.startsWith(args[1])) completions.add(player.name)
            }
        }
        if (sorting) completions.sort()
        return completions
    }
}