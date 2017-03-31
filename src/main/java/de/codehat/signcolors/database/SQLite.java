/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.database;

import de.codehat.signcolors.SignColors;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLite extends Database {

    private String databaseLocation;
    private Connection connection;

    /**
     * Represents the connection to a SQLite database.
     *
     * @param plugin     The plugin instance.
     * @param databaseLocation Location of the SQLite database.
     */
    public SQLite(Plugin plugin, String databaseLocation) {
        super(plugin, DatabaseType.SQLITE);
        this.databaseLocation = databaseLocation;
    }

    @Override
    public Connection openConnection() {
        File file = new File(this.plugin.getDataFolder().getAbsolutePath() + File.separator
                + databaseLocation);
        if (!(file.exists())) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                SignColors.logError("Unable to create SQLite database!");
                SignColors.logError("Disabling this plugin until problem has been fixed!");
                exception.printStackTrace();
                this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.plugin.getDataFolder().getAbsolutePath()
                    + File.separator + databaseLocation);
        } catch (SQLException exception) {
            SignColors.logError("Can't connect to SQLite database!");
            SignColors.logError("Disabling this plugin until problem has been fixed!");
            exception.printStackTrace();
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        } catch (ClassNotFoundException exception) {
            SignColors.logError("JDBC Driver not found!");
            SignColors.logError("Disabling this plugin until problem has been fixed!");
            exception.printStackTrace();
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }
        return connection;
    }

    @Override
    public boolean checkConnection() {
        try {
            return !(connection.isClosed());
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException exception) {
                plugin.getLogger().log(Level.SEVERE, "Error closing the SQLite Connection!");
                exception.printStackTrace();
            }
        }
    }

}