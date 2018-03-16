package de.codehat.signcolors.util

import de.codehat.signcolors.SignColors
import java.util.logging.Logger

fun Logger.debug(message: String) {
    if (SignColors.debug) {
        info("DEBUG: $message")
    }
}