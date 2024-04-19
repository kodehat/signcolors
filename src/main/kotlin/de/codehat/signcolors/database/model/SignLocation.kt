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
package de.codehat.signcolors.database.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = SignLocation.TABLE)
class SignLocation() {

    companion object {
        const val TABLE = "sc_sign_locations"
    }

    @DatabaseField lateinit var world: String

    @DatabaseField var x: Int = 0

    @DatabaseField var y: Int = 0

    @DatabaseField var z: Int = 0

    constructor(world: String, x: Int, y: Int, z: Int) : this() {
        this.world = world
        this.x = x
        this.y = y
        this.z = z
    }

    override fun toString(): String {
        return "SL{$world#$x#$y#$z}"
    }
}
