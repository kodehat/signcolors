// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.configs

enum class TranslationConfigKey(
  private val key: String,
  internal val fallbackKey: String? = null,
) {
  TAG("tag"),
  ERROR_PERMISSION_MISSING("error.permission.missing"),
  ERROR_COMMAND_UNKNOWN("error.command.unknown"),
  PARAMETER_REQUIRED("parameter.required"),
  PARAMETER_OPTIONAL("parameter.optional"),
  PARAMETER_PLAYER("parameter.player"),
  PARAMETER_AMOUNT("parameter.amount"),
  PARAMETER_SIGN_MATERIAL("parameter.sign-material"),
  COLOR_CODES_COLORS("color.codes.colors"),
  COLOR_CODES_FORMATS("color.codes.formats"),
  COLOR_CODES_ORDER_HINT("color.codes.order-hint"),
  INFO_AUTHOR("info.author"),
  INFO_VERSION("info.version"),
  COMMAND_HELP("command.help"),
  COMMAND_HELP_PAGE("command.help.page"),
  BUYSIGN_LORE("buysign.lore"),
  CONFIG_RELOAD("config.reload"),
  ERROR_BUYSIGN_FORMAT_INCORRECT("error.buysign.format-incorrect"),
  ERROR_BUYSIGN_PLAYER_INCORRECT("error.buysign.player-incorrect"),
  ERROR_BUYSIGN_AMOUNT_OR_PRICE_TOO_LOW("error.buysign.amount-or-price-too-low"),
  ERROR_BUYSIGN_INVALID_SIGN_TYPE("error.buysign.invalid-sign-type"),
  BUYSIGN_LINE_FOUR("buysign.line.four"),
  BUYSIGN_SIGN_AMOUNT_RECEIVED("buysign.sign-amount-received"),
  BUYSIGN_SIGN_AMOUNT_RECEIVED_SINGLE("buysign.sign-amount-received.single", "buysign.sign-amount-received"),
  ERROR_BUYSIGN_FIRST_LINE_BLOCKED("error.buysign.first-line-blocked"),
  ERROR_NOT_ENOUGH_MONEY("error.not-enough-money"),
  ERROR_NOT_ENOUGH_SPACE("error.not-enough-space"),
  ERROR_VAULT_MISSING("error.vault-missing"),
  ERROR_PLAYER_NOT_AVAILABLE("error.player-not-available"),
  ERROR_GIVE_AMOUNT_INVALID("error.give.amount-invalid"),
  ERROR_SIGN_MATERIAL_INVALID("error.give.sign-material-invalid"),
  GIVE_DONOR_SUCCESS("give.donor.success"),
  GIVE_DONOR_SUCCESS_SINGLE("give.donor.success.single", "give.donor.success"),
  GIVE_TARGET_SUCCESS("give.target.success"),
  GIVE_TARGET_SUCCESS_SINGLE("give.target.success.single", "give.target.success"),
  VERSION_NEW_AVAILABLE("version.new-available"),
  ;

  fun forAmount(
    amount: Int,
    singular: TranslationConfigKey,
  ): TranslationConfigKey = if (amount == 1) singular else this

  override fun toString(): String = key
}
