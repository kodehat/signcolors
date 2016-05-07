/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors;

import de.codehat.signcolors.commands.*;
import de.codehat.signcolors.database.SQLite;
import de.codehat.signcolors.languages.LanguageLoader;
import de.codehat.signcolors.listener.ColoredSignListener;
import de.codehat.signcolors.logger.PluginLogger;
import de.codehat.signcolors.updater.UpdateResult;
import de.codehat.signcolors.updater.Updater;
import de.codehat.signcolors.util.Message;
import de.codehat.signcolors.util.Utils;
import de.codehat.signcolors.util.ZipUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class SignColors extends JavaPlugin implements Listener {

    //All available colorcodes.
    public static final String ALL_COLOR_CODES = "0123456789abcdefklmnor";

    //Config version.
    private static final int CONFIG_VERSION = 2;

    //Vault support.
    public static Economy eco = null;

    //The language FileConfiguration.
    public FileConfiguration langCfg = null;

    //Updater Strings.
    public String updateLink = null, updateVersion = null;

    //ItemStacks/ItemMeta for colored signs.
    public ItemStack i = null;
    //private ItemMeta im = null;

    //Item lores for colored signs.
    private List<String> lores = new ArrayList<>();

    //Player 'last sign' HashMap
    public List<Player> sign_players = new ArrayList<>();

    //Checks if signcrafting is enabled.
    public boolean signcrafting;

    //Checks if sending an updatemessage is allowed.
    public boolean updatePlayerMsg;

    //Minecraft logger.
    private Logger log = Logger.getLogger("Minecraft");
    private PluginLogger plog = null;

    //SQLite Database.
    //private SQLite sqlite = null;

    //Database Connection.
    public Connection c = null;

    //The language module.
    private LanguageLoader lang = new LanguageLoader(this);

    @Override
    public void onDisable() {
        saveConfig();
        PluginDescriptionFile plugin = getDescription();
        if (c != null) {
            try {
                log.info("Closing database...");
                c.close();
                log.info("Database successfully closed.");
            } catch (Exception e) {
                log.info("An Error occured! Error:");
                e.printStackTrace();
                plog.warn(e.getMessage(), true);
            }
        }
        if (plog != null) plog.close();
        log.info("Version " + plugin.getVersion() + " by CodeHat disabled.");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.log = this.getLogger();
        loadConfig();
        checkConfigVersion();
        setupLogger();
        setupLanguage();
        lang.loadLanguage();
        setupSigns();
        new ColoredSignListener(this, lang);
        registerCommands();
        if (!setupEconomy()) {
            log.info("Some features won't work, because no Vault/Economy dependency found!");
            log.info("Please install this dependency!");
            plog.warn("Vault is NOT installed!", true);
        }
        loadDatabase();
        checkUpdates();
        startMetrics();
        PluginDescriptionFile plugin = getDescription();
        log.info("Version " + plugin.getVersion() + " by CodeHat enabled.");
        super.onEnable();
    }

    /**
     * Registers all SignColors commands.
     */
    private void registerCommands() {
        CommandHandler cmdh = new CommandHandler(this, lang);
        cmdh.registerNewCommand("help", new HelpCommand(this, lang));
        cmdh.registerNewCommand("reload", new ReloadCommand(this, lang));
        cmdh.registerNewCommand("colorcodes", new ColorCodesCommand(this, lang));
        cmdh.registerNewCommand("givesign", new GiveSignCommand(this, lang));
        cmdh.registerNewCommand("colorsymbol", new ColorSymbolCommand(this, lang));
        getCommand("sc").setExecutor(new CommandHandler(this, lang));
    }

    /**
     * Loads the config.yml file.
     */
    private void loadConfig() {
        if (new File("plugins" + File.separator + "SignColors" + File.separator + "config.yml").exists()) {
            FileConfiguration cfg = this.getConfig();
            cfg.options().copyDefaults(true);
            this.reloadConfig();
        } else {
            saveDefaultConfig();
            FileConfiguration cfg = this.getConfig();
            cfg.options().copyDefaults(true);
            this.reloadConfig();
        }
    }

    /**
     * Check the current config version and create new if needed.
     */
    private void checkConfigVersion() {
        if (CONFIG_VERSION > this.getConfig().getInt("configversion")) {
            backupConfig();
            backupLanguages();
            loadConfig();
        }
    }

    /**
     * Makes a backup of the current config.yml.
     */
    private void backupConfig() {
        File oldConfigBackup = new File(this.getDataFolder().toPath().toString() + File.separator + "config.yml.old");
        if (oldConfigBackup.exists()) {
            if (!oldConfigBackup.delete()) {
                log.warning("Could not delete old config backup file! Please delete it manually and restart the server!");
                return;
            }
        }
        Path sourceConfig = Paths.get(this.getDataFolder().toPath().toString() + File.separator + "config.yml");
        Path targetConfig = Paths.get(this.getDataFolder().toPath().toString() + File.separator + "config.yml.old");
        try {
            Files.copy(sourceConfig, targetConfig);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        log.info("Made a backup of the old config.yml!");
        File config = new File(this.getDataFolder().toPath().toString() + File.separator + "config.yml");
        if (!config.delete()) {
            log.warning("Could not delete old config file! Please delete it manually and restart the server!");
        }
    }

    /**
     * Makes a backup of the current 'languages' folder.
     */
    private void backupLanguages() {
        File oldLangBackup = new File(this.getDataFolder().toPath().toString() + File.separator + "languages.old");
        if (oldLangBackup.exists()) Utils.deleteDirectory(oldLangBackup);
        ZipUtils.zipFolder(this.getDataFolder().toPath().toString() + File.separator + "languages",
                this.getDataFolder().toPath().toString() + File.separator + "languages.old.zip");
        log.info("Made a backup of the old languages folder!");
        if (Utils.deleteDirectory(new File(this.getDataFolder().toPath().toString() + File.separator + "languages"))) {
            log.info("Successfully deleted old languages folder!");
        } else {
            log.warning("Could not delete the old languages folder");
        }
    }

    /**
     * Loads the given language from the LANGCODE.yml.
     */
    public void setupLanguage() {
        String languageCode = this.getConfig().get("language").toString();
        File langDir = new File(this.getDataFolder().toPath().toString() + File.separator + "languages"
                + File.separator);
        if (!langDir.exists()) {
            if (!langDir.mkdir()) {
                log.warning("Could not create 'languages' folder! Please create it manually and restart the server!");
                return;
            }
        }
        if (langDir.listFiles().length == 0) {
            extractFile(getResource("EN.yml"), new File(this.getDataFolder().toPath().toString() + File.separator
                    + "languages" + File.separator + "EN.yml"));
            extractFile(getResource("DE.yml"), new File(this.getDataFolder().toPath().toString() + File.separator
                    + "languages" + File.separator + "DE.yml"));
            extractFile(getResource("ES.yml"), new File(this.getDataFolder().toPath().toString() + File.separator
                    + "languages" + File.separator + "ES.yml"));
        }
        File languageFile = new File(this.getDataFolder().toPath().toString() + File.separator + "languages"
                + File.separator + languageCode + ".yml");
        langCfg = YamlConfiguration.loadConfiguration(languageFile);
        log.info("Successfully loaded language file: " + languageCode + ".yml :)");
    }

    /**
     * Helper to extract files from the SignColors.jar.
     *
     * @param in   Resource via getResource("file-in-jar.ending").
     * @param file Location where the file should be put to.
     */
    private void extractFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a location of a sign to the database.
     *
     * @param location Location of the sign (world, x, y, z).
     */
    public void addSign(String location) {
        try {
            final Statement signlocation = c.createStatement();
            signlocation.executeUpdate("INSERT INTO signs (location) VALUES ('" + location + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a location of a sign is stored in the database.
     *
     * @param location Location of the sign (world,x,y,z).
     * @return True if stored, false if not found.
     */
    public boolean checkSign(String location) {
        try {
            final Statement signlocation = c.createStatement();
            ResultSet res = signlocation.executeQuery("SELECT * FROM signs WHERE location = '" + location + "';");
            while (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a location of a sign from the database.
     *
     * @param location Location of the sign (world,x,y,z).
     */
    public void deleteSign(String location) {
        Statement signlocation;
        try {
            signlocation = c.createStatement();
            signlocation.executeUpdate("DELETE FROM signs WHERE location = '" + location + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the recipe for the colored signs or recreates it.
     */
    public void setupSigns() {
        i = coloredSignStack();
        /*ItemMeta im = i.getItemMeta();
        lores.clear();
        lores.add(Message.replaceColors(lang.getLang("signlore")));
        im.setDisplayName(Message.replaceColors(lang.getLang("signname")));
        im.setLore(lores);
        i.setItemMeta(im);*/
        if (getConfig().getBoolean("signcrafting")) {
            removeRecipe();
            if (getConfig().get("recipetype").equals("shapeless")) {
                List<String> ingredients = (List<String>) getConfig().getList("recipes.shapeless.ingredients");
                if (ingredients.size() > 9) {
                    log.warning("You added more than nine crafting items to the config!");
                    log.warning("Please change it or you will not be able to craft colored signs!");
                    return;
                }
                ShapelessRecipe sr = new ShapelessRecipe(coloredSignStack(1));
                for (String ingredient : ingredients) {
                    if (ingredient.contains(":")) {
                        String[] ingredientData = ingredient.split(":");
                        Material m = Material.getMaterial(ingredientData[0]);
                        sr.addIngredient(m, Integer.valueOf(ingredientData[1]));
                    } else {
                        Material m = Material.getMaterial(ingredient);
                        sr.addIngredient(m);
                    }
                }
                getServer().addRecipe(sr);
                signcrafting = true;
            } else if (getConfig().get("recipetype").equals("shaped")) {
                removeRecipe();
                List<String> shape = (List<String>) getConfig().getList("recipes.shaped.craftingshape");
                if (shape.size() > 3) {
                    log.warning("You added more than three recipe shapes to the config!");
                    log.warning("Please change it or you will not be able to craft colored signs!");
                    return;
                }
                ShapedRecipe sr = new ShapedRecipe(coloredSignStack(1));
                switch (shape.size()) {
                    case 1:
                        sr.shape(shape.get(0));
                        break;
                    case 2:
                        sr.shape(shape.get(0), shape.get(1));
                        break;
                    case 3:
                        sr.shape(shape.get(0), shape.get(1), shape.get(2));
                        break;
                    default:
                        log.warning("You defined too many or no recipe shapes!");
                        log.warning("Please change it or you will not be able to craft colored signs!");
                        return;
                }
                ConfigurationSection ingredients = getConfig().getConfigurationSection("recipes.shaped.ingredients");
                for (String key : ingredients.getKeys(false)) {
                    if (ingredients.get(key).toString().contains(":")) {
                        String[] ingredient = ingredients.get(key).toString().split(":");
                        Material m = Material.getMaterial(ingredient[0]);
                        sr.setIngredient(key.charAt(0), m, Integer.valueOf(ingredient[1]));
                    } else {
                        Material m = Material.getMaterial(ingredients.get(key).toString());
                        sr.setIngredient(key.charAt(0), m);
                    }
                }
                getServer().addRecipe(sr);
                signcrafting = true;
            } else {
                log.warning("Unknown config value of 'recipetype'! Possible values are: 'shaped' and 'shapeless'.");
                log.warning("Please change it or you will not be able to craft colored signs!");
            }
        } else {
            removeRecipe();
            signcrafting = false;
        }
    }

    /**
     * The ItemStack for colored signs.
     *
     * @return The ItemStack for colored signs.
     */
    public ItemStack coloredSignStack() {
        ItemStack i = new ItemStack(Material.SIGN, this.getConfig().getInt("signamount"));
        ItemMeta im = i.getItemMeta();
        lores.clear();
        lores.add(Message.replaceColors(lang.getLang("signlore")));
        im.setDisplayName(Message.replaceColors(lang.getLang("signname")));
        im.setLore(lores);
        i.setItemMeta(im);
        return i;
    }

    /**
     * The ItemStack for colored signs.
     *
     * @param amount Amount if you do not want to use the config 'amount'. Else it can be null.
     * @return The ItemStack for colored signs.
     */
    public ItemStack coloredSignStack(int amount) {
        ItemStack i = new ItemStack(Material.SIGN, amount);
        ItemMeta im = i.getItemMeta();
        lores.clear();
        lores.add(Message.replaceColors(lang.getLang("signlore")));
        im.setDisplayName(Message.replaceColors(lang.getLang("signname")));
        im.setLore(lores);
        i.setItemMeta(im);
        return i;
    }

    /**
     * Configures the PluginLogger.
     */
    public void setupLogger() {
        if (this.getConfig().getBoolean("logging")) {
            plog = new PluginLogger(this);
        } else if (!this.getConfig().getBoolean("logging") && plog != null) {
            plog.close();
            plog = null;
        }
    }

    /**
     * Removes the created colored sign recipe.
     */
    private void removeRecipe() {
        Iterator<Recipe> it = getServer().recipeIterator();
        Recipe recipe;
        while (it.hasNext()) {
            recipe = it.next();
            if (recipe != null && recipe.getResult().getType() == Material.SIGN && recipe.getResult().getAmount()
                    == getConfig().getInt("signamount")) {
                it.remove();
            }
        }
    }

    /**
     * Loads the database for the location of the signs.
     */
    public void loadDatabase() {
        if (signcrafting && c == null) {
            log.info("Loading database...");
            File db_dir = new File(this.getDataFolder().toPath().toString() + File.separator + "data" + File.separator);
            if (!db_dir.exists()) {
                if (!db_dir.mkdir()) {
                    log.warning("Could not create 'data' folder! Please create it manually and restart the server!");
                    return;
                }
            }
            SQLite sqlite = new SQLite(this, "data" + File.separator + "signs.db");
            c = sqlite.openConnection();
            Statement signlocation = null;
            try {
                signlocation = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (signlocation != null) {
                    signlocation.executeUpdate("CREATE TABLE IF NOT EXISTS signs (location);");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (!signcrafting && c != null) {
            try {
                log.info("Closing database...");
                c.close();
                c = null;
                log.info("Database successfully closed.");
            } catch (Exception e) {
                log.info("An Error occured! Error:");
                e.printStackTrace();
                plog.warn(e.getMessage(), true);
            }
        }
    }

    /**
     * Gives you a colored sign ItemStack with a specific amount.
     *
     * @param amount Amount of signs.
     * @return A colored sign ItemStack.
     */
    public ItemStack getSign(int amount) {
        ItemStack is = new ItemStack(Material.SIGN, amount);
        ItemMeta isim = is.getItemMeta();
        List<String> l = new ArrayList<>();
        l.clear();
        l.add(Message.replaceColors(lang.getLang("signlore")));
        isim.setDisplayName(Message.replaceColors(lang.getLang("signname")));
        isim.setLore(l);
        is.setItemMeta(isim);
        return is;
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
        eco = rsp.getProvider();
        return eco != null;
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
                log.info("Metrics: Failed to submit the stats :-(");
                e.printStackTrace();
                plog.warn(e.getMessage(), true);
            }
        }
    }

    /**
     * Checks for plugin updates.
     */
    private void checkUpdates() {
        if (getConfig().getBoolean("updatecheck")) {
            final PluginDescriptionFile plugin = getDescription();
            this.getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
                @Override
                public void run() {
                    Updater updater = new Updater(plugin.getVersion());
                    UpdateResult result = updater.checkForUpdate();
                    log.info("Checking for Updates...");
                    if (result == UpdateResult.NEEDED) {
                        log.info("A new version is available: v" + updater.getLatestVersion());
                        log.info("Get it from: " + updater.getDownloadUrl());
                        info("New version available: v" + updater.getLatestVersion(), true);
                        updatePlayerMsg = true;
                        updateLink = updater.getDownloadUrl();
                        updateVersion = updater.getLatestVersion();
                    } else if (result == UpdateResult.UNNEEDED) {
                        log.info("No new version available");
                    } else {
                        log.info("Could not check for Updates");
                    }
                }
            });
        }
    }

    /**
     * Logs with INFO level.
     *
     * @param msg    Message to log.
     * @param toFile Log it to file?
     */
    public void info(String msg, boolean toFile) {
        if (plog != null) plog.info(msg, toFile);
    }

    /**
     * Logs with INFO level.
     *
     * @param msg    Message to log.
     * @param toFile Log it to file?
     */
    public void warn(String msg, boolean toFile) {
        if (plog != null) plog.warn(msg, toFile);
    }
}