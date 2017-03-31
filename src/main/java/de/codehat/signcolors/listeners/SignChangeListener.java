/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.listeners;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * This listener only listens for sign specific events.
 */
public class SignChangeListener extends PluginListener {

    public SignChangeListener(SignColors plugin) {
        super(plugin);
    }

    /**
     * Creates a colored sign.
     *
     * @param event SignChangeEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (this.getPlugin().isSigncrafting()) {
            if (!player.hasPermission("signcolors.craftsign.bypass")) {
                if (itemInMainHand.getType() == Material.AIR) {
                    if (this.getPlugin().signPlayers.contains(player)) {
                        if (this.getPlugin().signPlayers.contains(player)) this.getPlugin().signPlayers.remove(player);
                        setSignColors(event, player);
                        Block block = event.getBlock();
                        this.getPlugin().getPluginDatabase().addSign(block.getLocation());
                    }
                } else {
                    if (itemInMainHand.getItemMeta().hasLore()) {
                        if (this.getPlugin().signPlayers.contains(player)) this.getPlugin().signPlayers.remove(player);
                        setSignColors(event, player);
                        Block block = event.getBlock();
                        this.getPlugin().getPluginDatabase().addSign(block.getLocation());
                    }
                }
            } else {
                setSignColors(event, player);
            }
        } else {
            setSignColors(event, player);
        }
    }

    /**
     * SC sign creation event.
     *
     * @param event SignChangeEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler
    public void createSignColorsSign(SignChangeEvent event) {
        Player player = event.getPlayer();
        if ((event.getLine(0).equalsIgnoreCase("[SignColors]"))) {
            if (player.hasPermission("signcolors.sign.create")) {
                // Check if line is not empty and contains the sign amount and the price splitted by a ":"
                if (!event.getLine(1).isEmpty() && event.getLine(1).contains(":")) {
                    // [0] = sign amount; [1] sign price for the specified amount
                    String[] signData = event.getLine(1).split(":");

                    // Check if entered types are valid for Integer and Double
                    if (!Utils.isInteger(signData[0].trim()) || !Utils.isDouble(signData[1].trim())) {
                        Message.sendWithLogo(player, this.getPlugin().getStr("INCFORMATSIGN"));
                        event.setCancelled(true);
                        return;
                    }

                    // Get amount and price
                    int amount = Integer.valueOf(signData[0].trim());
                    Double price = Double.valueOf(signData[1].trim());

                    // Return if amount or price 0 or lower
                    if (amount <= 0 || price <= 0) {
                        Message.sendWithLogo(player, this.getPlugin().getStr("PRICEAMOUNTTOLOW"));
                        event.setCancelled(true);
                        return;
                    }

                    // Set sign lines
                    this.setSignColorsSign(event, amount, price);

                    // Play success (anvil) sound
                    //TODO: Set sound in config!!!
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 0.75F, 1F);

                } else {
                    // Set sign lines with default values
                    this.setSignColorsSign(event, this.getPlugin().getConfig().getInt("signamount.sc_sign"),
                            this.getPlugin().getConfig().getDouble("price"));

                    // Play success (anvil) sound
                    //TODO: Set sound in config!!!
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 0.75F, 1F);
                }
            } else {
                Message.send(player, this.getPlugin().getStr("NOACTION"));
            }
        }
    }

    /**
     * Sets the lines on a SC sign.
     *
     * @param event  SignChangeEvent to get the sign.
     * @param amount Amount of colored signs.
     * @param price  Price of colored signs.
     */
    private void setSignColorsSign(SignChangeEvent event, int amount, Double price) {
        // Set sign lines
        event.setLine(0, Message.replaceColors("&6[&3SC&6]"));
        event.setLine(1, Message.replaceColors(this.getPlugin().getStr("SLTWO")));
        event.setLine(2, Message.replaceColors(String.format("&8%s : %s", String.valueOf(amount),
                String.valueOf(price))));
        event.setLine(3, Message.replaceColors(this.getPlugin().getStr("SLONE")));
    }

    /**
     * Checks the first line of the sign and prevents creation if necessary.
     *
     * @param event SignChangeEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void checkFirstLine(SignChangeEvent event) {
        @SuppressWarnings("unchecked")
        List<String> blockedLines = (List<String>) this.getPlugin().getConfig().getList("blocked_firstlines");
        SignColors.info(event.getLine(0).trim());
        for (String blocked_line : blockedLines) {
            SignColors.info(Message.replaceColors(blocked_line));
            if (event.getLine(0).trim().equals(Message.replaceColors(blocked_line))
                    && !event.getPlayer().hasPermission("signcolors.blockedfirstlines.bypass")) {
                Message.sendWithLogo(event.getPlayer(), this.getPlugin().getStr("NOTALLFL"));
                event.setCancelled(true);
            }
        }
    }

    /**
     * Does the 'magic' to make a sign colored.
     *
     * @param event SignChangeEvent.
     * @param player Player writing on the sign.
     */
    private void setSignColors(SignChangeEvent event, Player player) {
        String colorChar = this.getPlugin().getConfig().getString("colorsymbol");
        for (int line = 0; line < 4; line++) {
            if (event.getLine(line).isEmpty() || !event.getLine(line).contains(colorChar)) continue;
            String[] colorline = event.getLine(line).split(colorChar);
            String newline = "";
            if (colorline.length > 0) newline = colorline[0];
            for (String partline : colorline) {
                int color;
                if (partline.equals(colorline[0])) continue;
                if (partline.length() == 0
                        || SignColors.ALL_COLOR_CODES.indexOf(partline.toLowerCase().charAt(0)) == -1) {
                    newline += colorChar + partline;
                    continue;
                }
                if ((color = SignColors.ALL_COLOR_CODES.indexOf(partline.toLowerCase().charAt(0))) == -1
                        || !checkColorPermissions(player, color)) {
                    newline += colorChar + partline;
                } else {
                    String newpartline = colorChar + partline;
                    newline += Message.replaceColors(colorChar.charAt(0), newpartline);
                }
            }
            event.setLine(line, newline);
        }
    }

    /**
     * Checks the player's permissions for creating a colored sign.
     *
     * @param player     Player to check.
     * @param color Color to check.
     * @return true if player has permissions, false if not.
     */
    private static boolean checkColorPermissions(Player player, int color) {
        char col = SignColors.ALL_COLOR_CODES.charAt(color);
        return (color == 0) || player.hasPermission("signcolors.color." + col)
                || player.hasPermission("signcolors.*") || player.hasPermission("signcolors.colors");
    }
}