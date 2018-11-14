package de.codehat.signcolors.listener

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.permission.Permissions
import de.codehat.signcolors.util.hasPermission
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack

class BlockListener: Listener {

    @Suppress("unused")
    @EventHandler
    fun onPlacingOneSign(event: BlockPlaceEvent) {
        val player = event.player
        val itemInMainHand = player.inventory.itemInMainHand

        if (itemInMainHand != null && SignColors.instance.coloredSignManager.signCrafting
            && !player.hasPermission(Permissions.BYPASS_SIGN_CRAFTING)) {
            if (itemInMainHand.amount == 1 && itemInMainHand.type == Material.SIGN
                && itemInMainHand.itemMeta.hasLore()) {
                SignColors.instance.fixSignPlayers.add(player)
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onBreakColoredSign(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        if (SignColors.instance.coloredSignManager.signCrafting
                && (block.type == Material.WALL_SIGN || block.type == Material.SIGN)
                && SignColors.instance.signLocationDao.exists(block)) {
            block.type = Material.AIR
            if (player.gameMode == GameMode.SURVIVAL) {
                val droppedStack = ItemStack(SignColors.instance.coloredSignManager.coloredSignStack)
                droppedStack.amount = 1
                block.world.dropItemNaturally(block.location, droppedStack)
            }
            SignColors.instance.signLocationDao.delete(block)
            event.isCancelled = true
        }
    }

}
