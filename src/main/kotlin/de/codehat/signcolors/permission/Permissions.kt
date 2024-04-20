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
package de.codehat.signcolors.permission

enum class Permissions(private var permission: String) {
  ALL("signcolors.*"),
  CMD_ALL("signcolors.command.*"),
  BUY_SIGN_ALL("signcolors.buysign.*"),
  COLOR_ALL("signcolors.color.*"),
  FORMATTING_ALL("signcolors.formatting.*"),
  BYPASS_ALL("signcolors.bypass.*"),
  CMD_INFO("signcolors.command.info"),
  CMD_HELP("signcolors.command.help"),
  CMD_RELOAD("signcolors.command.reload"),
  CMD_COLOR_CODES("signcolors.command.colorcodes"),
  CMD_GIVE("signcolors.command.give"),
  BUY_SIGN_CREATE("signcolors.buysign.create"),
  BUY_SIGN_USE("signcolors.buysign.use"),
  BYPASS_CRAFTING("signcolors.bypass.crafting"),
  BYPASS_BLOCKED_FIRST_LINES("signcolors.bypass.blockedfirstlines"),
  UPDATE_MESSAGE("signcolors.updatemessage"),
  ;

  override fun toString() = permission

  companion object {
    fun color(colorCode: Char): String {
      return "signcolors.color.$colorCode"
    }

    fun formatting(formattingCode: Char): String {
      return "signcolors.formatting.$formattingCode"
    }
  }
}
