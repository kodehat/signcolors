// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import de.codehat.signcolors.listener.SignListener
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BuySignValidationTest {
  @Test
  fun acceptsBothBuySignHeaders() {
    assertTrue(SignListener.isBuySignHeader("[SignColors]"))
    assertTrue(SignListener.isBuySignHeader("[sc]"))
    assertFalse(SignListener.isBuySignHeader("[Buy]"))
    assertFalse(SignListener.isBuySignHeader(null))
  }

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
