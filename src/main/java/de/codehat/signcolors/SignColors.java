/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors;

import de.codehat.signcolors.commands.*;
import de.codehat.signcolors.database.Database;
import de.codehat.signcolors.database.MySQL;
import de.codehat.signcolors.database.SQLite;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.listener.BlockListener;
import de.codehat.signcolors.listener.PlayerListener;
import de.codehat.signcolors.listener.SignChangeListener;
import de.codehat.signcolors.logger.PluginLogger;
import de.codehat.signcolors.manager.BackupManager;
import de.codehat.signcolors.manager.SignManager;
import de.codehat.signcolors.updater.UpdateCallback;
import de.codehat.signcolors.updater.UpdateResult;
import de.codehat.signcolors.updater.Updater;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SignColors extends JavaPlugin implements Listener {

    /*
     * ---------------------------------------------------------
     *  Static/Final Variables
     * ---------------------------------------------------------
     */

    // All available colorcodes.
    public static final String ALL_COLOR_CODES = "0123456789abcdefklmnor";

    // Vault support.
    public static Economy ECONOMY;

    /*
     * ---------------------------------------------------------
     *  Public Variables
     * ---------------------------------------------------------
     */

    // The language FileConfiguration.
    public FileConfiguration langCfg;

    // Strings used by the updater.
    public String updateLink, updateVersion;

    // Player 'last sign' HashMap.
    public List<Player> signPlayers = new ArrayList<>();

    // Shows whether the 'signcrafting' option is enabled or disabled.
    public boolean isSignCrafting;

    // Shows if sending an update message is allowed.
    public boolean sendUpdateMsgToPlayer;

    /*
     * ---------------------------------------------------------
     *  Private Variables
     * ---------------------------------------------------------
     */

    // Minecraft and plugin logger.
    private Logger log_ = Logger.getLogger("Minecraft");
    private PluginLogger plog_;

    // Database Connection.
    private Database database_;

    // The language module.
    private LanguageLoader langLoader_ = new LanguageLoader(this);

    // Backup Manager.
    private BackupManager backupManager_ = new BackupManager(this);

    // Sign Manager.
    private SignManager signManager_ = new SignManager(this);

    /*
     * ---------------------------------------------------------
     *  JavaPlugin Methods
     * ---------------------------------------------------------
     */

    @Override
    public void onDisable() {
        this.reloadConfig();
        this.saveConfig();
        PluginDescriptionFile plugin = this.getDescription();
        // Close database if needed.
        if (this.database_.getConnection() != null) {
            try {
                this.log_.info("Closing database...");
                this.database_.getConnection().close();
                this.log_.info("Database successfully closed.");
            } catch (Exception e) {
                this.log_.info("An Error occured! Error:");
                e.printStackTrace();
                this.plog_.warn(e.getMessage(), true);
            }
        }
        // Close logger if needed.
        if (this.plog_ != null) this.plog_.close();
        this.log_.info("Version " + plugin.getVersion() + " by CodeHat disabled.");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.log_ = this.getLogger();
        this.loadConfig();
        this.backupManager_.checkConfigVersion();
        this.setupLogger();
        this.langLoader_.setupLanguage();
        this.langLoader_.loadLanguage();
        this.signManager_.setupColoredSigns();
        this.registerListeners();
        this.registerCommands();
        // Check if Vault is installed.
        if (!this.setupEconomy()) {
            this.log_.info("Some features won't work, because no Vault/Economy dependency found!");
            this.log_.info("Please install this dependency!");
            if (this.plog_ != null) this.plog_.warn("Vault is NOT installed!", true);
        }
        this.loadDatabase();
        this.checkUpdates();
        this.startMetrics();
        PluginDescriptionFile plugin = this.getDescription();
        this.log_.info("Version " + plugin.getVersion() + " by CodeHat enabled.");
        super.onEnable();
    }

    /*
     * ---------------------------------------------------------
     *  Plugin Methods
     * ---------------------------------------------------------
     */

    /**
     * Registers all SignColors listener.
     */
    private void registerListeners() {
        new PlayerListener(this, this.langLoader_);
        new SignChangeListener(this, this.langLoader_);
        new BlockListener(this, this.langLoader_);
    }

    /**
     * Registers all SignColors commands.
     */
    private void registerCommands() {
        CommandHandler cmdh = new CommandHandler(this, this.langLoader_);

        // Register all subcommands.
        cmdh.registerNewCommand("help", new HelpCommand(this, this.langLoader_));
        cmdh.registerNewCommand("reload", new ReloadCommand(this, this.langLoader_));
        cmdh.registerNewCommand("colorcodes", new ColorCodesCommand(this, this.langLoader_));
        cmdh.registerNewCommand("givesign", new GiveSignCommand(this, this.langLoader_));
        cmdh.registerNewCommand("colorsymbol", new ColorSymbolCommand(this, this.langLoader_));
        cmdh.registerNewCommand("upgrade", new UpgradeCommand(this, this.langLoader_));

        // Set executor for /sc.
        this.getCommand("sc").setExecutor(new CommandHandler(this, this.langLoader_));
    }

    /**
     * Loads the config.yml file.
     */
    public void loadConfig() {
        try {
            // Create folders if needed.
            if (!this.getDataFolder().exists()) {
                if (!this.getDataFolder().mkdirs()) {
                    this.log_.warning("Could not create the data folder for the plugin! Please check if you have enough " +
                            "permissions to create folders!");
                    return;
                }
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                // Config not found.
                this.getLogger().info("config.yml not found, creating!");
                this.saveDefaultConfig();
            } else {
                // Config found.
                this.getLogger().info("config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.reloadConfig();
    }

    /**
     * Configures the PluginLogger.
     */
    public void setupLogger() {
        if (this.getConfig().getBoolean("logging")) {
            this.plog_ = new PluginLogger(this);
        } else if (!this.getConfig().getBoolean("logging") && this.plog_ != null) {
            this.plog_.close();
            this.plog_ = null;
        }
    }

    /**
     * Loads the database for the location of the signs.
     */
    public void loadDatabase() {
        if (this.isSignCrafting && (this.database_ == null || this.database_.getConnection() == null)) {
            this.getLogger().info("Using database type: " + this.getConfig().getString("database_type").toUpperCase());
            if (this.getConfig().getString("database_type").equals("mysql")) {
                MySQL mysql = new MySQL(this, this.getConfig().getString("mysql.host"), this.getConfig().getString("mysql.port"),
                        this.getConfig().getString("mysql.database"), this.getConfig().getString("mysql.username"),
                        this.getConfig().getString("mysql.password"));
                mysql.openConnection();
                this.database_ = mysql;
                PreparedStatement ps;
                try {
                    ps = this.database_.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS "
                            + this.getConfig().getString("mysql.table_prefix")
                            + "sign_locations (world VARCHAR(50), x INT, y INT, z INT)");
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                File db_dir = new File(this.getDataFolder().toPath().toString() + File.separator + "data" + File.separator);
                if (!db_dir.exists()) {
                    if (!db_dir.mkdir()) {
                        this.log_.warning("Could not create 'data' folder! Please create it manually and restart the server!");
                        return;
                    }
                }
                SQLite sqlite = new SQLite(this, "data" + File.separator + "sign_locations.db");
                sqlite.openConnection();
                this.database_ = sqlite;
                PreparedStatement ps;
                try {
                    ps = this.database_.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS "
                            + this.getConfig().getString("mysql.table_prefix")
                            + "sign_locations (world VARCHAR(50), x INT, y INT, z INT)");
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if (!this.isSignCrafting && this.database_.getConnection() != null) {
            try {
                this.log_.info("Closing database...");
                this.database_.getConnection().close();
                this.log_.info("Database successfully closed.");
            } catch (Exception e) {
                this.log_.info("An Error occured! Error:");
                e.printStackTrace();
                this.plog_.warn(e.getMessage(), true);
            }
        }
    }

    /**
     * Setups vault.
     *
     * @return True on a successfull economy setup, false if economy setup fails.
     */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        ECONOMY = rsp.getProvider();
        return ECONOMY != null;
    }

    /**
     * Starts metrics service.
     */
    public void startMetrics() {
        if (this.getConfig().getBoolean("metrics")) {
            Metrics metrics;
            try {
                metrics = new Metrics(this);
                metrics.start();
            } catch (IOException e) {
                this.log_.info("Metrics: Failed to submit the stats :-(");
                e.printStackTrace();
                this.plog_.warn(e.getMessage(), true);
            }
        }
    }

    /**
     * Checks for plugin updates.
     */
    private void checkUpdates() {
        if (getConfig().getBoolean("updatecheck")) {
            final PluginDescriptionFile plugin = getDescription();
            this.log_.info("Checking for Updates...");
            this.getServer().getScheduler().runTaskAsynchronously(this, new Updater(plugin.getVersion(),
                    new UpdateCallback<UpdateResult, String>() {
                @Override
                public void call(UpdateResult result, String version) {
                    switch (result) {
                        case NEEDED:
                            getServer().getConsoleSender().sendMessage("[SignColors] " + ChatColor.GREEN
                                    + "A new version is available: v" + version);
                            getServer().getConsoleSender().sendMessage("[SignColors] " + ChatColor.GREEN
                                    + "Get it from: " + ChatColor.GOLD + Updater.getDownloadUrl());
                            info("New version available: v" + version, true);
                            sendUpdateMsgToPlayer = true;
                            updateLink = Updater.getDownloadUrl();
                            updateVersion = version;
                            break;
                        case UNNEEDED:
                            log_.info("No new version available");
                            break;
                        default:
                            log_.info("Could not check for Updates");
                    }
                }
            }));
        }
    }

    /**
     * Return the plugin database.
     *
     * @return The database. Either MySQL or SQLite.
     */
    public Database getPluginDatabase() {
        return this.database_;
    }

    /**
     * Return the plugin's language loader.
     *
     * @return The language loader.
     */
    public LanguageLoader getLanguageLoader() {
        return this.langLoader_;
    }

    /**
     * Return the plugin's sign manager.
     *
     * @return The sign manager.
     */
    public SignManager getSignManager() {
        return this.signManager_;
    }

    /**
     * Logs with INFO level.
     *
     * @param msg    Message to log.
     * @param toFile Log it to file?
     */
    private void info(String msg, boolean toFile) {
        if (this.plog_ != null) this.plog_.info(msg, toFile);
    }

    /**
     * Logs with INFO level.
     *
     * @param msg    Message to log.
     * @param toFile Log it to file?
     */
    public void warn(String msg, boolean toFile) {
        if (this.plog_ != null) this.plog_.warn(msg, toFile);
    }
}