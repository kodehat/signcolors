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

class PluginConfig(
  plugin: SignColors,
) : AbstractConfig<YamlFileConfiguration, YamlConfiguration, PluginConfigKey>(
    plugin,
    "config.yml",
    YamlFileConfiguration(),
  ) {
  fun getLanguage(): String? = cfgData?.getString(PluginConfigKey.LANGUAGE.toString())

  fun getPrefixCharacter(): String = cfgData?.getString(PluginConfigKey.PREFIX_CHARACTER.toString()) ?: "&"

  fun getCraftingEnabled(): Boolean? = cfgData?.getBoolean(PluginConfigKey.CRAFTING_ENABLED.toString())

  fun getCraftingRecipeType(): String? = cfgData?.getString(PluginConfigKey.CRAFTING_RECIPE_TYPE.toString())

  fun getCraftingSignType(): String? = cfgData?.getString(PluginConfigKey.CRAFTING_SIGN_TYPE.toString())

  fun getCraftingAmount(): Int? = cfgData?.getInt(PluginConfigKey.CRAFTING_AMOUNT.toString())

  fun getCraftingIngredients(): ConfigurationSection? = cfgData?.getConfigurationSection(PluginConfigKey.CRAFTING_INGREDIENTS.toString())

  fun getCraftingShape(): List<String>? = cfgData?.getStringList(PluginConfigKey.CRAFTING_SHAPE.toString())

  fun getBuySignAmount(): Int? = cfgData?.getInt(PluginConfigKey.BUY_SIGN_AMOUNT.toString())

  fun getBuySignPrice(): Double? = cfgData?.getDouble(PluginConfigKey.BUY_SIGN_PRICE.toString())

  fun getBuySignSignType(): String? = cfgData?.getString(PluginConfigKey.BUY_SIGN_SIGN_TYPE.toString())

  fun getBuySignBlockedFirstLines(): List<String>? = cfgData?.getStringList(PluginConfigKey.BUY_SIGN_BLOCKED_FIRST_LINES.toString())

  fun getBuySignCreationSoundEnabled(): Boolean? = cfgData?.getBoolean(PluginConfigKey.BUY_SIGN_CREATION_SOUND_ENABLED.toString())

  fun getBuySignCreationSoundType(): String? = cfgData?.getString(PluginConfigKey.BUY_SIGN_CREATION_SOUND_TYPE.toString())

  fun getBuySignCreationSoundVolume(): Double? = cfgData?.getDouble(PluginConfigKey.BUY_SIGN_CREATION_SOUND_VOLUME.toString())

  fun getBuySignCreationSoundPitch(): Double? = cfgData?.getDouble(PluginConfigKey.BUY_SIGN_CREATION_SOUND_PITCH.toString())

  fun getBuySignReceivingSoundEnabled(): Boolean? = cfgData?.getBoolean(PluginConfigKey.BUY_SIGN_RECEIVING_SOUND_ENABLED.toString())

  fun getBuySignReceivingSoundType(): String? = cfgData?.getString(PluginConfigKey.BUY_SIGN_RECEIVING_SOUND_TYPE.toString())

  fun getBuySignReceivingSoundVolume(): Double? = cfgData?.getDouble(PluginConfigKey.BUY_SIGN_RECEIVING_SOUND_VOLUME.toString())

  fun getBuySignReceivingSoundPitch(): Double? = cfgData?.getDouble(PluginConfigKey.BUY_SIGN_RECEIVING_SOUND_PITCH.toString())

  fun getUpdateCheck(): Boolean? = cfgData?.getBoolean(PluginConfigKey.OTHER_UPDATE_CHECK.toString())

  fun getErrorReporting(): Boolean? = cfgData?.getBoolean(PluginConfigKey.OTHER_ERROR_REPORTING.toString())

  fun getDebug(): Boolean? = cfgData?.getBoolean(PluginConfigKey.OTHER_DEBUG.toString())

  fun getConfigVersion(): Int? = cfgData?.getInt(PluginConfigKey.OTHER_CONFIG_VERSION.toString())
}
