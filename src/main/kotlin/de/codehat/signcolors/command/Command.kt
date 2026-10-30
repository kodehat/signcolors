// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.command

import de.codehat.signcolors.SignColors
import org.bukkit.command.CommandSender

abstract class Command(
  protected val plugin: SignColors,
) {
  abstract fun onCommand(
    sender: CommandSender,
    command: org.bukkit.command.Command,
    label: String,
    args: Array<out String>,
  )
}
