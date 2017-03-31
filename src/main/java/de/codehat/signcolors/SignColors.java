/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors;

import de.codehat.signcolors.commands.*;
import de.codehat.signcolors.database.Database;
import de.codehat.signcolors.database.MySQL;
import de.codehat.signcolors.database.SQLite;
import de.codehat.signcolors.listeners.BlockListener;
import de.codehat.signcolors.listeners.PlayerListener;
import de.codehat.signcolors.listeners.PluginListener;
import de.codehat.signcolors.listeners.SignChangeListener;
import de.codehat.signcolors.managers.BackupManager;
import de.codehat.signcolors.managers.LanguageManager;
import de.codehat.signcolors.managers.SignManager;
import de.codehat.signcolors.updater.Updater;
import net.milkbowl.vault.economy.Economy;
import org.bstats.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is the main class of the 'SignColors' spigot plugin.
 */
public class SignColors extends JavaPlugin {

    // All available colorcodes
    public static final String ALL_COLOR_CODES = "0123456789abcdefklmnor";

    // Vault instance
    private static Economy econ = null;

    // Logger instance
    private static Logger logger;

    // Player 'last sign' HashMap
    public List<Player> signPlayers = new ArrayList<>();

    // Shows if a newer version of this plugin is available
    private boolean updateAvailable = false;

    // If a newer version is available, the version number is saved here
    private String newerVersion;

    // The command managers
    private CommandManager commandManager = new CommandManager(this);

    // Shows whether the 'signcrafting' option is enabled or disabled
    private boolean signcrafting;

    // Database connection
    private Database database;

    // Language manager
    private LanguageManager languageManager;

    // Sign Manager
    private SignManager signManager;

    /**
     * Log an info message to console.
     *
     * @param message The message to log.
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Log a warning message to console.
     *
     * @param message The message to log.
     */
    public static void logError(String message) {
        logger.severe(message);
    }

