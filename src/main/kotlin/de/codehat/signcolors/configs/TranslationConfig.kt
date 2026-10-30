// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.configs

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.config.AbstractConfig
import de.codehat.signcolors.config.PropertiesFileConfiguration
import de.codehat.signcolors.util.color
import java.io.File
import java.util.Properties

class TranslationConfig(
  plugin: SignColors,
  language: String? = "en_US",
) : AbstractConfig<PropertiesFileConfiguration, Properties, TranslationConfigKey>(
    plugin,
    "translations" + File.separator + "$language.properties",
    PropertiesFileConfiguration(),
  ) {
  fun t(
    key: TranslationConfigKey,
    vararg params: Any,
  ): String = cfgData?.getProperty(key.toString())!!.format(*params).color()
}
