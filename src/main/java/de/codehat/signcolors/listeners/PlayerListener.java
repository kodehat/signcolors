/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.listeners;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.updater.Updater;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This listener only listens for player specific events.
 */
public class PlayerListener extends PluginListener {

    public PlayerListener(SignColors plugin) {
        super(plugin);
    }

    /**
     * Sends the update message upon joining to players with permission.
     *
     * @param event PlayerJoinEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.getPlugin().isUpdateAvailable() && player.hasPermission("signcolors.updatemsg")) {
            Message.sendWithLogo(player, this.getPlugin().getStr("UPDATEMSG")
                    + " (v" + this.getPlugin().getNewerVersion() + "):");
            Message.send(player, "&2" + Updater.getSpigotUrl());
        }
    }

    /**
     * Remove the left player from the list.
     * Actually the player can't be in the map,
     * but just to make sure he is really removed.
     *
     * @param event PlayerQuitEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (this.getPlugin().signPlayers.contains(player)) {
            this.getPlugin().signPlayers.remove(player);
        }
    }

    /**
     * [SC] sign use event.
     *
     * @param event PlayerInteractEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase(Message.replaceColors("&6[&3SC&6]"))) {
                    if (player.hasPermission("signcolors.sign.use")) {
                        // [0] = sign amount; [1] sign price for the specified amount
                        String[] signData = ChatColor.stripColor(sign.getLine(2)).split(":");

                        // Check if the amount and the price on the sign is valid
                        if (!Utils.isInteger(signData[0].trim()) || !Utils.isDouble(signData[1].trim())) {
                            Message.sendWithLogo(player, this.getPlugin().getStr("INCORRECTFORMAT"));
                            return;
                        }

                        // Get amount and price
                        int amount = Integer.valueOf(signData[0].trim());
                        Double price = Double.valueOf(signData[1].trim());

                        // Check if the player has enough money to buy signs
                        if (SignColors.getEconomy().getBalance(player) < price) {
                            Message.send(player, this.getPlugin().getStr("NOTENMONEY"));
                            return;
                        }

                        // Check if the inventory of the player is not full
                        if (player.getInventory().firstEmpty() == -1) {
                            Message.send(player, this.getPlugin().getStr("NOTENSPACE"));
                            return;
                        }

                        // Withdraw the costs for the signs
                        SignColors.getEconomy().withdrawPlayer(player, price);

                        // Copy the ItemStack and set the defined amount
                        ItemStack signs = new ItemStack(this.getPlugin().getSignManager().coloredSignStack);
                        signs.setAmount(amount);

                        // Add the signs to the player's inventory and update it
                        player.getInventory().addItem(signs);
                        player.updateInventory();

                        // Play the success sound and send a message to the player
                        player.playSound(player.getLocation(),
                                Sound.valueOf(this.getPlugin().getConfig().getString("sounds.getsigns_scsign.type")),
                                (float) this.getPlugin().getConfig().getDouble("sounds.getsigns_scsign.volume"),
                                (float) this.getPlugin().getConfig().getDouble("sounds.getsigns_scsign.pitch"));
                        Message.sendWithLogo(player, "&c-&6" + SignColors.getEconomy().format(price)
                                + " &a--->>>&6 "
                                + SignColors.getEconomy().format(SignColors.getEconomy().getBalance(player)));
                        Message.sendWithLogo(player, this.getPlugin().getStr("SIGNMSG") + amount
                                + this.getPlugin().getStr("SIGNMSGB"));
                    } else {
                        Message.sendWithLogo(player, this.getPlugin().getStr("NOACTION"));
                    }
                }
            }
        }
    }

}
