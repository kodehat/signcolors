// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.util

import de.codehat.signcolors.SignColors
import java.util.logging.Level

class ErrorUtil {
  companion object {
    internal fun report(
      plugin: SignColors,
      e: Exception,
    ) {
      if (!plugin.pluginConfig.getErrorReporting()!!) return

      plugin.logger.log(Level.SEVERE) { e.localizedMessage }
      // Sentry.capture(e)
    }
  }
}
