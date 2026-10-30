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
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class BuySignValidationTest {
  @Test
  fun acceptsValidInputAndDefaults() {
    assertEquals(10 to 100.0, SignListener.parseBuySignAmountPrice("", 10, 100.0))
    assertEquals(3 to 2.5, SignListener.parseBuySignAmountPrice(" 3 : 2.5 ", null, null))
  }

  @Test
  fun rejectsInvalidAmountsAndPrices() {
    for (line in listOf("3", "1:2:3", "1:", "0:2", "-1:2", "1:0", "1:-2", "1:NaN", "1:Infinity")) {
      assertNull(SignListener.parseBuySignAmountPrice(line, 10, 100.0), line)
    }
    assertNull(SignListener.parseBuySignAmountPrice("", 0, 100.0))
    assertNull(SignListener.parseBuySignAmountPrice("", 1, Double.NaN))
    assertNull(SignListener.parseBuySignAmountPrice("", null, 1.0))
  }
}
