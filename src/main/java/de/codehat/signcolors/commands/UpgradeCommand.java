/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.database.SQLite;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.util.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpgradeCommand extends BaseCommand {

    public UpgradeCommand(SignColors plugin, LanguageLoader lang) {
        super(plugin, lang);
    }

    @Override
    public void onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.upgrade")) {
            Message.sendLogoMsg(sender, lang.getLang("nocmd"));
            return;
        }
        final File old_db = new File(this.plugin.getDataFolder().toPath().toString() + File.separator + "data" + File.separator
                + "signs.db");
        if (!old_db.exists()) {
            Message.sendLogoMsg(sender, "&cCannot import old data, because database file 'signs.db' is missing!");
            return;
        }
        Message.sendLogoMsg(sender, "&aStarting import and check of all old sign locations...");
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
            @Override
            public void run() {
                SQLite sqlite = new SQLite(plugin, "data" + File.separator + "signs.db");
                Connection connection = sqlite.openConnection();
                int locations = 0;
                int fails = 0;
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM signs");
                    ResultSet rs  = ps.executeQuery();
                    String location;
                    while (rs.next()) {
                        location = rs.getString("location");
                        String[] parts = location.split(",");
                        Location l = new Location(plugin.getServer().getWorld(parts[0]), Double.parseDouble(parts[1]),
                                Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                        Block b = plugin.getServer().getWorld(parts[0]).getBlockAt(l);
                        if (b.getType().equals(Material.SIGN) || b.getType().equals(Material.SIGN_POST)
                                || b.getType().equals(Material.WALL_SIGN)) {
                            plugin.addSign(l);
                            locations++;
                        } else {
                            fails++;
                        }
                    }
                    connection.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                File rename_file = new File(plugin.getDataFolder().toPath().toString() + File.separator + "data" + File.separator
                        + "signs.db.imported");
                if (!old_db.renameTo(rename_file)) plugin.getLogger().warning("Could not rename old database file!");
                Message.sendLogoMsg(sender, String.format("&aRemoved &6%s &ainvalid sign locations and imported &6%s &asign locations from the old database.",
                        String.valueOf(fails), String.valueOf(locations)));
            }
        });
    }
}
