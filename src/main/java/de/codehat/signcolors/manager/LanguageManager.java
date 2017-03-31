/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.manager;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LanguageManager extends Manager {

    private FileConfiguration langCfg;

    public LanguageManager(SignColors plugin) {
        super(plugin);
    }

    /**
     * Creates and extracts all language files.
     * Additionally it loads current language file.
     */
    public void setupLanguage() {
        // Get current language code from config
        String languageCode = this.getPlugin().getConfig().get("language").toString();
        // Get 'languages' folder
        File languageDir = new File(this.getPlugin().getDataFolder().getAbsolutePath() + File.separator
                + "languages" + File.separator);
        // Create 'languages' folder if it doesn't exist
        if (!languageDir.exists()) {
            if (!languageDir.mkdir()) {
                SignColors.logError("Could not create 'languages' folder! Please create it manually and restart the server!");
                SignColors.logError("Disabling this plugin until problem has been fixed!");
                this.getPlugin().getServer().getPluginManager().disablePlugin(this.getPlugin());
            }
        }
        // Check if the 'languages' folder contains all languages. Extract them if not
        if (languageDir.isDirectory() && languageDir.listFiles().length == 0) {
            // All language codes
            String[] languageCodes = {"EN", "DE", "ES"};

            // Extract all available languages
            for (String language : languageCodes) {
                Utils.extractFile(this.getPlugin().getResource(language + ".yml"),
                        new File(this.getPlugin().getDataFolder().getAbsolutePath() + File.separator
                        + "languages" + File.separator + language + ".yml"));
            }
        }
        this.loadLanguage();
    }

    /**
     * (Re-)loads the current language file into memory.
     */
    public void loadLanguage() {
        String languageCode = this.getPlugin().getConfig().get("language").toString();
        // Get current language file
        File languageFile = new File(this.getPlugin().getDataFolder().getAbsolutePath() + File.separator
                + "languages" + File.separator + languageCode + ".yml");
        // Load current language file.
        this.langCfg = YamlConfiguration.loadConfiguration(languageFile);
        Message.signcolorsLogo = this.getStr("TAG");
        SignColors.info("Successfully loaded the language file (" + languageCode + ".yml)!");
    }

    /**
     * Translates the given key to the related language string.
     *
     * @param key The key in the language file.
     * @return    The translated string.
     */
    public String getStr(String key) {
        return this.langCfg.getString(key);
    }
}
