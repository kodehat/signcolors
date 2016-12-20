/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.listener;

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

public class PlayerListener implements Listener {

    // Instances
    private SignColors plugin;
    private LanguageLoader lang;

    /**
     * Constructor.
     *
     * @param instance SignColors instance.
     * @param lang     LanguageLoader instance.
     */
    public PlayerListener(SignColors instance, LanguageLoader lang) {
        this.plugin = instance;
        this.lang = lang;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
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
        if (this.plugin.sendUpdateMsgToPlayer && p.hasPermission("signcolors.updatemsg")) {
            Message.sendMsg(p, lang.getLang("updatemsg") + " (" + this.plugin.updateVersion + "):");
            Message.sendMsg(p, "&2" + this.plugin.updateLink);
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
        if (this.plugin.signPlayers.contains(p)) {
            this.plugin.signPlayers.remove(p);
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
                            Message.sendLogoMsg(p, lang.getLang("incorrectformat"));
                            return;
                        }

                        // Get amount and price.
                        int amount = Integer.valueOf(sign_data[0].trim());
                        Double price = Double.valueOf(sign_data[1].trim());

                        // Check if the player has enough money to buy signs.
                        if (SignColors.ECONOMY.getBalance(p) < price) {
                            Message.sendMsg(p, lang.getLang("notenmoney"));
                            return;
                        }

                        // Check if the inventory of the player is not full.
                        if (p.getInventory().firstEmpty() == -1) {
                            Message.sendMsg(p, lang.getLang("notenspace"));
                            return;
                        }

                        // Withdraw the costs for the signs.
                        SignColors.ECONOMY.withdrawPlayer(p, price);

                        // Copy the ItemStack and set the defined amount.
                        ItemStack signs = new ItemStack(this.plugin.getSignManager().coloredSignStack);
                        signs.setAmount(amount);

                        // Add the signs to the player's inventory and update it.
                        p.getInventory().addItem(signs);
                        p.updateInventory();

                        // Play the success sound (anvil) and send a message to the player.
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.75F, 1F);
                        Message.sendMsg(p, "&6-" + SignColors.ECONOMY.format(price) + " &a--->>>&6 "
                                + SignColors.ECONOMY.format(SignColors.ECONOMY.getBalance(p)));
                        Message.sendMsg(p, lang.getLang("signmsg") + amount
                                + lang.getLang("signmsgb"));
                    } else {
                        Message.sendMsg(p, lang.getLang("noaction"));
                    }
                }
            }
        }
    }

}
