// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.util

import de.codehat.signcolors.SignColors
import java.util.logging.Logger

fun Logger.debug(
  plugin: SignColors,
  message: String,
) {
  if (plugin.pluginConfig.getDebug() == true) {
    info("DEBUG: $message")
  }
}
