package de.codehat.signcolors.util

import de.codehat.signcolors.permission.Permissions
import org.bukkit.command.CommandSender

fun CommandSender.hasPermission(permission: Permissions): Boolean = this.hasPermission(permission.toString())
