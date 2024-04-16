package de.codehat.signcolors.util

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.config.ConfigKey
import java.lang.Exception

class ErrorUtil {
	companion object {
	    internal fun report(e: Exception) {
			val config = SignColors.instance.config

			if (!config.getBoolean(ConfigKey.OTHER_ERROR_REPORTING.toString())) return

			//Sentry.capture(e)
		}
	}
}
