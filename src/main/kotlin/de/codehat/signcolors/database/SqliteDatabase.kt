package de.codehat.signcolors.database

import de.codehat.signcolors.database.abstraction.Database

class SqliteDatabase(connectionString: String): Database(connectionString) {

    companion object {
        // Pattern is DATABASE_FILE_PATH
        private const val SQLITE_JDBC_CONNECTION_STRING = "jdbc:sqlite:%s"

        fun createConnectionString(databaseFilePath: String): String {
            return String.format(SQLITE_JDBC_CONNECTION_STRING, databaseFilePath)
        }
    }
}