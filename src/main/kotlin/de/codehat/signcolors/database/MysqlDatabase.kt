package de.codehat.signcolors.database

import de.codehat.signcolors.database.abstraction.Database

class MysqlDatabase(connectionString: String): Database(connectionString) {

    companion object {
        // Pattern is HOSTNAME -> PORT -> DATABASE -> USER -> PASSWORD
        private const val MYSQL_JDBC_CONNECTION_STRING = "jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false&autoReconnect=true"

        fun createConnectionString(hostname: String, port: Int, database: String, user: String, password: String): String {
            return String.format(MYSQL_JDBC_CONNECTION_STRING,
                    hostname,
                    port,
                    database,
                    user,
                    password)
        }
    }
}