// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.config

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Properties

class PropertiesFileConfiguration : IFileConfiguration<Properties> {
  override fun load(file: File): Properties {
    val properties = Properties()
    FileInputStream(file).use { properties.load(it) }
    return properties
  }

  override fun save(
    configuration: Properties,
    file: File,
  ) {
    FileOutputStream(file).use { configuration.store(it, null) }
  }
}
