package de.codehat.signcolors

import de.codehat.pluginupdatechecker.UpdateChecker
import de.codehat.signcolors.command.CommandManager
import de.codehat.signcolors.command.TabCompletion
import de.codehat.signcolors.commands.HelpCommand
import de.codehat.signcolors.commands.ReloadCommand
import de.codehat.signcolors.commands.GiveSignCommand
import de.codehat.signcolors.commands.ColorcodesCommand
import de.codehat.signcolors.commands.InfoCommand
import de.codehat.signcolors.config.ConfigKey
import de.codehat.signcolors.configs.LanguageConfig
import de.codehat.signcolors.daos.SignLocationDao
import de.codehat.signcolors.database.MysqlDatabase
import de.codehat.signcolors.database.SqliteDatabase
import de.codehat.signcolors.database.abstraction.Database
import de.codehat.signcolors.dependencies.VaultDependency
import de.codehat.signcolors.listener.BlockListener
import de.codehat.signcolors.listener.PlayerListener
import de.codehat.signcolors.listener.SignListener
import de.codehat.signcolors.managers.BackupOldFilesManager
import de.codehat.signcolors.managers.ColoredSignManager
import io.sentry.Sentry
import net.milkbowl.vault.economy.Economy
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

@Suppress("unused")
class SignColors: JavaPlugin() {

    private val commandManager = CommandManager()

    internal val fixSignPlayers = mutableListOf<Player>()
    internal var updateAvailablePair: Pair<Boolean, String> = Pair(false, "")

    lateinit var database: Database
        private set
    lateinit var signLocationDao: SignLocationDao
        private set
    lateinit var coloredSignManager: ColoredSignManager

    companion object {
        private const val UPDATE_CHECKER_URL = "https://pluginapi.codehat.de/plugins"
		private const val UPDATE_CHECKER_PLUGIN_ID = "Syjzymgdz"
		private const val SENTRY_DSN = "https://e811618a183e43558b27567e9e79e8b2@sentry.io/1322662"

        internal lateinit var instance: SignColors
        internal lateinit var languageConfig: LanguageConfig
        internal var debug = false
            private set

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

        checkAndDoBackup()
        saveDefaultConfig()
        loadLanguage()
        loadDependencies()
        loadDatabase()
        signLocationDao = SignLocationDao(database.connectionSource)
        loadManagers()
        registerCommands()
        registerListener()
        startUpdateCheckerIfEnabled()

        logger.info("v${description.version} has been enabled.")
    }

    override fun onDisable() {
        database.close()

        logger.info("v${description.version} has been disabled.")
    }

    private fun checkAndDoBackup() {
        BackupOldFilesManager()
    }

    internal fun loadLanguage() {
        val language = config.getString(ConfigKey.LANGUAGE.toString())
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

    internal fun loadDatabase() {
        val databaseType = config.getString(ConfigKey.DATABASE_TYPE.toString())

        if (databaseType.equals("sqlite", true)) {
            val sqliteDatabasePath = dataFolder.absolutePath + File.separator + "sign_locations.db"

            database = SqliteDatabase(SqliteDatabase.createConnectionString(sqliteDatabasePath))

            logger.info("Using SQLite to save sign locations (path to DB is '$sqliteDatabasePath').")
        } else if (databaseType.equals("mysql", true)) {
            database = MysqlDatabase(MysqlDatabase.createConnectionString(
                    config.getString(ConfigKey.DATABASE_HOST.toString()),
                    config.getInt(ConfigKey.DATABASE_PORT.toString()),
                    config.getString(ConfigKey.DATABASE_NAME.toString()),
                    config.getString(ConfigKey.DATABASE_USER.toString()),
                    config.getString(ConfigKey.DATABASE_PASSWORD.toString())
            ))

            logger.info("Using MySQL to save sign locations.")
        }
    }

    private fun loadManagers() {
        coloredSignManager = ColoredSignManager()
    }

    private fun registerCommands() {
        with(commandManager) {
            registerCommand(CommandManager.CMD_INFO, InfoCommand())
            registerCommand(CommandManager.CMD_HELP, HelpCommand())
            registerCommand(CommandManager.CMD_GIVE_SIGN, GiveSignCommand())
            registerCommand(CommandManager.CMD_COLOR_CODES, ColorcodesCommand())
            registerCommand(CommandManager.CMD_RELOAD, ReloadCommand())
            //registerCommand(CommandManager.CMD_MIGRATE_DATABASE, MigrateDatabaseCommand())
        }

        with(getCommand(CommandManager.CMD_PREFIX)) {
            executor = commandManager
            tabCompleter = TabCompletion()
        }
    }

    private fun registerListener() {
        with(server.pluginManager) {
            registerEvents(BlockListener(), this@SignColors)
            registerEvents(SignListener(), this@SignColors)
            registerEvents(PlayerListener(), this@SignColors)
        }
    }

    private fun startUpdateCheckerIfEnabled() {
        if (config.getBoolean(ConfigKey.OTHER_UPDATE_CHECK.toString())) {
            logger.info("Checking for an update...")
            val updateChecker = UpdateChecker.Builder(this)
                    .setUrl(UPDATE_CHECKER_URL)
                    .setPluginId(UPDATE_CHECKER_PLUGIN_ID)
                    .setCurrentVersion(description.version)
                    .onNewVersion {
                        logger.info("New version (v$it) is available!")
                        updateAvailablePair = Pair(true, it)
                    }
                    .onLatestVersion {
                        logger.info("No new version available.")
                    }
                    .onError {
                        logger.warning("Unable to check for an update!")
                    }
                    .build()
            updateChecker.check()
        }
    }

	private fun enableErrorReporting() {
		if (config.getBoolean(ConfigKey.OTHER_ERROR_REPORTING.toString())) {
			Sentry.init(SENTRY_DSN)
			logger.info("Error reporting has been enabled.")
		}
	}

}
