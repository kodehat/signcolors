/*
 * Copyright (c) 2015 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.languages;

import de.codehat.signcolors.SignColors;

import java.io.IOException;
import java.util.HashMap;

public class LanguageLoading {

	//SignColors instance.
	private SignColors plugin;

	//Language HashMap.
	public HashMap<String, String> lg;

	/**
	 * Constructor.
	 * @param instance SignColors instance.
	 */
	public LanguageLoading(SignColors instance) {
		this.plugin = instance;
	}

	/**
	 * Loads the language HashMap (en/de).
	 */
	public void loadLanguages() {
		this.plugin.languageEN = this.plugin.getConfig().getString("language").equalsIgnoreCase("en");
		if (this.plugin.languageEN) {
			lg = new HashMap<>();
			lg.put("nocmd", this.plugin.cfgen.getString("EN.ENNOCMDACCESS"));
			lg.put("noaction", this.plugin.cfgen.getString("EN.ENNOACTION"));
			lg.put("sciauthor", this.plugin.cfgen.getString("EN.ENSCINFOAUTHOR"));
			lg.put("scicmd", this.plugin.cfgen.getString("EN.ENSCINFOCMD"));
			lg.put("scicmdh", this.plugin.cfgen.getString("EN.ENSCINFOCMDH"));
			lg.put("csch", this.plugin.cfgen.getString("EN.ENCOLORSYMBOLCH"));
			lg.put("cschtma", this.plugin.cfgen.getString("EN.ENCOLORSYMBOLTMA"));
			lg.put("uncmd", this.plugin.cfgen.getString("EN.ENUNKNOWNCMD"));
			lg.put("uncmdh", this.plugin.cfgen.getString("EN.ENUNKNOWNCMDH"));
			lg.put("configre", this.plugin.cfgen.getString("EN.ENCONFREL"));
			lg.put("sc", this.plugin.cfgen.getString("EN.ENSC"));
			lg.put("schelp", this.plugin.cfgen.getString("EN.ENSCHELP"));
			lg.put("scre", this.plugin.cfgen.getString("EN.ENSCRE"));
			lg.put("sccs", this.plugin.cfgen.getString("EN.ENSCCS"));
			lg.put("slone", this.plugin.cfgen.getString("EN.ENSLONE"));
			lg.put("sltwo", this.plugin.cfgen.getString("EN.ENSLTWO"));
			lg.put("sltwob", this.plugin.cfgen.getString("EN.ENSLTWOB"));
			lg.put("slthree", this.plugin.cfgen.getString("EN.ENSLTHREE"));
			lg.put("signmsg", this.plugin.cfgen.getString("EN.ENSIGNMSG"));
			lg.put("signmsgb", this.plugin.cfgen.getString("EN.ENSIGNMSGB"));
			lg.put("notenmoney", this.plugin.cfgen.getString("EN.ENNOTENMONEY"));
			lg.put("notenspace", this.plugin.cfgen.getString("EN.ENNOTENSPACE"));
			lg.put("updatemsg", this.plugin.cfgen.getString("EN.ENUPDATEMSG"));
			lg.put("sclogo", this.plugin.cfgen.getString("EN.TAG"));
			lg.put("colorlist", this.plugin.cfgen.getString("EN.ENCOLORLIST"));
			lg.put("formatlist", this.plugin.cfgen.getString("EN.ENFORMATLIST"));
			lg.put("colorcodes", this.plugin.cfgen.getString("EN.ENCOLORCODES"));
			lg.put("signname", this.plugin.cfgen.getString("EN.ENSIGNNAME"));
			lg.put("signlore", this.plugin.cfgen.getString("EN.ENSIGNLORE"));
			lg.put("pnoton", this.plugin.cfgen.getString("EN.ENPNOTON"));
			lg.put("givesign", this.plugin.cfgen.getString("EN.ENGIVESIGN"));
			lg.put("invamount", this.plugin.cfgen.getString("EN.ENINVAMOUNT"));
			lg.put("gshelp", this.plugin.cfgen.getString("EN.ENGSHELP"));
		} else {
			lg = new HashMap<>();
			lg.put("nocmd", this.plugin.cfgde.getString("DE.DENOCMDACCESS"));
			lg.put("noaction", this.plugin.cfgde.getString("DE.DENOACTION"));
			lg.put("sciauthor", this.plugin.cfgde.getString("DE.DESCINFOAUTHOR"));
			lg.put("scicmd", this.plugin.cfgde.getString("DE.DESCINFOCMD"));
			lg.put("scicmdh", this.plugin.cfgde.getString("DE.DESCINFOCMDH"));
			lg.put("csch", this.plugin.cfgde.getString("DE.DECOLORSYMBOLCH"));
			lg.put("cschtma", this.plugin.cfgde.getString("DE.DECOLORSYMBOLTMA"));
			lg.put("uncmd", this.plugin.cfgde.getString("DE.DEUNKNOWNCMD"));
			lg.put("uncmdh", this.plugin.cfgde.getString("DE.DEUNKNOWNCMDH"));
			lg.put("configre", this.plugin.cfgde.getString("DE.DECONFREL"));
			lg.put("sc", this.plugin.cfgde.getString("DE.DESC"));
			lg.put("schelp", this.plugin.cfgde.getString("DE.DESCHELP"));
			lg.put("scre", this.plugin.cfgde.getString("DE.DESCRE"));
			lg.put("sccs", this.plugin.cfgde.getString("DE.DESCCS"));
			lg.put("slone", this.plugin.cfgde.getString("DE.DESLONE"));
			lg.put("sltwo", this.plugin.cfgde.getString("DE.DESLTWO"));
			lg.put("sltwob", this.plugin.cfgde.getString("DE.DESLTWOB"));
			lg.put("slthree", this.plugin.cfgde.getString("DE.DESLTHREE"));
			lg.put("signmsg", this.plugin.cfgde.getString("DE.DESIGNMSG"));
			lg.put("signmsgb", this.plugin.cfgde.getString("DE.DESIGNMSGB"));
			lg.put("notenmoney", this.plugin.cfgde.getString("DE.DENOTENMONEY"));
			lg.put("notenspace", this.plugin.cfgde.getString("DE.DENOTENSPACE"));
			lg.put("updatemsg", this.plugin.cfgde.getString("DE.DEUPDATEMSG"));
			lg.put("sclogo", this.plugin.cfgde.getString("DE.TAG"));
			lg.put("colorlist", this.plugin.cfgde.getString("DE.DECOLORLIST"));
			lg.put("formatlist", this.plugin.cfgde.getString("DE.DEFORMATLIST"));
			lg.put("colorcodes", this.plugin.cfgde.getString("DE.DECOLORCODES"));
			lg.put("signname", this.plugin.cfgde.getString("DE.DESIGNNAME"));
			lg.put("signlore", this.plugin.cfgde.getString("DE.DESIGNLORE"));
			lg.put("pnoton", this.plugin.cfgde.getString("DE.DEPNOTON"));
			lg.put("givesign", this.plugin.cfgde.getString("DE.DEGIVESIGN"));
			lg.put("invamount", this.plugin.cfgde.getString("DE.DEINVAMOUNT"));
			lg.put("gshelp", this.plugin.cfgde.getString("DE.DEGSHELP"));
		}
	}

	/**
	 * Creates the english language file strings.
	 */
	public void languageFileEN() {
		if (!this.plugin.en.exists()) {
			this.plugin.cfgen.addDefault("EN.TAG", "&6[&3SignColors&6] ");
			this.plugin.cfgen.addDefault("EN.ENNOCMDACCESS", "&cYou do not have access to this command!");
			this.plugin.cfgen.addDefault("EN.ENNOACTION", "&cYou are not allowed to do this!");
			this.plugin.cfgen.addDefault("EN.ENSCINFOAUTHOR", "&6Developed by:");
			this.plugin.cfgen.addDefault("EN.ENSCINFOCMD", "&6Commands:");
			this.plugin.cfgen.addDefault("EN.ENSCINFOCMDH", "&aUse /sc help to get a list of commands.");
			this.plugin.cfgen.addDefault("EN.ENCOLORSYMBOLCH", "&aThe colorsymbol has successfully been set to:");
			this.plugin.cfgen.addDefault("EN.ENCOLORSYMBOLTMA", "&cToo many arguments!");
			this.plugin.cfgen.addDefault("EN.ENUNKNOWNCMD", "&cUnknown command!");
			this.plugin.cfgen.addDefault("EN.ENUNKNOWNCMDH", "&aType /sc help for a list of commands.");
			this.plugin.cfgen.addDefault("EN.ENCONFREL", "&aSuccessfully reloaded config.yml.");
			this.plugin.cfgen.addDefault("EN.ENSC", "&aShows info about SignColors.");
			this.plugin.cfgen.addDefault("EN.ENSCCS", "&aChanges the color symbol.");
			this.plugin.cfgen.addDefault("EN.ENSCRE", "&aReloads the config.yml.");
			this.plugin.cfgen.addDefault("EN.ENSCHELP", "&aShows a list of commands.");
			this.plugin.cfgen.addDefault("EN.ENSLONE", "&4*Click me*");
			this.plugin.cfgen.addDefault("EN.ENSLTWO", "&b< ");
			this.plugin.cfgen.addDefault("EN.ENSLTWOB", " signs >");
			this.plugin.cfgen.addDefault("EN.ENSLTHREE", "&ePrice: ");
			this.plugin.cfgen.addDefault("EN.ENSIGNMSG", "&aHere are ");
			this.plugin.cfgen.addDefault("EN.ENSIGNMSGB", " &asigns.");
			this.plugin.cfgen.addDefault("EN.ENNOTENMONEY", "&cYou don't have enough money to buy signs!");
			this.plugin.cfgen.addDefault("EN.ENNOTENSPACE", "&cYou don't have enough space for signs!");
			this.plugin.cfgen.addDefault("EN.ENUPDATEMSG", "&aAn update is available download it here");
			this.plugin.cfgen.addDefault("EN.ENCOLORLIST", "Colours");
			this.plugin.cfgen.addDefault("EN.ENFORMATLIST", "Formatting");
			this.plugin.cfgen.addDefault("EN.ENCOLORCODES", "&aLists all ColorCodes.");
			this.plugin.cfgen.addDefault("EN.ENSIGNNAME", "&aS&3i&6g&cn");
			this.plugin.cfgen.addDefault("EN.ENSIGNLORE", "&7Sign with colors");
			this.plugin.cfgen.addDefault("EN.ENPNOTON", "&cPlayer not online or not found!");
			this.plugin.cfgen.addDefault("EN.ENGIVESIGN", "&aYou gave &6%s &a-> %s &acolored signs.");
			this.plugin.cfgen.addDefault("EN.ENINVAMOUNT", "&cInvalid amount!");
			this.plugin.cfgen.addDefault("EN.ENGSHELP", "&aGive colored signs.");
			this.plugin.cfgen.options().copyDefaults(true);
			try {
				this.plugin.cfgen.save(this.plugin.en);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates the german language file strings.
	 */
	public void languageFileDE() {
		if (!this.plugin.de.exists()) {
			this.plugin.cfgde.addDefault("DE.TAG", "&6[&3SignColors&6] ");
			this.plugin.cfgde.addDefault("DE.DENOCMDACCESS", "&cDu hast keinen Zugriff auf diesen Befehl!");
			this.plugin.cfgde.addDefault("DE.DENOACTION", "&cDu hast keine Rechte dies zu tun!");
			this.plugin.cfgde.addDefault("DE.DESCINFOAUTHOR", "&6Entwickelt von:");
			this.plugin.cfgde.addDefault("DE.DESCINFOCMD", "&6Befehle:");
			this.plugin.cfgde.addDefault("DE.DESCINFOCMDH", "&aBenutze /sc help fuer eine Liste von Befehlen.");
			this.plugin.cfgde.addDefault("DE.DECOLORSYMBOLCH", "&aDas Farbsymbol wurde erfolgreich geaendert zu:");
			this.plugin.cfgde.addDefault("DE.DECOLORSYMBOLTMA", "&cZu viele Argumente!");
			this.plugin.cfgde.addDefault("DE.DEUNKNOWNCMD", "&cUnbekannter Befehl!");
			this.plugin.cfgde.addDefault("DE.DEUNKNOWNCMDH", "&aGebe /sc help fuer eine Liste von Befehlen ein.");
			this.plugin.cfgde.addDefault("DE.DECONFREL", "&aDie config.yml wurde erfolgreich neugeladen.");
			this.plugin.cfgde.addDefault("DE.DESC", "&aZeigt Infos ueber SignColors.");
			this.plugin.cfgde.addDefault("DE.DESCCS", "&aAendert das Farbsymbol.");
			this.plugin.cfgde.addDefault("DE.DESCRE", "&aLaedt die config.yml neu.");
			this.plugin.cfgde.addDefault("DE.DESCHELP", "&aZeigt eine Liste von Befehlen.");
			this.plugin.cfgde.addDefault("DE.DESLONE", "&4*Klick mich*");
			this.plugin.cfgde.addDefault("DE.DESLTWO", "&b<");
			this.plugin.cfgde.addDefault("DE.DESLTWOB", " Schilder>");
			this.plugin.cfgde.addDefault("DE.DESLTHREE", "&ePreis: ");
			this.plugin.cfgde.addDefault("DE.DESIGNMSG", "&aHier sind ");
			this.plugin.cfgde.addDefault("DE.DESIGNMSGB", " &aSchilder.");
			this.plugin.cfgde.addDefault("DE.DENOTENMONEY", "&cDu hast nicht genug Geld fuer farbige Schilder!");
			this.plugin.cfgde.addDefault("DE.DENOTENSPACE", "&cDu hast nicht genug Platz im Inventar!");
			this.plugin.cfgde.addDefault("DE.DEUPDATEMSG", "&aEin Update ist verfuegbar, downloade es hier");
			this.plugin.cfgde.addDefault("DE.DECOLORLIST", "Farben");
			this.plugin.cfgde.addDefault("DE.DEFORMATLIST", "Formatierungen");
			this.plugin.cfgde.addDefault("DE.DECOLORCODES", "&aListet alle Farbcodes.");
			this.plugin.cfgde.addDefault("DE.DESIGNNAME", "&aS&3c&6h&ci&dl&fd");
			this.plugin.cfgde.addDefault("DE.DESIGNLORE", "&7Schild mit Farben");
			this.plugin.cfgde.addDefault("DE.DEPNOTON", "&cSpieler nicht online oder nicht gefunden!");
			this.plugin.cfgde.addDefault("DE.DEGIVESIGN", "&aDu hast &6%s &a-> %s &afarbige Schilder gegeben.");
			this.plugin.cfgde.addDefault("DE.DEINVAMOUNT", "&cUngueltige Anzahl!");
			this.plugin.cfgde.addDefault("DE.DEGSHELP", "&aVergebe farbige Schilder.");
			this.plugin.cfgde.options().copyDefaults(true);
			try {
				this.plugin.cfgde.save(this.plugin.de);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns language message.
	 * @param key Language key.
	 * @return Message.
	 */
	public String getLang(String key) {
		return lg.get(key);
	}
}