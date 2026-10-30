/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2026 CodeHat
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
package de.codehat.signcolors.managers

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.database.Database
import de.codehat.signcolors.database.MysqlDatabase
import de.codehat.signcolors.database.SqliteDatabase
import de.codehat.signcolors.manager.Manager
import java.io.File

class DatabaseManager(
  plugin: SignColors,
) : Manager(plugin) {
  internal lateinit var database: Database

  init {
    start()
  }

  override fun start() {
    val databaseType = plugin.pluginConfig.getDatabaseType()

    if (databaseType.equals("sqlite", true)) {
      val sqliteDatabasePath = plugin.dataFolder.absolutePath + File.separator + "sign_locations.db"

      database = SqliteDatabase(SqliteDatabase.createConnectionString(sqliteDatabasePath))

      plugin.logger.info(
        "Using SQLite to save sign locations (path to DB is '$sqliteDatabasePath').",
      )
    } else if (databaseType.equals("mysql", true)) {
      database =
        MysqlDatabase(
          MysqlDatabase.createConnectionString(
            plugin.pluginConfig.getDatabaseHost(),
            plugin.pluginConfig.getDatabasePort()!!,
            plugin.pluginConfig.getDatabaseName(),
            plugin.pluginConfig.getDatabaseUser(),
            plugin.pluginConfig.getDatabasePassword(),
          ),
        )

      plugin.logger.info("Using MySQL to save sign locations.")
    }
  }

  override fun stop() {
    database.close()
  }
}
