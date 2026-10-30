/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2026 CodeHat
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
package de.codehat.signcolors

import de.codehat.signcolors.listener.SignListener
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SignColorParsingTest {
  private fun color(line: String, permitted: Boolean = true): String =
    SignListener.colorSignLine(line, "&") { permitted }

  @Test
  fun preservesRepeatedTextAndPrefixes() {
    assertEquals("hello&hello", color("hello&hello"))
    assertEquals("a§a", color("a&a"))
    assertEquals("&§aHi&", color("&&aHi&"))
    assertEquals("hello&&hello", color("hello&&hello"))
  }

  @Test
  fun translatesOnlyPermittedCodes() {
    assertEquals("§aGreen §lBold", color("&AGreen &lBold"))
    assertEquals("&aGreen &lBold", color("&aGreen &lBold", permitted = false))
    assertEquals("&zUnknown", color("&zUnknown"))
  }
}
