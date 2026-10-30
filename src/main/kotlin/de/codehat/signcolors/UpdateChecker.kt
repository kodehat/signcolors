// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import org.bukkit.Bukkit
import java.io.IOException
import java.net.URI
import java.util.Scanner
import java.util.function.Consumer
import java.util.logging.Level

class UpdateChecker(
  val plugin: SignColors,
  private val resourceID: String,
) {
  fun getVersion(consumer: Consumer<String>) {
    Bukkit
      .getScheduler()
      .runTaskAsynchronously(
        plugin,
        Runnable {
          try {
            URI("https://api.spigotmc.org/legacy/update.php?resource=$resourceID/~")
              .toURL()
              .openStream()
              .use {
                val scanner = Scanner(it)
                if (scanner.hasNext()) {
                  consumer.accept(scanner.next())
                }
              }
          } catch (e: IOException) {
            plugin.logger.log(Level.WARNING, "Unable to check for update!", e)
          }
        },
      )
  }
}
