package de.codehat.signcolors

import de.codehat.signcolors.command.CommandManager
import de.codehat.signcolors.commands.HelpCommand
import de.codehat.signcolors.configs.LanguageConfig
import de.codehat.signcolors.dependencies.VaultDependency
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class SignColors: JavaPlugin() {

    private val commandManager = CommandManager()

    companion object {
        internal lateinit var instance: SignColors
        internal lateinit var languageConfig: LanguageConfig

        private var vaultDependency: VaultDependency? = null

        fun isVaultAvailable(): Pair<Boolean, Economy?> {
            with (vaultDependency) {
                return if (this != null && this.economy != null) {
                    Pair(true, this.economy)
                } else {
                    Pair(false, null)
                }
            }
        }
    }

    override fun onEnable() {
        instance = this

        saveDefaultConfig()
        loadLanguage()
        loadDependencies()
        registerCommands()

        logger.info("v${description.version} has been enabled.")
    }

    override fun onDisable() {

        logger.info("v${description.version} has been disabled.")
    }

    private fun loadLanguage() {
        val language = config.getString("language")
        languageConfig = LanguageConfig(language)

        logger.info("Loaded language '$language'.")
    }

    private fun loadDependencies() {
        logger.info("Looking for available dependencies...")

        // Find and load Vault
        try {
            vaultDependency = VaultDependency(this)
        } catch (e: NoClassDefFoundError) {
            // Drop error silently
        }

        if (isVaultAvailable().first) {
            logger.info("Vault found and loaded.")
        } else {
            logger.warning("Vault is missing! Economy features have been disabled.")
        }
    }

    private fun registerCommands() {
        with(commandManager) {
            registerCommand("help", HelpCommand())
        }

        this.getCommand("sc").executor = commandManager
    }

}