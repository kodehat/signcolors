package de.codehat.signcolors.database.abstraction

import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import de.codehat.signcolors.database.model.SignLocation

abstract class Database(connectionString: String) {

    var connectionSource: JdbcConnectionSource
        private set

    init {
        connectionSource = JdbcConnectionSource(connectionString)

        createRequiredTablesIfNotExist()
    }

    fun close() {
        connectionSource.closeQuietly() // Or just '#close()'?
    }

    private fun createRequiredTablesIfNotExist() {
        TableUtils.createTableIfNotExists(connectionSource, SignLocation::class.java)
    }
}
