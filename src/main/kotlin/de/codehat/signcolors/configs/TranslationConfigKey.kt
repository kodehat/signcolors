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

enum class TranslationConfigKey(private val key: String) {
  TAG("tag"),
  ERROR_PERMISSION_MISSING("error.permission.missing"),
  ERROR_COMMAND_UNKNOWN("error.command.unknown"),
  PARAMETER_REQUIRED("parameter.required"),
  PARAMETER_OPTIONAL("parameter.optional"),
  PARAMETER_PLAYER("parameter.player"),
  PARAMETER_AMOUNT("parameter.amount"),
  COLOR_CODES_COLORS("color.codes.colors"),
  COLOR_CODES_FORMATS("color.codes.formats"),
  INFO_AUTHOR("info.author"),
  INFO_VERSION("info.version"),
  COMMAND_HELP("command.help"),
  COMMAND_HELP_PAGE("command.help.page"),
  BUYSIGN_NAME("buysign.name"),
  BUYSIGN_LORE("buysign.lore"),
  CONFIG_RELOAD("config.reload"),
  ERROR_BUYSIGN_FORMAT_INCORRECT("error.buysign.format-incorrect"),
  ERROR_BUYSIGN_PLAYER_INCORRECT("error.buysign.player-incorrect"),
  ERROR_BUYSIGN_AMOUNT_OR_PRICE_TOO_LOW("error.buysign.amount-or-price-too-low"),
  BUYSIGN_LINE_TWO("buysign.line.two"),
  BUYSIGN_LINE_FOUR("buysign.line.four"),
  BUYSIGN_SIGN_AMOUNT_RECEIVED("buysign.sign-amount-received"),
  ERROR_BUYSIGN_FIRST_LINE_BLOCKED("error.buysign.first-line-blocked"),
  ERROR_NOT_ENOUGH_MONEY("error.not-enough-money"),
  ERROR_NOT_ENOUGH_SPACE("error.not-enough-space"),
  ERROR_VAULT_MISSING("error.vault-missing"),
  ERROR_PLAYER_NOT_AVAILABLE("error.player-not-available"),
  ERROR_GIVE_AMOUNT_INVALID("error.give.amount-invalid"),
  GIVE_DONOR_SUCCESS("give.donor.success"),
  GIVE_TARGET_SUCCESS("give.target.success"),
  VERSION_NEW_AVAILABLE("version.new-available"),
  ;

  override fun toString(): String = key
}
