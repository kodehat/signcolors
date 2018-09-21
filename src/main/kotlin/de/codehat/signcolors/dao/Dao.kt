package de.codehat.signcolors.dao

import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource

abstract class Dao<T, ID>(connectionSource: JdbcConnectionSource, typeClass: Class<T>) {

    protected val dao = DaoManager.createDao(connectionSource, typeClass)!!
}