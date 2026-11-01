// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import de.codehat.signcolors.managers.BackupOldFilesManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class ConfigBackupTest {
  @TempDir
  lateinit var directory: Path

  @Test
  fun backsUpOnlyConfigAndPreservesOtherFiles() {
    val config = directory.resolve("config.yml")
    Files.writeString(config, "other:\n  configVersion: 7\n")
    val translations = Files.createDirectories(directory.resolve("translations"))
    val language = Files.writeString(translations.resolve("en_US.properties"), "hello=world")
    val legacy = Files.createDirectories(directory.resolve("languages"))
    val oldLanguage = Files.writeString(legacy.resolve("en_US.properties"), "old=value")
    val database = Files.writeString(Files.createDirectories(directory.resolve("data")).resolve("signs.db"), "database")

    val backup = BackupOldFilesManager.backupConfig(directory.toFile(), 7, "other:\n  configVersion: 8\n".byteInputStream())

    assertTrue(backup.name.startsWith("config.yml.v7-"))
    assertTrue(backup.name.endsWith(".bak"))
    assertEquals("other:\n  configVersion: 7\n", backup.readText())
    assertEquals("other:\n  configVersion: 8\n", Files.readString(config))
    assertEquals("hello=world", Files.readString(language))
    assertEquals("old=value", Files.readString(oldLanguage))
    assertEquals("database", Files.readString(database))
  }

  @Test
  fun repeatedBackupsDoNotOverwriteEarlierBackups() {
    val config = directory.resolve("config.yml")
    Files.writeString(config, "original")
    val first = BackupOldFilesManager.backupConfig(directory.toFile(), 7, "replacement".byteInputStream())
    val second = BackupOldFilesManager.backupConfig(directory.toFile(), 7, "latest".byteInputStream())
    assertTrue(first != second)
    assertEquals("original", first.readText())
    assertEquals("replacement", second.readText())
    assertEquals("latest", Files.readString(config))
  }

  @Test
  fun failedBackupDoesNotRemoveOtherFilesOrLeaveABackup() {
    val other = Files.writeString(directory.resolve("other.yml"), "keep")
    assertThrows(Exception::class.java) {
      BackupOldFilesManager.backupConfig(directory.toFile(), 0, "default".byteInputStream())
    }
    assertTrue(Files.exists(other))
    Files.list(directory).use { assertEquals(listOf("other.yml"), it.map { path -> path.fileName.toString() }.toList()) }
  }
}
