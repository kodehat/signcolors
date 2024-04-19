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
package de.codehat.signcolors.daos

import com.j256.ormlite.jdbc.JdbcConnectionSource
import de.codehat.signcolors.dao.Dao
import de.codehat.signcolors.database.model.SignLocation
import org.bukkit.Location
import org.bukkit.block.Block

class SignLocationDao(connectionSource: JdbcConnectionSource) :
    Dao<SignLocation, Void>(connectionSource, SignLocation::class.java) {

    fun exists(block: Block): Boolean {
        return exists(block.location)
    }

    fun exists(location: Location): Boolean {
        with(location) {
            return exists(this.world!!.name, this.blockX, this.blockY, this.blockZ)
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

    fun create(block: Block) {
        create(block.location)
    }

    fun create(location: Location) {
        with(location) { create(this.world!!.name, this.blockX, this.blockY, this.blockZ) }
    }

    fun create(world: String, x: Int, y: Int, z: Int) {
        SignLocation(world, x, y, z).apply { dao.create(this) }
    }

    fun delete(block: Block) {
        delete(block.location)
    }

    fun delete(location: Location) {
        with(location) { delete(this.world!!.name, this.blockX, this.blockY, this.blockZ) }
    }

    fun delete(world: String, x: Int, y: Int, z: Int) {
        if (exists(world, x, y, z)) {
            val deleteBuilder = dao.deleteBuilder()
            val where = deleteBuilder.where()

            with(where) {
                eq("world", world)
                and()
                eq("x", x)
                and()
                eq("y", y)
                and()
                eq("z", z)
            }

            deleteBuilder.delete()
        }
    }
}
