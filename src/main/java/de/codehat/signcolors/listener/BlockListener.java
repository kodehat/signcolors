/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.listener;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoader;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {

    // Instances
    private SignColors plugin_;
    private LanguageLoader lang_;

    /**
     * Constructor.
     *
     * @param instance SignColors instance.
     * @param lang     LanguageLoader instance.
     */
    public BlockListener(SignColors instance, LanguageLoader lang) {
        this.plugin_ = instance;
        this.lang_ = lang;
        this.plugin_.getServer().getPluginManager().registerEvents(this, this.plugin_);
    }

    /**
     * Fixes the NPE on creating a colored sign with an amount of 1x sign in the player's inventory.
     *
     * @param e BlockPlaceEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onLastSignFix(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i != null && this.plugin_.isSignCrafting && !p.hasPermission("signcolors.craftsign.bypass")) {
            if (i.getAmount() == 1 && i.getType() == Material.SIGN && i.getItemMeta().hasLore()) {
                this.plugin_.signPlayers.add(p);
            }
        }
    }

    /**
     * Drops a colored sign if signcrafting is enabled and block is available.
     *
     * @param e BlockBreakEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBreakColoredSign(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (this.plugin_.isSignCrafting && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN
                || b.getType() == Material.SIGN) && this.plugin_.getPluginDatabase().checkSign(b.getLocation())) {
            this.plugin_.getPluginDatabase().deleteSign(b.getLocation());
            b.setType(Material.AIR);
            if (p.getGameMode().equals(GameMode.SURVIVAL)) {
                b.getWorld().dropItemNaturally(b.getLocation(),
                        new ItemStack(this.plugin_.getSignManager().getSign(1)));
            }
            e.setCancelled(true);
        }
    }
}