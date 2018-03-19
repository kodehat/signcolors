package de.codehat.signcolors.commands

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.command.abstraction.Command
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import de.codehat.signcolors.util.sendLogoMsg
import org.bukkit.command.CommandSender

class ReloadCommand: Command() {

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<out String>) {
        if (!sender.hasPermission(Permissions.CMD_RELOAD)) {
            sender.sendLogoMsg(LanguageKey.NO_PERMISSION)
            return
        }

        with(SignColors) {
            instance.coloredSignManager.oldSignAmount = instance.config.getInt("sign_amount.crafting")
            instance.reloadConfig()
            languageConfig.reload() //TODO: Reload language config accordingly!
            instance.coloredSignManager.removeRecipe() //TODO: Remove recipe accordingly!
            instance.coloredSignManager.setup() //TODO: Reload colored sign recipe accordingly!
            //instance.loadDatabase() //TODO: Reload database accordingly!
        }

        sender.sendLogoMsg(LanguageKey.CONFIG_RELOAD)
    }
}