package de.codehat.signcolors.database.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = SignLocation.TABLE)
class SignLocation() {

    companion object {
        const val TABLE = "sc_sign_locations"
    }

    @DatabaseField
    lateinit var world: String

    @DatabaseField
    var x: Int = 0

    @DatabaseField
    var y: Int = 0

    @DatabaseField
    var z: Int = 0

    constructor(world: String, x: Int, y: Int, z: Int): this() {
        this.world = world
        this.x = x
        this.y = y
        this.z = z
    }

    override fun toString(): String {
        return "SL{$world#$x#$y#$z}"
    }
}