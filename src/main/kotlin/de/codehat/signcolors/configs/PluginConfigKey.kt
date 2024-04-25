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
package de.codehat.signcolors.configs

enum class PluginConfigKey(private val key: String) {
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

  // Database:
  DATABASE_TYPE("database.type"),
  DATABASE_HOST("database.host"),
  DATABASE_PORT("database.port"),
  DATABASE_NAME("database.name"),
  DATABASE_USER("database.user"),
  DATABASE_PASSWORD("database.password"),

  // Other:
  OTHER_UPDATE_CHECK("other.updateCheck"),
  OTHER_ERROR_REPORTING("other.errorReporting"),
  OTHER_DEBUG("other.debug"),
  OTHER_CONFIG_VERSION("other.configVersion"),
  ;

  override fun toString(): String = key
}