    /**
     * Get the econ instance.
     *
     * @return The econ instance.
     */
    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        // Close the database if necessary
        if (this.database.getConnection() != null) {
            this.closeDatabase();
        }
        // Log "disable" message
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        info(String.format("Version %s by %s disabled.", pluginDescriptionFile.getVersion(),
                pluginDescriptionFile.getAuthors().get(0)));
    }

    @Override
    public void onEnable() {
        logger = this.getLogger();

        // Save the default config, if it doesn't exist.
        this.saveDefaultConfig();

        // Setup all managers.
        this.loadManagers();

        // Setup Vault
        if (!this.setupEconomy()) {
            logError("Some features won't work, because Vault is missing!");
            logError("Please install Vault!");
        }

        // Register listeners
        this.registerListeners();
        // Register commands
        this.registerCommands();

        // Load database
        this.loadDatabase();

        // Setup metrics
        this.setupMetrics();

        // Start update check
        this.checkForUpdate();

        // Log enable message
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        logger.info(String.format("Version %s by %s enabled.", pluginDescriptionFile.getVersion(),
                pluginDescriptionFile.getAuthors().get(0)));
    }

    /**
     * Registers the plugin's listeners.
     */
    private void registerListeners() {
        // All available listeners
        PluginListener[] listeners = {
                new PlayerListener(this),
                new BlockListener(this),
                new SignChangeListener(this)
        };

        // Loop through all listeners and register each of them
        for (PluginListener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * Registers the plugin's commands.
     */
    private void registerCommands() {
        // Register all commands
        this.commandManager.registerCommand("", new InfoCommand(this));
        this.commandManager.registerCommand("colorcodes", new ColorCodesCommand(this));
        this.commandManager.registerCommand("givesign", new GiveSignCommand(this));
        this.commandManager.registerCommand("help", new HelpCommand(this));
        this.commandManager.registerCommand("reload", new ReloadCommand(this));
        this.commandManager.registerCommand("upgrade", new UpgradeCommand(this));

        // Set command executor
        this.getCommand("sc").setExecutor(this.commandManager);
        // Set tab completer
        this.getCommand("sc").setTabCompleter(new TabCompletion());
    }

    /**
     * Load the plugin's managers.
     */
    private void loadManagers() {
        BackupManager backupManager = new BackupManager(this);
        this.languageManager = new LanguageManager(this);
        this.signManager = new SignManager(this);

        backupManager.checkConfigVersion();
        this.languageManager.setupLanguage();
        this.signManager.setupColoredSigns();
    }

    /**
     * Loads the database for the locations of the placed colored signs.
     */
    public void loadDatabase() {
        if (this.isSigncrafting() && (this.database == null || this.database.getConnection() == null)) {
            info("Loading database (" + this.getConfig().getString("database_type").toUpperCase() + ").");
            if (this.getConfig().getString("database_type").equals("mysql")) {
                MySQL mysql = new MySQL(this, this.getConfig().getString("mysql.host"),
                        this.getConfig().getString("mysql.port"),
                        this.getConfig().getString("mysql.database"),
                        this.getConfig().getString("mysql.username"),
                        this.getConfig().getString("mysql.password"));
                mysql.openConnection();
                this.database = mysql;
            } else {
                File databaseDir = new File(this.getDataFolder().getAbsolutePath() + File.separator + "data"
                        + File.separator);
                if (!databaseDir.exists()) {
                    if (!databaseDir.mkdir()) {
                        logError("Could not create 'data' folder! " +
                                "Please create it manually and restart the server!");
                        logError("Disabling this plugin until problem has been fixed!");
                        this.getServer().getPluginManager().disablePlugin(this);
                    }
                }
                SQLite sqlite = new SQLite(this, "data" + File.separator + "sign_locations.db");
                sqlite.openConnection();
                this.database = sqlite;
            }
            try {
                this.database.createTables(this.getConfig().getString("mysql.table_prefix"));
            } catch (SQLException exception) {
                logError("Could not create necessary database tables!");
                logError("Disabling this plugin until problem has been fixed!");
                exception.printStackTrace();
                this.getServer().getPluginManager().disablePlugin(this);
            }
        } else if (!this.isSigncrafting() && this.database.getConnection() != null) {
            this.closeDatabase();
        }
    }

    /**
     * Initializes vault for economy support.
     *
     * @return true if vault is there and ready, false if not.
     */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    /**
     * Starts the update check task.
     */
    private void checkForUpdate() {
        if (this.getConfig().getBoolean("updatecheck")) {
            info("Looking for a newer version...");
            final PluginDescriptionFile pluginDescriptionFile = getDescription();
            this.getServer().getScheduler().runTaskAsynchronously(this,
                    new Updater(pluginDescriptionFile.getVersion(), (result, newVersion) -> {
                        switch (result) {
                            case AVAILABLE:
                                info("A newer version is available (v" + newVersion + ")!");
                                info("Get it from: " + Updater.getSpigotUrl());
                                this.updateAvailable = true;
                                this.newerVersion = newVersion;
                                break;
                            case UNAVAILABLE:
                                info("No newer version available.");
                                break;
                            default:
                                logError("Checking for a newer version FAILED :(");
                        }
                    }));
        } else {
            info("Looking for a newer version is DISABLED :(");
        }
    }

    /**
     * Enables or disabled metrics based on the 'metrics' config value.
     */
    private void setupMetrics() {
        if (this.getConfig().getBoolean("metrics")) {
            new Metrics(this);
            info("Metrics are ENABLED :)");
        } else {
            info("Metrics are DISABLED :(");
        }
    }

    /**
     * Same as {@link LanguageManager#getStr(String)}
     */
    public String getStr(String key) {
        return this.languageManager.getStr(key);
    }

    /**
     * Get the version number of newest update.
     *
     * @return The version string.
     */
    public String getNewerVersion() {
        return newerVersion;
    }

    /**
     * Shows if a newer version of plugin is available.
     *
     * @return true if available, false if not.
     */
    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    /**
     * Is the possibility to craft colored signs enabled or disabled?
     *
     * @return true if enabled, false if not.
     */
    public boolean isSigncrafting() {
        return signcrafting;
    }

    /**
     * Enable or disable to possibility to craft colored signs.
     *
     * @param signcrafting true to enable, false to disable.
     */
    public void setSigncrafting(boolean signcrafting) {
        this.signcrafting = signcrafting;
    }

    /**
     * Get the plugin's database.
     *
     * @return The database. Either MySQL or SQLite.
     */
    public Database getPluginDatabase() {
        return this.database;
    }

    private void closeDatabase() {
        try {
            info("Closing database...");
            this.database.getConnection().close();
            info("Database successfully closed.");
        } catch (Exception e) {
            logError("An error occured while closing the database!");
            e.printStackTrace();
        }
    }

    /**
     * Returns the plugin's language manager.
     *
     * @return The language manager.
     */
    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    /**
     * Return the plugin's sign managers.
     *
     * @return The sign managers.
     */
    public SignManager getSignManager() {
        return this.signManager;
    }
}