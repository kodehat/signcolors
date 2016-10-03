/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.languages;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class LanguageLoader {

    /*
     * ---------------------------------------------------------
     *  Private Variables
     * ---------------------------------------------------------
     */

    //Language HashMap.
    private HashMap<String, String> languageMap_;
    //SignColors instance.
    private SignColors plugin_;

    /**
     * Constructor.
     *
     * @param instance SignColors instance.
     */
    public LanguageLoader(SignColors instance) {
        this.plugin_ = instance;
    }

    /**
     * Loads the given language from the LANGCODE.yml.
     */
    public void setupLanguage() {
        // Get current language code from config.
        String languageCode = this.plugin_.getConfig().get("language").toString();
        // Get 'languages' folder.
        File langDir = new File(this.plugin_.getDataFolder().toPath().toString() + File.separator + "languages"
                + File.separator);
        // Create 'languages' if it does not exist.
        if (!langDir.exists()) {
            if (!langDir.mkdir()) {
                this.plugin_.getLogger().warning("Could not create 'languages' folder! Please create it manually and restart the server!");
                return;
            }
        }
        // Check if the 'languages' folder contains all languages. Extract them if not.
        if (langDir.isDirectory() && langDir.listFiles().length == 0) {
            // All language codes.
            String[] languageCodes = {"EN", "DE", "ES"};

            // Extract all available languages.
            for (String language : languageCodes) {
                Utils.extractFile(this.plugin_.getResource(language + ".yml"), new File(this.plugin_.getDataFolder().toPath().toString() + File.separator
                        + "languages" + File.separator + language + ".yml"));
            }
        }
        // Get current languages file.
        File languageFile = new File(this.plugin_.getDataFolder().toPath().toString() + File.separator + "languages"
                + File.separator + languageCode + ".yml");
        // Load current language file.
        this.plugin_.langCfg = YamlConfiguration.loadConfiguration(languageFile);
        this.plugin_.getLogger().info("Successfully loaded language file: " + languageCode + ".yml :)");
    }

    /**
     * Loads the language HashMap (en/de).
     */
    public void loadLanguage() {
        languageMap_ = new HashMap<>();
        languageMap_.put("nocmd", this.plugin_.langCfg.getString("NOCMDACCESS"));
        languageMap_.put("noaction", this.plugin_.langCfg.getString("NOACTION"));
        languageMap_.put("sciauthor", this.plugin_.langCfg.getString("SCINFOAUTHOR"));
        languageMap_.put("scicmd", this.plugin_.langCfg.getString("SCINFOCMD"));
        languageMap_.put("scicmdh", this.plugin_.langCfg.getString("SCINFOCMDH"));
        languageMap_.put("csch", this.plugin_.langCfg.getString("COLORSYMBOLCH"));
        languageMap_.put("cschtma", this.plugin_.langCfg.getString("COLORSYMBOLTMA"));
        languageMap_.put("uncmd", this.plugin_.langCfg.getString("UNKNOWNCMD"));
        languageMap_.put("uncmdh", this.plugin_.langCfg.getString("UNKNOWNCMDH"));
        languageMap_.put("configre", this.plugin_.langCfg.getString("CONFREL"));
        languageMap_.put("sc", this.plugin_.langCfg.getString("SC"));
        languageMap_.put("schelp", this.plugin_.langCfg.getString("SCHELP"));
        languageMap_.put("scre", this.plugin_.langCfg.getString("SCRE"));
        languageMap_.put("sccs", this.plugin_.langCfg.getString("SCCS"));
        languageMap_.put("slone", this.plugin_.langCfg.getString("SLONE"));
        languageMap_.put("sltwo", this.plugin_.langCfg.getString("SLTWO"));
        languageMap_.put("signmsg", this.plugin_.langCfg.getString("SIGNMSG"));
        languageMap_.put("signmsgb", this.plugin_.langCfg.getString("SIGNMSGB"));
        languageMap_.put("notenmoney", this.plugin_.langCfg.getString("NOTENMONEY"));
        languageMap_.put("notenspace", this.plugin_.langCfg.getString("NOTENSPACE"));
        languageMap_.put("updatemsg", this.plugin_.langCfg.getString("UPDATEMSG"));
        languageMap_.put("sclogo", this.plugin_.langCfg.getString("TAG"));
        languageMap_.put("colorlist", this.plugin_.langCfg.getString("COLORLIST"));
        languageMap_.put("formatlist", this.plugin_.langCfg.getString("FORMATLIST"));
        languageMap_.put("colorcodes", this.plugin_.langCfg.getString("COLORCODES"));
        languageMap_.put("signname", this.plugin_.langCfg.getString("SIGNNAME"));
        languageMap_.put("signlore", this.plugin_.langCfg.getString("SIGNLORE"));
        languageMap_.put("pnoton", this.plugin_.langCfg.getString("PNOTON"));
        languageMap_.put("givesign", this.plugin_.langCfg.getString("GIVESIGN"));
        languageMap_.put("invamount", this.plugin_.langCfg.getString("INVAMOUNT"));
        languageMap_.put("gshelp", this.plugin_.langCfg.getString("GSHELP"));
        languageMap_.put("notallfl", this.plugin_.langCfg.getString("NOTALLFL"));
        Message.lang = this;
    }

    /**
     * Returns a specific message.
     *
     * @param key Language key.
     * @return Message.
     */
    public String getLang(String key) {
        return languageMap_.get(key);
    }
}