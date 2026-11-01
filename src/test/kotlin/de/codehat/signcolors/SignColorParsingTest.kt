// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import de.codehat.signcolors.listener.SignListener
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SignColorParsingTest {
  private fun color(
    line: String,
    permitted: Boolean = true,
  ): String = SignListener.colorSignLine(line, "&") { permitted }

  @Test
  fun preservesRepeatedTextAndPrefixes() {
    assertEquals("hello&hello", color("hello&hello"))
    assertEquals("a§a", color("a&a"))
    assertEquals("&§aHi&", color("&&aHi&"))
    assertEquals("hello&&hello", color("hello&&hello"))
  }

  @Test
  fun blackRequiresPermission() {
    assertEquals(
      "&0Black",
      SignListener.colorSignLine("&0Black", "&") { code ->
        SignListener.hasColorPermission(code) { false }
      },
    )
    assertEquals(
      "§0Black",
      SignListener.colorSignLine("&0Black", "&") { code ->
        SignListener.hasColorPermission(code) { it == "signcolors.color.0" }
      },
    )
  }

  @Test
  fun colorAndFormattingPermissionsAreSeparate() {
    val codes = "0123456789abcdefklmnor"
    for ((index, code) in codes.withIndex()) {
      val isColor = index < 16
      assertEquals(isColor, SignListener.hasColorPermission(index) { it.startsWith("signcolors.color.") }, "$code color wildcard")
      assertEquals(
        !isColor,
        SignListener.hasColorPermission(index) { it.startsWith("signcolors.formatting.") },
        "$code formatting wildcard",
      )
    }
    assertEquals(
      "§aGreen &lBold",
      SignListener.colorSignLine("&aGreen &lBold", "&") { index ->
        SignListener.hasColorPermission(index) { it.startsWith("signcolors.color.") }
      },
    )
  }

  @Test
  fun reconstructsEditableLegacyCodes() {
    assertEquals("&aGreen &lBold", SignListener.editableSignLine("§aGreen §lBold", "&"))
    assertEquals("!AUpper !rReset", SignListener.editableSignLine("§AUpper §rReset", "!"))
    assertEquals("literal §z and &a", SignListener.editableSignLine("literal §z and &a", "&"))
  }

  @Test
  fun editingPreservesUnauthorizedCodesAsLiteralText() {
    val lines = arrayOf("§aGreen §lBold", "§cRed", "plain", "")
    assertEquals(true, SignListener.canEditFormatting(lines))
    val saved =
      lines.map { line ->
        val editable = SignListener.editableSignLine(line, "&")
        SignListener.colorSignLine(editable, "&") { index -> index == 10 }
      }
    assertEquals(listOf("§aGreen &lBold", "&cRed", "plain", ""), saved)
    assertEquals(false, SignListener.canEditFormatting(arrayOf("§x§a§b§c§d§e§fHex")))
  }

  @Test
  fun translatesOnlyPermittedCodes() {
    assertEquals("§aGreen §lBold", color("&AGreen &lBold"))
    assertEquals("&aGreen &lBold", color("&aGreen &lBold", permitted = false))
    assertEquals("&zUnknown", color("&zUnknown"))
  }
}
