/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.listeners;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener extends PluginListener {

    public PlayerListener(SignColors plugin) {
        super(plugin);
    }

    /**
     * Sends the updatemessage on server join to players with permission.
     *
     * @param e PlayerJoinEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (this.getPlugin().sendUpdateMsgToPlayer && p.hasPermission("signcolors.updatemsg")) {
            Message.send(p, lang.getLang("updatemsg") + " (" + this.getPlugin().updateVersion + "):");
            Message.send(p, "&2" + this.getPlugin().updateLink);
        }
    }

    /**
     * Remove the left player from the list.
     * Actually the player cannot be in the Map,
     * but just to make sure he is really removed.
     *
     * @param e PlayerQuitEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (this.getPlugin().signPlayers.contains(p)) {
            this.getPlugin().signPlayers.remove(p);
        }
    }

    /**
     * [SC] sign use event.
     *
     * @param e PlayerInteractEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                Sign s = (Sign) e.getClickedBlock().getState();
                if (s.getLine(0).equalsIgnoreCase(Message.replaceColors("&6[&3SC&6]"))) {
                    if (p.hasPermission("signcolors.sign.use")) {
                        // [0] = sign amount; [1] sign price for the specified amount.
                        String[] sign_data = ChatColor.stripColor(s.getLine(2)).split(":");

                        // Check if the amount and the price on the sign is valid.
                        if (!Utils.isInteger(sign_data[0].trim()) || !Utils.isDouble(sign_data[1].trim())) {
                            Message.sendWithLogo(p, lang.getLang("incorrectformat"));
                            return;
                        }

                        // Get amount and price.
                        int amount = Integer.valueOf(sign_data[0].trim());
                        Double price = Double.valueOf(sign_data[1].trim());

                        // Check if the player has enough money to buy signs.
                        if (SignColors.getEconomy().getBalance(p) < price) {
                            Message.send(p, lang.getLang("notenmoney"));
                            return;
                        }

                        // Check if the inventory of the player is not full.
                        if (p.getInventory().firstEmpty() == -1) {
                            Message.send(p, lang.getLang("notenspace"));
                            return;
                        }

                        // Withdraw the costs for the signs.
                        SignColors.getEconomy().withdrawPlayer(p, price);

                        // Copy the ItemStack and set the defined amount.
                        ItemStack signs = new ItemStack(this.getPlugin().getSignManager().coloredSignStack);
                        signs.setAmount(amount);

                        // Add the signs to the player's inventory and update it.
                        p.getInventory().addItem(signs);
                        p.updateInventory();

                        // Play the success sound (anvil) and send a message to the player.
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.75F, 1F);
                        Message.send(p, "&6-" + SignColors.getEconomy().format(price) + " &a--->>>&6 "
                                + SignColors.getEconomy().format(SignColors.getEconomy().getBalance(p)));
                        Message.send(p, lang.getLang("signmsg") + amount
                                + lang.getLang("signmsgb"));
                    } else {
                        Message.send(p, lang.getLang("noaction"));
                    }
                }
            }
        }
    }

}
