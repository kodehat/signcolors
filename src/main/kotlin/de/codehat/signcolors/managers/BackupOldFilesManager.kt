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
package de.codehat.signcolors.managers

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.manager.Manager
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class BackupOldFilesManager(private val plugin: SignColors) : Manager {
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
    when {
      plugin.pluginConfig.getConfigVersion() == 0 -> {
        plugin.logger.info(
          "No config version code found! Making a backup of all old files now.",
        )
        setup()
      }
      CONFIG_VERSION > plugin.pluginConfig.getConfigVersion() as Int -> {
        plugin.logger.info(
          "Old config version found! Making a backup of the old config now.",
        )
        setup()
      }
      else -> plugin.logger.info("Config is up to date.")
    }
  }

  override fun setup() {
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
