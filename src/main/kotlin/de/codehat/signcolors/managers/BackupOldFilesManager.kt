package de.codehat.signcolors.managers

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.config.ConfigKey
import de.codehat.signcolors.manager.Manager
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class BackupOldFilesManager: Manager {

    companion object {
        const val CONFIG_VERSION = 6
        private const val BUFFER_SIZE = 1024

        private fun addToZipFile(file: File, zos: ZipOutputStream) {
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
            SignColors.instance.config.getInt(ConfigKey.OTHER_CONFIG_VERSION.toString()) == 0 -> {
                SignColors.instance.logger.info("No config version code found! Making a backup of all old files now.")
                setup()
            }
            CONFIG_VERSION > SignColors.instance.config.getInt(ConfigKey.OTHER_CONFIG_VERSION.toString()) -> {
                SignColors.instance.logger.info("Old config version found! Making a backup of the old config now.")
                setup()
            }
            else -> SignColors.instance.logger.info("Config is up to date.")
        }
    }

    override fun setup() {
        val dataFolder = SignColors.instance.dataFolder
        val backupZipName = "backup-${System.currentTimeMillis()}.zip"
        val fos = FileOutputStream(File(dataFolder, backupZipName))
        val zos = ZipOutputStream(fos)

        // Add all .yml files to the .zip file
        dataFolder.listFiles { _, name -> name.endsWith(".yml")}.forEach {
            SignColors.instance.logger.info("Backing up '${it.name}'")
            addToZipFile(it, zos)
        }

        zos.close()
        fos.close()

        // Now delete all old files
        dataFolder.listFiles { _, name -> name.endsWith(".yml")}.forEach {
            SignColors.instance.logger.info("Deleting old '${it.name}'")
            it.delete()
        }

        SignColors.instance.logger.info("Created backup file '$backupZipName' in the plugin's data folder!")
    }
}