/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2022 CodeHat
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
package de.codehat.signcolors.database

import de.codehat.signcolors.database.abstraction.Database

class SqliteDatabase(connectionString: String) : Database(connectionString) {

    companion object {
        // Pattern is DATABASE_FILE_PATH
        private const val SQLITE_JDBC_CONNECTION_STRING = "jdbc:sqlite:%s"

        fun createConnectionString(databaseFilePath: String): String {
            return String.format(SQLITE_JDBC_CONNECTION_STRING, databaseFilePath)
        }
    }
}
