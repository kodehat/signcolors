// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import de.codehat.signcolors.config.TranslationFileUpdater
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class TranslationFileUpdaterTest {
  @TempDir
  lateinit var dir: Path

  private fun update(
    file: Path,
    content: String,
  ): Path? = TranslationFileUpdater.update(file, content.byteInputStream())

  @Test
  fun backsUpAndReplacesUnversionedAndOldFiles() {
    val file = dir.resolve("en_US.properties")
    Files.writeString(file, "greeting=custom\n")
    val firstBackup = update(file, "translation.version=1\ngreeting=new\n")!!
    assertEquals("greeting=custom\n", Files.readString(firstBackup))
    assertEquals("translation.version=1\ngreeting=new\n", Files.readString(file))

    val secondBackup = update(file, "translation.version=2\ngreeting=newer\n")!!
    assertNotEquals(firstBackup, secondBackup)
    assertEquals("translation.version=1\ngreeting=new\n", Files.readString(secondBackup))
    assertEquals("greeting=custom\n", Files.readString(firstBackup))
    assertEquals("translation.version=2\ngreeting=newer\n", Files.readString(file))
  }

  @Test
  fun leavesCurrentNewerAndMissingFilesAlone() {
    val file = dir.resolve("de_DE.properties")
    assertNull(update(file, "translation.version=1\n"))
    assertEquals(false, Files.exists(file))
    Files.writeString(file, "translation.version=2\ngreeting=custom\n")
    assertNull(update(file, "translation.version=2\ngreeting=bundled\n"))
    assertNull(update(file, "translation.version=1\ngreeting=older\n"))
    assertEquals("translation.version=2\ngreeting=custom\n", Files.readString(file))
  }

  @Test
  fun invalidVersionsDoNotOverwriteFiles() {
    val file = dir.resolve("en_US.properties")
    Files.writeString(file, "translation.version=invalid\ngreeting=custom\n")
    assertThrows(IllegalArgumentException::class.java) { update(file, "translation.version=1\n") }
    assertEquals("translation.version=invalid\ngreeting=custom\n", Files.readString(file))
    assertThrows(IllegalArgumentException::class.java) { update(file, "greeting=missing-version\n") }
    assertEquals("translation.version=invalid\ngreeting=custom\n", Files.readString(file))
  }
}
