/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.commands;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.database.SQLite;
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

/**
 * This class represents the '/sc upgrade' command.
 */
public class UpgradeCommand extends AbstractCommand {

    public UpgradeCommand(SignColors plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("signcolors.upgrade")) {
            Message.sendWithLogo(sender, this.getPlugin().getStr("NOCMDACCESS"));
            return;
        }
        final File old_db = new File(this.getPlugin().getDataFolder().getAbsolutePath() + File.separator
                + "data" + File.separator + "signs.db");
        if (!old_db.exists()) {
            Message.sendWithLogo(sender, this.getPlugin().getStr("OLDDBMISS"));
            return;
        }
        Message.sendWithLogo(sender, this.getPlugin().getStr("IMPORTSTART"));
        this.getPlugin().getServer().getScheduler().runTaskAsynchronously(this.getPlugin(), () -> {
            SQLite sqlite = new SQLite(this.getPlugin(), "data" + File.separator + "signs.db");
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
                    Location l = new Location(this.getPlugin().getServer().getWorld(parts[0]),
                            Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                    Block b = this.getPlugin().getServer().getWorld(parts[0]).getBlockAt(l);
                    if (b.getType().equals(Material.SIGN) || b.getType().equals(Material.SIGN_POST)
                            || b.getType().equals(Material.WALL_SIGN)) {
                        this.getPlugin().getPluginDatabase().addSign(l);
                        locations++;
                    } else {
                        fails++;
                    }
                }
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            File rename_file = new File(this.getPlugin().getDataFolder().toPath().toString() + File.separator
                    + "data" + File.separator + "signs.db.imported");
            if (!old_db.renameTo(rename_file))
                this.getPlugin().getLogger().warning("Could not rename old database file!");
            Message.sendWithLogo(sender, String.format(this.getPlugin().getStr("IMPORTFINISH"),
                    String.valueOf(fails), String.valueOf(locations)));
        });
    }
}
