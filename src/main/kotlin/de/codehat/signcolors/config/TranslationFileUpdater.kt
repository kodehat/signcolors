// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.config

import java.io.InputStream
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.Properties

/** Updates an existing bundled translation, returning the backup path if it changed. */
internal object TranslationFileUpdater {
  private const val VERSION_KEY = "translation.version"

  fun update(
    file: Path,
    bundled: InputStream,
  ): Path? {
    val bundledBytes = bundled.use { it.readBytes() }
    val bundledVersion = version(bundledBytes.inputStream(), requireVersion = true)
    if (!Files.exists(file)) return null // Normal resource extraction creates missing files.

    val installedVersion = Files.newInputStream(file).use { version(it, requireVersion = false) }
    if (installedVersion >= bundledVersion) return null

    val directory = file.parent
    val backup = Files.createTempFile(directory, "${file.fileName}.v$installedVersion-", ".bak")
    try {
      Files.copy(file, backup, StandardCopyOption.REPLACE_EXISTING)
    } catch (e: Exception) {
      Files.deleteIfExists(backup)
      throw e
    }

    val replacement = Files.createTempFile(directory, ".translation-", ".tmp")
    try {
      Files.write(replacement, bundledBytes)
      try {
        Files.move(replacement, file, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
      } catch (_: AtomicMoveNotSupportedException) {
        Files.move(replacement, file, StandardCopyOption.REPLACE_EXISTING)
      }
    } finally {
      Files.deleteIfExists(replacement)
    }
    return backup
  }

  private fun version(
    input: InputStream,
    requireVersion: Boolean,
  ): Int {
    val properties = Properties().apply { load(input) }
    val raw = properties.getProperty(VERSION_KEY)
    if (raw == null && !requireVersion) return 0
    val parsed = raw?.trim()?.toIntOrNull()
    require(parsed != null && parsed > 0) { "Invalid or missing $VERSION_KEY: $raw" }
    return parsed
  }
}
