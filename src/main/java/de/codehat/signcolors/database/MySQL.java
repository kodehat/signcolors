/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.database;

import de.codehat.signcolors.SignColors;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Database {

    private Connection connection;
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    /**
     * Represents the connection to a MySQL database.
     *
     * @param plugin Plugin instance.
     */
    public MySQL(Plugin plugin, String host, String port, String database, String username, String password) {
        super(plugin, DatabaseType.MYSQL);
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection openConnection() {
        if (this.connection == null || !checkConnection()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/"
                        + this.database, this.username, this.password);
                SignColors.info("Successfully opened MySQL connection.");
            } catch (SQLException exception) {
                SignColors.logError("Could not connect to MySQL server! Are your credentials correct?");
                SignColors.logError("Disabling this plugin until problem has been fixed!");
                exception.printStackTrace();
                this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
            }
        }
        return this.connection;
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
                SignColors.logError("Error occured while closing the MySQL connection!");
                exception.printStackTrace();
            }
        }
    }
}
