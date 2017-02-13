/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.database;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Database {

    private Connection connection_;
    private String host_;
    private String port_;
    private String database_;
    private String username_;
    private String password_;

    /**
     * Creates a new Database
     *
     * @param plugin Plugin instance
     */
    public MySQL(Plugin plugin, String host, String port, String database, String username, String password) {
        super(plugin);
        this.host_ = host;
        this.port_ = port;
        this.database_ = database;
        this.username_ = username;
        this.password_ = password;
    }

    @Override
    public Connection openConnection() {
        if (this.connection_ == null || !checkConnection()) {
            try {
                connection_ = DriverManager.getConnection("jdbc:mysql://" + this.host_ + ":" + this.port_ + "/"
                        + this.database_, this.username_, this.password_);
                this.plugin.getLogger().info("Successfully opened MySQL connection.");
            } catch (SQLException e) {
                this.plugin.getLogger().warning("Could not connect to MySQL server! Are your credentials correct?");
                e.printStackTrace();
            }
        }
        return this.connection_;
    }

    @Override
    public boolean checkConnection() {
        try {
            return !(connection_.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return connection_;
    }

    @Override
    public void closeConnection() {
        if (connection_ != null) {
            try {
                connection_.close();
                connection_ = null;
            } catch (SQLException e) {
                plugin.getLogger().warning("Error closing the MySQL connection!");
                e.printStackTrace();
            }
        }
    }
}
