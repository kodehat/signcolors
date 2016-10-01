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
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static de.codehat.signcolors.SignColors.eco;

public class ColoredSignListener implements Listener {

    //Instances
    private SignColors plugin;
    private LanguageLoader lang;

    /**
     * Constructor.
     *
     * @param instance SignColors instance.
     * @param lang     LanguageLoading instance.
     */
    public ColoredSignListener(SignColors instance, LanguageLoader lang) {
        this.plugin = instance;
        this.lang = lang;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    /**
     * Fixes the NPE on creating a colored sign with an amount of 1x sign in the player's inventory.
     *
     * @param e BlockPlaceEvent.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onLastSignFix(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i != null && this.plugin.signcrafting && !p.hasPermission("signcolors.craftsign.bypass")) {
            if (i.getAmount() == 1 && i.getType() == Material.SIGN && i.getItemMeta().hasLore()) {
                this.plugin.sign_players.add(p);
            }
        }
    }

    /**
     * Remove the left player from the list.
     * Actually the player cannot be in the Map,
     * but just to make sure he is really removed.
     *
     * @param e PlayerQuitEvent.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (this.plugin.sign_players.contains(p)) {
            this.plugin.sign_players.remove(p);
        }
    }

    /**
     * Drops a colored sign if signcrafting is enabled and block is available.
     *
     * @param e BlockBreakEvent.
     */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBreakColoredSign(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (this.plugin.signcrafting && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN
                || b.getType() == Material.SIGN) && this.plugin.checkSign(b.getLocation())) {
            this.plugin.deleteSign(b.getLocation());
            b.setType(Material.AIR);
            if (p.getGameMode().equals(GameMode.SURVIVAL)) {
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(this.plugin.getSign(1)));
            }
            e.setCancelled(true);
        }
    }

    /**
     * Creates a colored sign.
     *
     * @param e SignChangeEvent.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (this.plugin.signcrafting) {
            if (!p.hasPermission("signcolors.craftsign.bypass")) {
                if (i.getType() == Material.AIR) {
                    if (this.plugin.sign_players.contains(p)) {
                        if (this.plugin.sign_players.contains(p)) this.plugin.sign_players.remove(p);
                        makeColors(e, p);
                        Block b = e.getBlock();
                        this.plugin.addSign(b.getLocation());
                    }
                } else {
                    if (i.getItemMeta().hasLore()) {
                        if (this.plugin.sign_players.contains(p)) this.plugin.sign_players.remove(p);
                        makeColors(e, p);
                        Block b = e.getBlock();
                        this.plugin.addSign(b.getLocation());
                    }
                }
            } else {
                makeColors(e, p);
            }
        } else {
            makeColors(e, p);
        }
    }

    /**
     * [SC] sign creation event.
     *
     * @param e SignChangeEvent.
     */
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
                            this.plugin.getConfig().getDouble("signamount.sc_sign"));

                    // Play success (anvil) sound.
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 0.75F, 1F);
                }
            } else {
                Message.sendMsg(p, lang.getLang("noaction"));
            }
        }
    }

    private void setSignColorsSign(SignChangeEvent event, int amount, Double price) {
        // Set sign lines.
        event.setLine(0, Message.replaceColors("&6[&3SC&6]"));
        event.setLine(1, "&8Amount : Price");
        event.setLine(2, Message.replaceColors(String.format("&b%s &7: &b%s", String.valueOf(amount), String.valueOf(price))));
        event.setLine(3, Message.replaceColors(lang.getLang("slone")));
    }

    /**
     * [SC] sign use event.
     *
     * @param e PlayerInteractEvent.
     */
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
                            Message.sendLogoMsg(p, "&cThis sign is incorrectly formatted! Contact your admin.");
                            return;
                        }

                        // Get amount and price.
                        int amount = Integer.valueOf(sign_data[0].trim());
                        Double price = Double.valueOf(sign_data[1].trim());

                        // Check if the player has enough money to buy signs.
                        if (eco.getBalance(p) < price) {
                            Message.sendMsg(p, lang.getLang("notenmoney"));
                            return;
                        }

                        // Check if the inventory of the player is not full.
                        if (p.getInventory().firstEmpty() == -1) {
                            Message.sendMsg(p, lang.getLang("notenspace"));
                            return;
                        }

                        // Withdraw the costs for the signs.
                        eco.withdrawPlayer(p, price);

                        // Copy the ItemStack and set the defined amount.
                        ItemStack signs = new ItemStack(this.plugin.i);
                        signs.setAmount(amount);

                        // Add the signs to the player's inventory and update it.
                        p.getInventory().addItem(signs);
                        p.updateInventory();

                        // Play the success sound (anvil) and send a message to the player.
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.75F, 1F);
                        Message.sendMsg(p, "&6-" + eco.format(price) + " &a--->>>&6 "
                                + eco.format(eco.getBalance(p)));
                        Message.sendMsg(p, lang.getLang("signmsg") + amount + lang.getLang("signmsgb"));
                    } else {
                        Message.sendMsg(p, lang.getLang("noaction"));
                    }
                }
            }
        }
    }

    /**
     * Checks the first line of the sign and prevents creation if needed.
     *
     * @param e SignChangeEvent.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void checkFirstLine(SignChangeEvent e) {
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
     * Sends the updatemessage on server join to players with permission.
     *
     * @param e PlayerJoinEvent.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (this.plugin.updatePlayerMsg && p.hasPermission("signcolors.updatemsg")) {
            Message.sendMsg(p, lang.getLang("updatemsg") + " (" + this.plugin.updateVersion + "):");
            Message.sendMsg(p, "&2" + this.plugin.updateLink);
        }
    }

    /**
     * Does the 'magic' to make a sign colored.
     *
     * @param e SignChangeEvent.
     * @param p Player writing on the sign.
     */
    private void makeColors(SignChangeEvent e, Player p) {
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
    private boolean checkColorPermissions(Player p, int color) {
        char col = SignColors.ALL_COLOR_CODES.charAt(color);
        return (color == 0) || p.hasPermission("signcolors.color." + col) || p.hasPermission("signcolors.*")
                || p.hasPermission("signcolors.colors");
    }
}