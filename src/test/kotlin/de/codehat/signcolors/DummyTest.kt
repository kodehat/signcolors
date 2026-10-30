// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class DummyTest {
  @Test
  fun alwaysTrueTest() {
    assertThat(1 + 1, `is`(2))
  }
}
