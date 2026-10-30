// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
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

  override fun getFileName(): String = file.name.substring(0, file.name.lastIndexOf("."))

  override fun getConfigurationData(): V? = cfgData
}
