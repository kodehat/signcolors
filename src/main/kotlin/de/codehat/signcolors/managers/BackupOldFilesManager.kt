// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.managers

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.manager.Manager
import java.io.File
import java.io.InputStream
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class BackupOldFilesManager(
  plugin: SignColors,
) : Manager(plugin) {
  companion object {
    const val CONFIG_VERSION = 8

    internal fun backupConfig(
      dataFolder: File,
      installedVersion: Int,
      bundled: InputStream,
    ): File {
      val bundledBytes = bundled.use { it.readBytes() }
      val directory = dataFolder.toPath()
      val config = directory.resolve("config.yml")
      val backup = Files.createTempFile(directory, "config.yml.v$installedVersion-", ".bak")
      try {
        Files.copy(config, backup, StandardCopyOption.REPLACE_EXISTING)
      } catch (e: Exception) {
        Files.deleteIfExists(backup)
        throw e
      }

      val replacement = Files.createTempFile(directory, ".config-", ".tmp")
      try {
        Files.write(replacement, bundledBytes)
        try {
          Files.move(replacement, config, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
        } catch (_: AtomicMoveNotSupportedException) {
          Files.move(replacement, config, StandardCopyOption.REPLACE_EXISTING)
        }
      } finally {
        Files.deleteIfExists(replacement)
      }
      return backup.toFile()
    }
  }

  init {
    start()
  }

  override fun start() {
    when {
      plugin.pluginConfig.getConfigVersion() == 0 -> {
        plugin.logger.info(
          "No config version code found! Making a backup of the old config now.",
        )
        doBackup()
      }

      CONFIG_VERSION > plugin.pluginConfig.getConfigVersion() as Int -> {
        plugin.logger.info(
          "Old config version found! Making a backup of the old config now.",
        )
        doBackup()
      }

      else -> {
        plugin.logger.info("Config is up to date.")
      }
    }
  }

  private fun doBackup() {
    val bundled = requireNotNull(plugin.getResource("config.yml")) { "Missing bundled config.yml" }
    val backup = backupConfig(plugin.dataFolder, plugin.pluginConfig.getConfigVersion() ?: 0, bundled)
    plugin.logger.info("Created config backup '${backup.name}' in the plugin's data folder!")
  }
}
