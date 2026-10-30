// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.util

import org.bukkit.NamespacedKey
import org.bukkit.block.Sign
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

class ColoredSignMarker(
  plugin: Plugin,
) {
  private val key = NamespacedKey(plugin, "colored_sign")

  fun mark(item: ItemStack) {
    val meta = item.itemMeta ?: return
    meta.persistentDataContainer.set(key, PersistentDataType.BYTE, 1.toByte())
    item.itemMeta = meta
  }

  fun isMarked(item: ItemStack): Boolean = item.itemMeta?.persistentDataContainer?.get(key, PersistentDataType.BYTE) == 1.toByte()

  fun mark(sign: Sign) {
    sign.persistentDataContainer.set(key, PersistentDataType.BYTE, 1.toByte())
  }

  fun isMarked(sign: Sign): Boolean = sign.persistentDataContainer.get(key, PersistentDataType.BYTE) == 1.toByte()
}
