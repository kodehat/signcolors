package de.codehat.signcolors

import de.codehat.signcolors.command.CommandManager
import de.codehat.signcolors.command.TabCompletion
import de.codehat.signcolors.commands.ColorcodesCommand
import de.codehat.signcolors.commands.HelpCommand
import de.codehat.signcolors.commands.InfoCommand
import de.codehat.signcolors.configs.LanguageConfig
import de.codehat.signcolors.daos.SignLocationDao
import de.codehat.signcolors.database.MysqlDatabase
import de.codehat.signcolors.database.SqliteDatabase
import de.codehat.signcolors.database.abstraction.Database
import de.codehat.signcolors.dependencies.VaultDependency
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

@Suppress("unused")
class SignColors: JavaPlugin() {

    private val commandManager = CommandManager()

    lateinit var database: Database
        private set
    lateinit var signLocationDao: SignLocationDao
        private set

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
        loadDatabase()
        signLocationDao = SignLocationDao(database.connectionSource)
        signLocationDao.create("test", 10, -50, 265)
        registerCommands()
        registerListener()

        logger.info("v${description.version} has been enabled.")
    }

    override fun onDisable() {
        database.close()

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

    private fun loadDatabase() {
        val databaseType = config.getString("database.type")

        if (databaseType.equals("sqlite", true)) {
            val sqliteDatabasePath = dataFolder.absolutePath + File.separator + "sign_locations.db"

            database = SqliteDatabase(SqliteDatabase.createConnectionString(sqliteDatabasePath))

            logger.info("Using SQLite to save sign locations (path to DB is '$sqliteDatabasePath').")
        } else if (databaseType.equals("mysql", true)) {
            database = MysqlDatabase(MysqlDatabase.createConnectionString(
                    config.getString("database.host"),
                    config.getInt("database.port"),
                    config.getString("database.name"),
                    config.getString("database.user"),
                    config.getString("database.password")
            ))

            logger.info("Using MySQL to save sign locations.")
        }
    }

    private fun registerCommands() {
        with(commandManager) {
            registerCommand("", InfoCommand())
            registerCommand("help", HelpCommand())
            registerCommand("colorcodes", ColorcodesCommand())
        }

        with(getCommand("sc")) {
            executor = commandManager
            tabCompleter = TabCompletion()
        }
    }

    private fun registerListener() {

    }

}