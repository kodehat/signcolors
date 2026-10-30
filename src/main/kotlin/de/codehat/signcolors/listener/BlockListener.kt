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
