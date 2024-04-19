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
package de.codehat.signcolors.config

import de.codehat.signcolors.SignColors
import java.io.File
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration

abstract class Config(private val fileName: String) {

    private val file = File(SignColors.instance.dataFolder, this.fileName)
    protected lateinit var cfg: FileConfiguration
        private set

    protected fun setup(extract: Boolean) {
        if (!file.exists() && extract) {
            try {
                SignColors.instance.saveResource(this.fileName, false)
            } catch (e: IllegalArgumentException) {
                with(SignColors.instance.logger) { warning("Unable to load file '$fileName'.") }
                return
            }
        } // May throw an exception if resource is not found
        else if (!file.exists()) {
            this.file.createNewFile()
        }

        this.cfg = YamlConfiguration.loadConfiguration(this.file)
    }

    open fun save() {
        this.cfg.save(this.file)
    }

    open fun reload() {
        this.setup(false)
    }

    protected fun getFilename(): String {
        return this.file.name.substring(0, this.file.name.lastIndexOf("."))
    }
}
