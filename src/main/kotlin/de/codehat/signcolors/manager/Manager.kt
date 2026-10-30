// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.manager

import de.codehat.signcolors.SignColors

abstract class Manager(
  protected val plugin: SignColors,
) : IManager {
  override fun stop() {
    // Nothing by default.
  }

  override fun reload() {
    stop()
    start()
  }
}
