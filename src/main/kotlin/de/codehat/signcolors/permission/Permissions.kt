// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.permission

enum class Permissions(
  private var permission: String,
) {
  ALL("signcolors.*"),
  CMD_ALL("signcolors.command.*"),
  BUY_SIGN_ALL("signcolors.buysign.*"),
  COLOR_ALL("signcolors.color.*"),
  FORMATTING_ALL("signcolors.formatting.*"),
  BYPASS_ALL("signcolors.bypass.*"),
  CMD_INFO("signcolors.command.info"),
  CMD_HELP("signcolors.command.help"),
  CMD_RELOAD("signcolors.command.reload"),
  CMD_CODES("signcolors.command.codes"),
  CMD_GIVE("signcolors.command.give"),
  BUY_SIGN_CREATE("signcolors.buysign.create"),
  BUY_SIGN_USE("signcolors.buysign.use"),
  BYPASS_CRAFTING("signcolors.bypass.crafting"),
  BYPASS_BLOCKED_FIRST_LINES("signcolors.bypass.blockedfirstlines"),
  UPDATE_MESSAGE("signcolors.updatemessage"),
  ;

  override fun toString() = permission

  companion object {
    fun color(colorCode: Char): String = "signcolors.color.$colorCode"

    fun formatting(formattingCode: Char): String = "signcolors.formatting.$formattingCode"
  }
}
