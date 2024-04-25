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
package de.codehat.signcolors

import com.j256.ormlite.logger.Level
import com.j256.ormlite.logger.Logger
import de.codehat.signcolors.command.CommandManager
import de.codehat.signcolors.command.TabCompletion
import de.codehat.signcolors.commands.ColorcodesCommand
import de.codehat.signcolors.commands.GiveSignCommand
import de.codehat.signcolors.commands.HelpCommand
import de.codehat.signcolors.commands.InfoCommand
import de.codehat.signcolors.commands.ReloadCommand
import de.codehat.signcolors.configs.PluginConfig
import de.codehat.signcolors.configs.TranslationConfig
import de.codehat.signcolors.configs.TranslationConfigKey
import de.codehat.signcolors.dependencies.VaultDependency
import de.codehat.signcolors.listener.BlockListener
import de.codehat.signcolors.listener.PlayerListener
import de.codehat.signcolors.listener.SignListener
import de.codehat.signcolors.managers.BackupOldFilesManager
import de.codehat.signcolors.managers.ColoredSignManager
import de.codehat.signcolors.managers.DatabaseManager
import de.codehat.signcolors.managers.ModelManager
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files

@Suppress("unused")
class SignColors : JavaPlugin() {
  lateinit var pluginConfig: PluginConfig
    private set

  private val translationConfigs: MutableMap<String, TranslationConfig> = mutableMapOf()

  private var vaultDependency: VaultDependency? = null

  internal lateinit var databaseManager: DatabaseManager

  internal lateinit var modelManager: ModelManager

  lateinit var coloredSignManager: ColoredSignManager

  private val commandManager = CommandManager(this)

  internal val fixSignPlayers = mutableListOf<Player>()
  internal var updateAvailablePair: Pair<Boolean, String> = Pair(false, "")

  companion object {
    private const val UPDATE_CHECKER_RESOURCE_ID = "6135"
    private val PROVIDED_LANGUAGES = arrayOf("de_DE", "en_US")
  }

  override fun onEnable() {
    Logger.setGlobalLogLevel(Level.WARNING)
    loadConfig()
    checkAndDoBackup()
    pluginConfig.reload() // Must reload if backup has been made.
    loadTranslations()
    loadDependencies()
    loadDatabase()
    loadManagers()
    registerCommands()
    registerListener()
    startUpdateCheckerIfEnabled()

    logger.info("v${description.version} has been enabled.")
  }

  override fun onDisable() {
    databaseManager.stop()
    modelManager.stop()
    coloredSignManager.stop()

    logger.info("v${description.version} has been disabled.")
  }

  private fun loadConfig() {
    pluginConfig = PluginConfig(this)
  }

  private fun checkAndDoBackup() {
    BackupOldFilesManager(this)
  }

  internal fun loadTranslations() {
    PROVIDED_LANGUAGES.forEach(this::loadTranslation)
    Files.list(dataFolder.toPath().resolve("translations"))
      .filter { it.fileName.toString().endsWith(".properties") }
      .map { it.fileName.toString().replace(".properties", "") }
      .filter { !PROVIDED_LANGUAGES.contains(it) }
      .forEach(this::loadTranslation)
  }

  private fun loadTranslation(language: String) {
    val translationConfig = TranslationConfig(this, language)
    translationConfigs[language] = translationConfig
    logger.info("Loaded translations for language '$language'.")
  }

  internal fun getTranslation(): TranslationConfig? {
    return translationConfigs[pluginConfig.getLanguage()]
  }

  private fun loadDependencies() {
    logger.info("Looking for available dependencies...")

    // Find and load Vault.
    try {
      vaultDependency = VaultDependency(this)
    } catch (e: NoClassDefFoundError) {
      // Drop error silently.
    }

    if (isVaultAvailable().first) {
      logger.info("Vault found and loaded.")
    } else {
      logger.warning("Vault is missing! Economy features have been disabled.")
    }
  }

  internal fun isVaultAvailable(): Pair<Boolean, Economy?> {
    with(vaultDependency) {
      return if (this != null && this.economy != null) {
        Pair(true, this.economy)
      } else {
        Pair(false, null)
      }
    }
  }

  private fun loadDatabase() {
    databaseManager = DatabaseManager(this)
  }

  private fun loadManagers() {
    coloredSignManager = ColoredSignManager(this)
    modelManager = ModelManager(this)
  }

  private fun registerCommands() {
    with(commandManager) {
      registerCommand(CommandManager.CMD_INFO, InfoCommand(this@SignColors))
      registerCommand(CommandManager.CMD_HELP, HelpCommand(this@SignColors))
      registerCommand(CommandManager.CMD_GIVE_SIGN, GiveSignCommand(this@SignColors))
      registerCommand(CommandManager.CMD_COLOR_CODES, ColorcodesCommand(this@SignColors))
      registerCommand(CommandManager.CMD_RELOAD, ReloadCommand(this@SignColors))
    }
    with(getCommand(CommandManager.CMD_PREFIX)) {
      this?.setExecutor(commandManager)
      this?.setTabCompleter(TabCompletion())
    }
  }

  private fun registerListener() {
    with(server.pluginManager) {
      registerEvents(BlockListener(this@SignColors), this@SignColors)
      registerEvents(SignListener(this@SignColors), this@SignColors)
      registerEvents(PlayerListener(this@SignColors), this@SignColors)
    }
  }

  private fun startUpdateCheckerIfEnabled() {
    if (pluginConfig.getUpdateCheck() == true) {
      logger.info("Checking for an update...")
      UpdateChecker(this, UPDATE_CHECKER_RESOURCE_ID).getVersion { version ->
        if (description.version == version) {
          logger.info("No new version available.")
        } else {
          logger.info("New version ($version) is available!")
          updateAvailablePair = Pair(true, version)
        }
      }
    }
  }

  internal fun sendColoredMessage(
    commandSender: CommandSender,
    message: String,
  ) {
    commandSender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', message))
  }

  internal fun sendLogoMessage(
    commandSender: CommandSender,
    message: String,
  ) {
    commandSender.sendMessage("${getTranslation()?.t(TranslationConfigKey.TAG)!!} $message")
  }

  internal fun sendLogoMessage(
    commandSender: CommandSender,
    translationConfigKey: TranslationConfigKey,
    vararg params: Any,
  ) {
    sendLogoMessage(commandSender, getTranslation()?.t(translationConfigKey, *params)!!)
  }

  // 	private fun enableErrorReporting() {
  // 		if (config.getBoolean(ConfigKey.OTHER_ERROR_REPORTING.toString())) {
  // 			Sentry.init(SENTRY_DSN)
  // 			logger.info("Error reporting has been enabled.")
  // 		}
  // 	}
}
