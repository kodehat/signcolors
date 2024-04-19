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

class MysqlDatabase(connectionString: String) : Database(connectionString) {

    companion object {
        // Pattern is HOSTNAME -> PORT -> DATABASE -> USER -> PASSWORD
        private const val MYSQL_JDBC_CONNECTION_STRING =
            "jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false&autoReconnect=true"

        fun createConnectionString(
            hostname: String?,
            port: Int,
            database: String?,
            user: String?,
            password: String?
        ): String {
            return String.format(
                MYSQL_JDBC_CONNECTION_STRING,
                hostname,
                port,
                database,
                user,
                password
            )
        }
    }
}
