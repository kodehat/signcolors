/*
 * Copyright (c) 2016 CodeHat.
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

public class BackupManager {

    // Config version.
    private static final int CONFIG_VERSION = 4;

    // Plugin instance.
    private SignColors plugin_;

    public BackupManager(SignColors signColors) {
        this.plugin_ = signColors;
    }

    /**
     * Check the current config version and create new if needed.
     */
    public void checkConfigVersion() {
        if (CONFIG_VERSION > this.plugin_.getConfig().getInt("configversion")) {
            // Backup old config file and languages folder to be able to update them.
            this.backupConfig();
            this.backupLanguages();
            this.plugin_.loadConfig();
        }
    }

    /**
     * Makes a backup of the current config.yml.
     */
    private void backupConfig() {
        File oldConfigBackup = new File(this.plugin_.getDataFolder(), "config.yml.old");
        // Delete old config.yml if it exists.
        if (oldConfigBackup.exists()) {
            if (!oldConfigBackup.delete()) {
                this.plugin_.getLogger().warning("Could not delete old config backup file! " +
                        "Please delete it manually and restart the server!");
                return;
            }
        }
        Path sourceConfig = Paths.get(this.plugin_.getDataFolder().toPath().toString() + File.separator
                + "config.yml");
        Path targetConfig = Paths.get(this.plugin_.getDataFolder().toPath().toString() + File.separator
                + "config.yml.old");
        try {
            // Make a backup of the current config.yml -> config.yml.old.
            Files.copy(sourceConfig, targetConfig);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.plugin_.getLogger().info("Made a backup of the old config.yml!");
        File config = new File(this.plugin_.getDataFolder().toPath().toString() + File.separator + "config.yml");
        // Delete the old config.yml to be able to create the updated one.
        if (!config.delete()) {
            this.plugin_.getLogger().warning("Could not delete old config file! "
                    + "Please delete it manually and restart the server!");
        }
    }

    /**
     * Makes a backup of the current 'languages' folder.
     */
    private void backupLanguages() {
        File oldLangBackup = new File(this.plugin_.getDataFolder().toPath().toString() + File.separator
                + "languages.old.zip");
        // Delete old languages backup .zip file if it exists.
        if (oldLangBackup.exists()) {
            if (!oldLangBackup.delete()) {
                this.plugin_.getLogger().warning("Could not delete old languages backup file! "
                        + "Please delete it manually and restart the server!");
                return;
            }
        }
        // Create a .zip file of the current 'languages' folder.
        ZipUtils.zipFolder(this.plugin_.getDataFolder().toPath().toString() + File.separator + "languages",
                this.plugin_.getDataFolder().toPath().toString() + File.separator + "languages.old.zip");
        this.plugin_.getLogger().info("Made a backup of the old languages folder!");
        // Delete the old 'languages' folder to be able to create the updated one.
        if (!Utils.deleteDirectory(new File(this.plugin_.getDataFolder().toPath().toString() + File.separator
                + "languages"))) {
            this.plugin_.getLogger().warning("Could not delete the old languages folder");
        }
    }
}