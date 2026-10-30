// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.config

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class YamlFileConfiguration : IFileConfiguration<YamlConfiguration> {
  override fun load(file: File): YamlConfiguration = YamlConfiguration.loadConfiguration(file)

  override fun save(
    configuration: YamlConfiguration,
    file: File,
  ) {
    configuration.save(file)
  }
}
