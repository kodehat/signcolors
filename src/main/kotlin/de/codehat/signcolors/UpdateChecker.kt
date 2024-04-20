/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2024 CodeHat
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

import org.bukkit.Bukkit
import java.io.IOException
import java.net.URL
import java.util.Scanner
import java.util.function.Consumer
import java.util.logging.Level

class UpdateChecker(val plugin: SignColors, private val resourceID: String) {
  fun getVersion(consumer: Consumer<String>) {
    Bukkit.getScheduler()
      .runTaskAsynchronously(
        plugin,
        Runnable {
          try {
            URL("https://api.spigotmc.org/legacy/update.php?resource=$resourceID/~")
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
