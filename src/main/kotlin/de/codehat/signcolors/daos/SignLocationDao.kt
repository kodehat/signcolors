package de.codehat.signcolors.daos

import com.j256.ormlite.jdbc.JdbcConnectionSource
import de.codehat.signcolors.SignColors
import de.codehat.signcolors.dao.abstraction.Dao
import de.codehat.signcolors.database.model.SignLocation
import org.bukkit.Location

class SignLocationDao(connectionSource: JdbcConnectionSource): Dao<SignLocation, Void>(connectionSource, SignLocation::class.java) {

    fun exists(location: Location): Boolean {
        with(location) {
            return exists(this.world.name, this.blockX, this.blockY, this.blockZ)
        }
    }

    fun exists(world: String, x: Int, y: Int, z: Int): Boolean {
        val queryBuilder = dao.queryBuilder()
        val where = queryBuilder.where()

        with(where) {
            eq("world", world)
            and()
            eq("x", x)
            and()
            eq("y", y)
            and()
            eq("z", z)
        }

        return dao.query(queryBuilder.prepare()).size >= 1
    }

    fun create(location: Location) {
        with(location) {
            create(this.world.name, this.blockX, this.blockY, this.blockZ)
        }
    }

    fun create(world: String, x: Int, y: Int, z: Int) {
        SignLocation(world, x, y, z).apply {
            dao.create(this)
        }
    }

    fun delete(location: Location) {
        with(location) {
            delete(this.world.name, this.blockX, this.blockY, this.blockZ)
        }
    }

    fun delete(world: String, x: Int, y: Int, z: Int) {

    }

}