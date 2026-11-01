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
  fun reconstructsEditableLegacyCodes() {
    assertEquals("&aGreen &lBold", SignListener.editableSignLine("§aGreen §lBold", "&"))
    assertEquals("!AUpper !rReset", SignListener.editableSignLine("§AUpper §rReset", "!"))
    assertEquals("literal §z and &a", SignListener.editableSignLine("literal §z and &a", "&"))
  }

  @Test
  fun editableFormattingRequiresAllPermissions() {
    val lines = arrayOf("§aGreen", "§lBold", "plain", "")
    assertEquals(true, SignListener.canEditFormatting(lines) { true })
    assertEquals(false, SignListener.canEditFormatting(lines) { it != 10 })
    assertEquals(false, SignListener.canEditFormatting(lines) { it != 17 })
    assertEquals(true, SignListener.canEditFormatting(arrayOf("plain §z")) { false })
    assertEquals(false, SignListener.canEditFormatting(arrayOf("§x§a§b§c§d§e§fHex")) { true })
  }

  @Test
  fun translatesOnlyPermittedCodes() {
    assertEquals("§aGreen §lBold", color("&AGreen &lBold"))
    assertEquals("&aGreen &lBold", color("&aGreen &lBold", permitted = false))
    assertEquals("&zUnknown", color("&zUnknown"))
  }
}
