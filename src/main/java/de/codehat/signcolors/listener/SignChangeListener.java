/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.listener;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SignChangeListener implements Listener {

    // Instances
    private SignColors plugin;
    private LanguageLoader lang;

    /**
     * Constructor.
     *
     * @param instance SignColors instance.
     * @param lang     LanguageLoader instance.
     */
    public SignChangeListener(SignColors instance, LanguageLoader lang) {
        this.plugin = instance;
        this.lang = lang;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    /**
     * Creates a colored sign.
     *
     * @param e SignChangeEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (this.plugin.isSignCrafting) {
            if (!p.hasPermission("signcolors.craftsign.bypass")) {
                if (i.getType() == Material.AIR) {
                    if (this.plugin.signPlayers.contains(p)) {
                        if (this.plugin.signPlayers.contains(p)) this.plugin.signPlayers.remove(p);
                        setSignColors(e, p);
                        Block b = e.getBlock();
                        this.plugin.getPluginDatabase().addSign(b.getLocation());
                    }
                } else {
                    if (i.getItemMeta().hasLore()) {
                        if (this.plugin.signPlayers.contains(p)) this.plugin.signPlayers.remove(p);
                        setSignColors(e, p);
                        Block b = e.getBlock();
                        this.plugin.getPluginDatabase().addSign(b.getLocation());
                    }
                }
            } else {
                setSignColors(e, p);
            }
        } else {
            setSignColors(e, p);
        }
    }

    /**
     * SC sign creation event.
     *
     * @param e SignChangeEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void createSignColorsSign(SignChangeEvent e) {
        Player p = e.getPlayer();
        if ((e.getLine(0).equalsIgnoreCase("[SignColors]"))) {
            if (p.hasPermission("signcolors.sign.create")) {
                // Check if line is not empty and contains the sign amount and the price splitted by a ":".
                if (!e.getLine(1).isEmpty() && e.getLine(1).contains(":")) {
                    // [0] = sign amount; [1] sign price for the specified amount.
                    String[] sign_data = e.getLine(1).split(":");

                    // Check if entered types are valid for Integer and Double.
                    if (!Utils.isInteger(sign_data[0].trim()) || !Utils.isDouble(sign_data[1].trim())) {
                        Message.sendLogoMsg(p, "&cIncorrect format of sign amount or price!");
                        return;
                    }

                    // Get amount and price.
                    int amount = Integer.valueOf(sign_data[0].trim());
                    Double price = Double.valueOf(sign_data[1].trim());

                    // Set sign lines.
                    this.setSignColorsSign(e, amount, price);

                    // Play success (anvil) sound.
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 0.75F, 1F);

                } else {
                    // Set sign lines with default values.
                    this.setSignColorsSign(e, this.plugin.getConfig().getInt("signamount.sc_sign"),
                            this.plugin.getConfig().getDouble("price"));

                    // Play success (anvil) sound.
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 0.75F, 1F);
                }
            } else {
                Message.sendMsg(p, lang.getLang("noaction"));
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
        // Set sign lines.
        event.setLine(0, Message.replaceColors("&6[&3SC&6]"));
        event.setLine(1, Message.replaceColors(lang.getLang("sltwo")));
        event.setLine(2, Message.replaceColors(String.format("&8%s : %s", String.valueOf(amount),
                String.valueOf(price))));
        event.setLine(3, Message.replaceColors(lang.getLang("slone")));
    }

    /**
     * Checks the first line of the sign and prevents creation if needed.
     *
     * @param e SignChangeEvent.
     */
    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGH)
    public void checkFirstLine(SignChangeEvent e) {
        @SuppressWarnings("unchecked")
        List<String> blocked_lines = (List<String>) this.plugin.getConfig().getList("blocked_firstlines");
        for (String blocked_line : blocked_lines) {
            if (e.getLine(0).trim().contains(Message.replaceColors(blocked_line))
                    && !e.getPlayer().hasPermission("signcolors.blockedfirstlines.bypass")) {
                Message.sendLogoMsg(e.getPlayer(), lang.getLang("notallfl"));
                e.setCancelled(true);
            }
        }
    }

    /**
     * Does the 'magic' to make a sign colored.
     *
     * @param e SignChangeEvent.
     * @param p Player writing on the sign.
     */
    private void setSignColors(SignChangeEvent e, Player p) {
        String colorChar = this.plugin.getConfig().getString("colorsymbol");
        for (int line = 0; line < 4; line++) {
            if (e.getLine(line).isEmpty() || !e.getLine(line).contains(colorChar)) continue;
            String[] colorline = e.getLine(line).split(colorChar);
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
                        || !checkColorPermissions(p, color)) {
                    newline += colorChar + partline;
                } else {
                    String newpartline = colorChar + partline;
                    newline += Message.replaceColors(colorChar.charAt(0), newpartline);
                }
            }
            e.setLine(line, newline);
        }
    }

    /**
     * Checks the player's permissions for creating a colored sign.
     *
     * @param p     Player to check.
     * @param color Color to check.
     * @return true if player has permissions, false if not.
     */
    private static boolean checkColorPermissions(Player p, int color) {
        char col = SignColors.ALL_COLOR_CODES.charAt(color);
        return (color == 0) || p.hasPermission("signcolors.color." + col) || p.hasPermission("signcolors.*")
                || p.hasPermission("signcolors.colors");
    }
}