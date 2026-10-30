// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.configs

enum class PluginConfigKey(
  private val key: String,
) {
  // Basics:
  LANGUAGE("language"),
  PREFIX_CHARACTER("prefixCharacter"),

  // Crafting:
  CRAFTING_ENABLED("crafting.enabled"),
  CRAFTING_RECIPE_TYPE("crafting.recipeType"),
  CRAFTING_SIGN_TYPE("crafting.signType"),
  CRAFTING_AMOUNT("crafting.amount"),
  CRAFTING_INGREDIENTS("crafting.ingredients"),
  CRAFTING_SHAPE("crafting.shape"),

  // "Buy" sign:
  BUY_SIGN_AMOUNT("buySign.amount"),
  BUY_SIGN_PRICE("buySign.price"),
  BUY_SIGN_SIGN_TYPE("buySign.signType"),
  BUY_SIGN_BLOCKED_FIRST_LINES("buySign.blockedFirstLines"),
  BUY_SIGN_CREATION_SOUND_ENABLED("buySign.creationSound.enabled"),
  BUY_SIGN_CREATION_SOUND_TYPE("buySign.creationSound.type"),
  BUY_SIGN_CREATION_SOUND_VOLUME("buySign.creationSound.volume"),
  BUY_SIGN_CREATION_SOUND_PITCH("buySign.creationSound.pitch"),
  BUY_SIGN_RECEIVING_SOUND_ENABLED("buySign.receivingSound.enabled"),
  BUY_SIGN_RECEIVING_SOUND_TYPE("buySign.receivingSound.type"),
  BUY_SIGN_RECEIVING_SOUND_VOLUME("buySign.receivingSound.volume"),
  BUY_SIGN_RECEIVING_SOUND_PITCH("buySign.receivingSound.pitch"),

  // Other:
  OTHER_UPDATE_CHECK("other.updateCheck"),
  OTHER_ERROR_REPORTING("other.errorReporting"),
  OTHER_DEBUG("other.debug"),
  OTHER_CONFIG_VERSION("other.configVersion"),
  ;

  override fun toString(): String = key
}
