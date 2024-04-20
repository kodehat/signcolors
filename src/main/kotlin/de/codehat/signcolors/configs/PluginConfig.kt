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
package de.codehat.signcolors.configs

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.config.AbstractConfig
import de.codehat.signcolors.config.YamlFileConfiguration
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration

class PluginConfig(plugin: SignColors) :
  AbstractConfig<YamlFileConfiguration, YamlConfiguration, PluginConfigKey>(
    plugin,
    "config.yml",
    YamlFileConfiguration(),
  ) {
  fun getLanguage(): String? {
    return cfgData?.getString(PluginConfigKey.LANGUAGE.toString())
  }

  fun getPrefixCharacter(): String {
    return cfgData?.getString(PluginConfigKey.PREFIX_CHARACTER.toString()) ?: "&"
  }

  fun getCraftingEnabled(): Boolean? {
    return cfgData?.getBoolean(PluginConfigKey.CRAFTING_ENABLED.toString())
  }

  fun getCraftingRecipeType(): String? {
    return cfgData?.getString(PluginConfigKey.CRAFTING_RECIPE_TYPE.toString())
  }

  fun getCraftingSignType(): String? {
    return cfgData?.getString(PluginConfigKey.CRAFTING_SIGN_TYPE.toString())
  }

  fun getCraftingAmount(): Int? {
    return cfgData?.getInt(PluginConfigKey.CRAFTING_AMOUNT.toString())
  }

  fun getCraftingIngredients(): ConfigurationSection? {
    return cfgData?.getConfigurationSection(PluginConfigKey.CRAFTING_INGREDIENTS.toString())
  }

  fun getCraftingShape(): List<String>? {
    return cfgData?.getStringList(PluginConfigKey.CRAFTING_SHAPE.toString())
  }

  fun getBuySignAmount(): Int? {
    return cfgData?.getInt(PluginConfigKey.BUY_SIGN_AMOUNT.toString())
  }

  fun getBuySignPrice(): Double? {
    return cfgData?.getDouble(PluginConfigKey.BUY_SIGN_PRICE.toString())
  }

  fun getBuySignBlockedFirstLines(): List<String>? {
    return cfgData?.getStringList(PluginConfigKey.BUY_SIGN_BLOCKED_FIRST_LINES.toString())
  }

  fun getBuySignCreationSoundEnabled(): Boolean? {
    return cfgData?.getBoolean(PluginConfigKey.BUY_SIGN_CREATION_SOUND_ENABLED.toString())
  }

  fun getBuySignCreationSoundType(): String? {
    return cfgData?.getString(PluginConfigKey.BUY_SIGN_CREATION_SOUND_TYPE.toString())
  }

  fun getBuySignCreationSoundVolume(): Double? {
    return cfgData?.getDouble(PluginConfigKey.BUY_SIGN_CREATION_SOUND_VOLUME.toString())
  }

  fun getBuySignCreationSoundPitch(): Double? {
    return cfgData?.getDouble(PluginConfigKey.BUY_SIGN_CREATION_SOUND_PITCH.toString())
  }

  fun getBuySignReceivingSoundEnabled(): Boolean? {
    return cfgData?.getBoolean(PluginConfigKey.BUY_SIGN_RECEIVING_SOUND_ENABLED.toString())
  }

  fun getBuySignReceivingSoundType(): String? {
    return cfgData?.getString(PluginConfigKey.BUY_SIGN_RECEIVING_SOUND_TYPE.toString())
  }

  fun getBuySignReceivingSoundVolume(): Double? {
    return cfgData?.getDouble(PluginConfigKey.BUY_SIGN_RECEIVING_SOUND_VOLUME.toString())
  }

  fun getBuySignReceivingSoundPitch(): Double? {
    return cfgData?.getDouble(PluginConfigKey.BUY_SIGN_RECEIVING_SOUND_PITCH.toString())
  }

  fun getDatabaseType(): String? {
    return cfgData?.getString(PluginConfigKey.DATABASE_TYPE.toString())
  }

  fun getDatabaseHost(): String? {
    return cfgData?.getString(PluginConfigKey.DATABASE_HOST.toString())
  }

  fun getDatabaseName(): String? {
    return cfgData?.getString(PluginConfigKey.DATABASE_NAME.toString())
  }

  fun getDatabasePort(): Int? {
    return cfgData?.getInt(PluginConfigKey.DATABASE_PORT.toString())
  }

  fun getDatabaseUser(): String? {
    return cfgData?.getString(PluginConfigKey.DATABASE_USER.toString())
  }

  fun getDatabasePassword(): String? {
    return cfgData?.getString(PluginConfigKey.DATABASE_PASSWORD.toString())
  }

  fun getUpdateCheck(): Boolean? {
    return cfgData?.getBoolean(PluginConfigKey.OTHER_UPDATE_CHECK.toString())
  }

  fun getErrorReporting(): Boolean? {
    return cfgData?.getBoolean(PluginConfigKey.OTHER_ERROR_REPORTING.toString())
  }

  fun getDebug(): Boolean? {
    return cfgData?.getBoolean(PluginConfigKey.OTHER_DEBUG.toString())
  }

  fun getConfigVersion(): Int? {
    return cfgData?.getInt(PluginConfigKey.OTHER_CONFIG_VERSION.toString())
  }
}
