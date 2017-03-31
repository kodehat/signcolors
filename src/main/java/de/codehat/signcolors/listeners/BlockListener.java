/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.listeners;

import de.codehat.signcolors.SignColors;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This listener only listens for block specific events.
 */
public class BlockListener extends PluginListener {

    public BlockListener(SignColors plugin) {
        super(plugin);
    }

    /**
     * Fixes the NPE on creating a colored sign with an amount of 1 sign in the player's inventory.
     *
     * @param event BlockPlaceEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlacingOneSign(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand != null && this.getPlugin().isSigncrafting()
                && !player.hasPermission("signcolors.craftsign.bypass")) {
            if (itemInMainHand.getAmount() == 1 && itemInMainHand.getType() == Material.SIGN
                    && itemInMainHand.getItemMeta().hasLore()) {
                this.getPlugin().signPlayers.add(player);
            }
        }
    }

    /**
     * Drops a colored sign if signcrafting is enabled and block is available.
     *
     * @param event BlockBreakEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBreakColoredSign(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (this.getPlugin().isSigncrafting() && (block.getType() == Material.SIGN_POST
                || block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN)
                && this.getPlugin().getPluginDatabase().checkSign(block.getLocation())) {
            this.getPlugin().getPluginDatabase().deleteSign(block.getLocation());
            block.setType(Material.AIR);
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                block.getWorld().dropItemNaturally(block.getLocation(),
                        new ItemStack(this.getPlugin().getSignManager().getSign(1)));
            }
            event.setCancelled(true);
        }
    }
}