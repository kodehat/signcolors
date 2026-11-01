// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
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

  fun isEditableSignColorsEnabled(): Boolean = cfgData?.getBoolean("other.editableSignColors", true) ?: true

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
