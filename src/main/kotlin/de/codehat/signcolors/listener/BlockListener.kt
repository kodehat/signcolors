// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.listener

import de.codehat.signcolors.SignColors
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack

class BlockListener(
  private val plugin: SignColors,
) : Listener {
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  fun onPlaceColoredSign(event: BlockPlaceEvent) {
    if (!plugin.coloredSignMarker.isMarked(event.itemInHand)) return
    val sign = event.blockPlaced.state as? Sign ?: return
    plugin.coloredSignMarker.mark(sign)
    sign.update()
  }

  @EventHandler(ignoreCancelled = true)
  fun onDropColoredSign(event: BlockDropItemEvent) {
    val sign = event.blockState as? Sign ?: return
    if (!plugin.coloredSignMarker.isMarked(sign)) return
    for (drop in event.items) {
      val stack = plugin.coloredSignManager.coloredSignStacks[drop.itemStack.type] ?: continue
      drop.itemStack = ItemStack(stack).apply { amount = drop.itemStack.amount }
    }
  }
}
