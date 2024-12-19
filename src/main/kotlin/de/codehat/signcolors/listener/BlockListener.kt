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
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.SignUtil
import de.codehat.signcolors.util.hasPermission
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack

class BlockListener(private val plugin: SignColors) : Listener {
  @Suppress("unused")
  @EventHandler
  fun onPlacingOneSign(event: BlockPlaceEvent) {
    val player = event.player
    val itemInMainHand = player.inventory.itemInMainHand

    if (
      plugin.coloredSignManager.craftingEnabled == true &&
      !player.hasPermission(Permissions.BYPASS_CRAFTING) &&
      itemInMainHand.amount == 1 &&
      itemInMainHand.type.name.endsWith("_SIGN") &&
      itemInMainHand.itemMeta!!.hasLore()
    ) {
      plugin.fixSignPlayers.add(player)
    }
  }

  @Suppress("unused")
  @EventHandler
  fun onBreakColoredSign(event: BlockBreakEvent) {
    val player = event.player
    val block = event.block

    if (
      plugin.coloredSignManager.craftingEnabled &&
      SignUtil.getAllSignMaterials().contains(
        block.type,
      ) && plugin.modelManager.signLocationDao.exists(block)
    ) {
      block.type = Material.AIR
      if (player.gameMode == GameMode.SURVIVAL) {
        val droppedStack = ItemStack(plugin.coloredSignManager.coloredSignStacks[block.type]!!)
        droppedStack.amount = 1
        block.world.dropItemNaturally(block.location, droppedStack)
      }
      plugin.modelManager.signLocationDao.delete(block)
      event.isCancelled = true
    }
  }
}
