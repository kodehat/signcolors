/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.manager;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Utils;
import de.codehat.signcolors.util.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupManager extends Manager {

    // Current version of the config
    private static final int CONFIG_VERSION = 5;

    public BackupManager(SignColors plugin) {
        super(plugin);
    }

    /**
     * Checks the current config version and creates new one if necessary.
     */
    public void checkConfigVersion() {
        if (CONFIG_VERSION > this.getPlugin().getConfig().getInt("configversion")) {
            // Backup old config file and languages folder to be able to update them.
            this.backupOldConfig();
            this.backupOldLangFiles();
            this.getPlugin().saveDefaultConfig();
        }
    }

    /**
     * Makes a backup of the current config.
     */
    private void backupOldConfig() {
        File oldConfigBackup = new File(this.getPlugin().getDataFolder(), "config.yml.old");
        // Delete old config backup if it exists
        if (oldConfigBackup.exists()) {
            if (!oldConfigBackup.delete()) {
                SignColors.logError("Could not delete old config file backup! " +
                        "Please delete it manually and restart the server!");
                SignColors.logError("Disabling this plugin until problem has been fixed!");
                this.getPlugin().getServer().getPluginManager().disablePlugin(this.getPlugin());
            }
        }
        Path sourceConfig = Paths.get(this.getPlugin().getDataFolder().toPath().toString() + File.separator
                + "config.yml");
        Path targetConfig = Paths.get(this.getPlugin().getDataFolder().toPath().toString() + File.separator
                + "config.yml.old");
        try {
            // Make a backup of the current config -> config.yml.old
            Files.copy(sourceConfig, targetConfig);
        } catch (IOException exception) {
            SignColors.logError("Couldn't backup old config! Delete config manually and restart the server!");
            SignColors.logError("Disabling this plugin until problem has been fixed!");
            exception.printStackTrace();
            this.getPlugin().getServer().getPluginManager().disablePlugin(this.getPlugin());
        }
        SignColors.info("Made a backup of the old config!");
        File config = new File(this.getPlugin().getDataFolder().toPath().toString() + File.separator
                + "config.yml");
        // Delete the old config to be able to create the new one
        if (!config.delete()) {
            SignColors.logError("Could not delete old config! Please delete it manually and restart the server!");
            SignColors.logError("Disabling this plugin until problem has been fixed!");
            this.getPlugin().getServer().getPluginManager().disablePlugin(this.getPlugin());
        }
    }

    /**
     * Makes a backup of the current 'languages' folder.
     */
    private void backupOldLangFiles() {
        File oldLangBackup = new File(this.getPlugin().getDataFolder().toPath().toString() + File.separator
                + "languages.old.zip");
        // Delete old languages backup file if it exists
        if (oldLangBackup.exists()) {
            if (!oldLangBackup.delete()) {
                SignColors.logError("Could not delete old languages backup file! "
                        + "Please delete it manually and restart the server!");
                SignColors.logError("Disabling this plugin until problem has been fixed!");
                this.getPlugin().getServer().getPluginManager().disablePlugin(this.getPlugin());
            }
        }
        // Create a .zip file of the current 'languages' folder
        ZipUtils.zipFolder(this.getPlugin().getDataFolder().toPath().toString() + File.separator
                + "languages", this.getPlugin().getDataFolder().toPath().toString() + File.separator
                + "languages.old.zip");
        SignColors.info("Made a backup of the old languages folder!");
        // Delete the old 'languages' folder to be able to create the updated one
        if (!Utils.deleteDirectory(new File(this.getPlugin().getDataFolder().toPath().toString()
                + File.separator + "languages"))) {
            SignColors.logError("Could not delete the old languages folder");
            SignColors.logError("Disabling this plugin until problem has been fixed!");
            this.getPlugin().getServer().getPluginManager().disablePlugin(this.getPlugin());
        }
    }
}