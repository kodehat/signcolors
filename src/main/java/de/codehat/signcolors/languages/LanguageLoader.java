/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.languages;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Message;

import java.util.HashMap;

public class LanguageLoader {

    //Language HashMap.
    private HashMap<String, String> lg;
    //SignColors instance.
    private SignColors plugin;

    /**
     * Constructor.
     *
     * @param instance SignColors instance.
     */
    public LanguageLoader(SignColors instance) {
        this.plugin = instance;
    }

    /**
     * Loads the language HashMap (en/de).
     */
    public void loadLanguage() {
        lg = new HashMap<>();
        lg.put("nocmd", this.plugin.langCfg.getString("NOCMDACCESS"));
        lg.put("noaction", this.plugin.langCfg.getString("NOACTION"));
        lg.put("sciauthor", this.plugin.langCfg.getString("SCINFOAUTHOR"));
        lg.put("scicmd", this.plugin.langCfg.getString("SCINFOCMD"));
        lg.put("scicmdh", this.plugin.langCfg.getString("SCINFOCMDH"));
        lg.put("csch", this.plugin.langCfg.getString("COLORSYMBOLCH"));
        lg.put("cschtma", this.plugin.langCfg.getString("COLORSYMBOLTMA"));
        lg.put("uncmd", this.plugin.langCfg.getString("UNKNOWNCMD"));
        lg.put("uncmdh", this.plugin.langCfg.getString("UNKNOWNCMDH"));
        lg.put("configre", this.plugin.langCfg.getString("CONFREL"));
        lg.put("sc", this.plugin.langCfg.getString("SC"));
        lg.put("schelp", this.plugin.langCfg.getString("SCHELP"));
        lg.put("scre", this.plugin.langCfg.getString("SCRE"));
        lg.put("sccs", this.plugin.langCfg.getString("SCCS"));
        lg.put("slone", this.plugin.langCfg.getString("SLONE"));
        lg.put("sltwo", this.plugin.langCfg.getString("SLTWO"));
        lg.put("sltwob", this.plugin.langCfg.getString("SLTWOB"));
        lg.put("slthree", this.plugin.langCfg.getString("SLTHREE"));
        lg.put("signmsg", this.plugin.langCfg.getString("SIGNMSG"));
        lg.put("signmsgb", this.plugin.langCfg.getString("SIGNMSGB"));
        lg.put("notenmoney", this.plugin.langCfg.getString("NOTENMONEY"));
        lg.put("notenspace", this.plugin.langCfg.getString("NOTENSPACE"));
        lg.put("updatemsg", this.plugin.langCfg.getString("UPDATEMSG"));
        lg.put("sclogo", this.plugin.langCfg.getString("TAG"));
        lg.put("colorlist", this.plugin.langCfg.getString("COLORLIST"));
        lg.put("formatlist", this.plugin.langCfg.getString("FORMATLIST"));
        lg.put("colorcodes", this.plugin.langCfg.getString("COLORCODES"));
        lg.put("signname", this.plugin.langCfg.getString("SIGNNAME"));
        lg.put("signlore", this.plugin.langCfg.getString("SIGNLORE"));
        lg.put("pnoton", this.plugin.langCfg.getString("PNOTON"));
        lg.put("givesign", this.plugin.langCfg.getString("GIVESIGN"));
        lg.put("invamount", this.plugin.langCfg.getString("INVAMOUNT"));
        lg.put("gshelp", this.plugin.langCfg.getString("GSHELP"));
        lg.put("notallfl", this.plugin.langCfg.getString("NOTALLFL"));
        Message.lang = this;
    }

    /**
     * Returns a specific message.
     *
     * @param key Language key.
     * @return Message.
     */
    public String getLang(String key) {
        return lg.get(key);
    }
}