/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2024 CodeHat
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

abstract class AbstractConfig<T : IFileConfiguration<V>, V, K>(
  protected val plugin: SignColors,
  private val fileName: String,
  private val cfg: IFileConfiguration<V>,
) : IConfig<T, V, K> {
  private val file = File(plugin.dataFolder, fileName)
  protected var cfgData: V?

  init {
    this.cfgData = load(true)
  }

  final override fun load(extract: Boolean): V? {
    if (!file.exists() && extract) {
      try {
        plugin.saveResource(fileName, false)
      } catch (e: IllegalArgumentException) {
        with(plugin.logger) { warning("Unable to load file '$fileName'.") }
        return null
      }
    } else if (!file.exists()) {
      // May throw an exception if resource is not found.
      file.createNewFile()
    }

    return cfg.load(file)
  }

  override fun save() {
    cfg.save(cfgData!!, file)
  }

  override fun reload() {
    load(true)
  }

  override fun getFileName(): String {
    return file.name.substring(0, file.name.lastIndexOf("."))
  }

  override fun getConfigurationData(): V? {
    return cfgData
  }
}
