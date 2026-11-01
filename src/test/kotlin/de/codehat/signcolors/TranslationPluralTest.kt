// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import de.codehat.signcolors.configs.TranslationConfig
import de.codehat.signcolors.configs.TranslationConfigKey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.util.Properties

class TranslationPluralTest {
  private val pairs =
    listOf(
      TranslationConfigKey.GIVE_DONOR_SUCCESS to TranslationConfigKey.GIVE_DONOR_SUCCESS_SINGLE,
      TranslationConfigKey.GIVE_TARGET_SUCCESS to TranslationConfigKey.GIVE_TARGET_SUCCESS_SINGLE,
      TranslationConfigKey.BUYSIGN_SIGN_AMOUNT_RECEIVED to TranslationConfigKey.BUYSIGN_SIGN_AMOUNT_RECEIVED_SINGLE,
    )

  @Test
  fun selectsSingularOnlyForOneAndFallsBackForCustomLanguages() {
    for ((plural, singular) in pairs) {
      assertEquals(singular, plural.forAmount(1, singular))
      assertEquals(plural, plural.forAmount(2, singular))
      assertEquals(plural, plural.forAmount(0, singular))
      val custom = Properties().apply { setProperty(plural.toString(), "old message") }
      assertEquals("old message", TranslationConfig.translation(custom, singular))
      custom.setProperty(singular.toString(), "single message")
      assertEquals("single message", TranslationConfig.translation(custom, singular))
    }
  }

  @Test
  fun bundledTranslationsContainBothFormsWithCompatibleArguments() {
    for (language in listOf("en_US", "de_DE")) {
      val properties = Properties()
      javaClass.getResourceAsStream("/translations/$language.properties")!!.use { properties.load(it) }
      for ((plural, singular) in pairs) {
        for (key in listOf(plural, singular)) {
          val text = properties.getProperty(key.toString())
          assertNotNull(text, "$language: $key")
          if (plural == TranslationConfigKey.BUYSIGN_SIGN_AMOUNT_RECEIVED) {
            text.format(12.5, 1)
          } else {
            text.format(1, "Alex")
          }
        }
      }
    }
  }
}
