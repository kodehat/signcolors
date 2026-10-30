// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.managers

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.manager.Manager
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.path.name

class BackupOldFilesManager(
  plugin: SignColors,
) : Manager(plugin) {
  companion object {
    const val CONFIG_VERSION = 7
    private const val BUFFER_SIZE = 1024

    private fun addToZipFile(
      file: File,
      zos: ZipOutputStream,
    ) {
      val bis = BufferedInputStream(FileInputStream(file))
      val zipEntry = ZipEntry(file.name)

      zos.putNextEntry(zipEntry)

      bis.copyTo(zos, BUFFER_SIZE)

      zos.closeEntry()
      bis.close()
    }
  }

  init {
    start()
  }

  override fun start() {
    when {
      plugin.pluginConfig.getConfigVersion() == 0 -> {
        plugin.logger.info(
          "No config version code found! Making a backup of all old files now.",
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
    val dataFolder = plugin.dataFolder
    val backupZipName = "backup-${System.currentTimeMillis()}.zip"
    val fos = FileOutputStream(File(dataFolder, backupZipName))
    val zos = ZipOutputStream(fos)

    // Add all .yml files to the .zip file.
    dataFolder
      .walkTopDown()
      .filter { file -> file.name.matches(".*\\.(ya?ml|properties)$".toRegex()) }
      .forEach {
        plugin.logger.info("Backing up '${it.name}'")
        addToZipFile(it, zos)
      }

    zos.close()
    fos.close()

    // Now delete all old files.
    dataFolder
      .walkTopDown()
      .filter { file -> file.name.matches(".*\\.(ya?ml|properties)$".toRegex()) }
      .forEach {
        plugin.logger.info("Deleting old '${it.name}'")
        it.delete()
      }

    plugin.logger.info("Created backup file '$backupZipName' in the plugin's data folder!")
  }
}
