// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import de.codehat.signcolors.command.CommandManager
import de.codehat.signcolors.command.TabCompletion
import de.codehat.signcolors.commands.CodesCommand
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
import de.codehat.signcolors.util.ColoredSignMarker
import net.milkbowl.vault.economy.Economy
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files

@Suppress("unused")
class SignColors : JavaPlugin() {
  lateinit var pluginConfig: PluginConfig
    private set

  private val translationConfigs: MutableMap<String, TranslationConfig> = mutableMapOf()

  private var vaultDependency: VaultDependency? = null

  internal val coloredSignMarker = ColoredSignMarker(this)

  lateinit var coloredSignManager: ColoredSignManager

  private val commandManager = CommandManager(this)

  internal var updateAvailablePair: Pair<Boolean, String> = Pair(false, "")

  companion object {
    private const val UPDATE_CHECKER_RESOURCE_ID = "6135"
    private val PROVIDED_LANGUAGES = arrayOf("de_DE", "en_US")
  }

  override fun onEnable() {
    loadConfig()
    checkAndDoBackup()
    pluginConfig.reload() // Must reload if backup has been made.
    loadTranslations()
    loadDependencies()
    loadManagers()
    registerCommands()
    registerListener()
    startUpdateCheckerIfEnabled()

    logger.info("v${description.version} has been enabled.")
  }

  override fun onDisable() {
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
    Files
      .list(dataFolder.toPath().resolve("translations"))
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

  internal fun getTranslation(): TranslationConfig? = translationConfigs[pluginConfig.getLanguage()]

  private fun loadDependencies() {
    logger.info("Looking for available dependencies...")

    if (!server.pluginManager.isPluginEnabled("Vault")) {
      logger.warning("Vault is missing or disabled. Economy features have been disabled.")
      return
    }

    try {
      vaultDependency = VaultDependency(this)
    } catch (e: NoClassDefFoundError) {
      logger.warning("Vault is enabled, but its economy API could not be loaded: ${e.message}")
      return
    }

    if (isVaultAvailable().first) {
      logger.info("Vault and an economy provider found and loaded.")
    } else {
      logger.warning("Vault is enabled, but no economy provider is registered. Economy features have been disabled.")
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

  private fun loadManagers() {
    coloredSignManager = ColoredSignManager(this)
  }

  private fun registerCommands() {
    with(commandManager) {
      registerCommand(CommandManager.CMD_INFO, InfoCommand(this@SignColors))
      registerCommand(CommandManager.CMD_HELP, HelpCommand(this@SignColors))
      registerCommand(CommandManager.CMD_GIVE_SIGN, GiveSignCommand(this@SignColors))
      registerCommand(CommandManager.CMD_CODES, CodesCommand(this@SignColors))
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
}
