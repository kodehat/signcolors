/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.manager;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.util.Message;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SignManager {

    /*
     * ---------------------------------------------------------
     *  Public Variables
     * ---------------------------------------------------------
     */

    // Old crafting sign recipe amount.
    public int oldSignAmount = 0;

    // ItemStack for colored signs.
    public ItemStack coloredSignStack;

    /*
     * ---------------------------------------------------------
     *  Private Variables
     * ---------------------------------------------------------
     */

    // Plugin instance.
    private SignColors plugin_;

    // Item lores for colored signs.
    private List<String> signLores_ = new ArrayList<>();

    public SignManager(SignColors signColors) {
        this.plugin_ = signColors;
    }

    /**
     * Creates the recipe for the colored signs or recreates it.
     */
    public void setupColoredSigns() {
        this.coloredSignStack = coloredSignStack();
        if (this.plugin_.getConfig().getBoolean("signcrafting")) {
            if (this.plugin_.getConfig().getString("recipetype").equals("shapeless")) {
                @SuppressWarnings("unchecked")
                List<String> ingredients = (List<String>) this.plugin_.getConfig().getList("recipes.shapeless.ingredients");
                if (ingredients.size() > 9) {
                    this.plugin_.getLogger().warning("You added more than nine crafting items to the config!");
                    this.plugin_.getLogger().warning("Please change it or you will not be able to craft colored signs!");
                    return;
                }
                ShapelessRecipe sr = new ShapelessRecipe(coloredSignStack(this.plugin_.getConfig().getInt("signamount.crafting")));
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
                this.plugin_.getServer().addRecipe(sr);
                this.plugin_.isSignCrafting = true;
            } else if (this.plugin_.getConfig().getString("recipetype").equals("shaped")) {
                @SuppressWarnings("unchecked")
                List<String> shape = (List<String>) this.plugin_.getConfig().getList("recipes.shaped.craftingshape");
                if (shape.size() > 3) {
                    this.plugin_.getLogger().warning("You added more than three recipe shapes to the config!");
                    this.plugin_.getLogger().warning("Please change it or you will not be able to craft colored signs!");
                    return;
                }
                ShapedRecipe sr = new ShapedRecipe(coloredSignStack(this.plugin_.getConfig().getInt("signamount.crafting")));
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
                        this.plugin_.getLogger().warning("You defined too many or no recipe shapes!");
                        this.plugin_.getLogger().warning("Please change it or you will not be able to craft colored signs!");
                        return;
                }
                ConfigurationSection ingredients = this.plugin_.getConfig().getConfigurationSection("recipes.shaped.ingredients");
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
                this.plugin_.getServer().addRecipe(sr);
                this.plugin_.isSignCrafting = true;
            } else {
                this.plugin_.getLogger().warning("Unknown config value of 'recipetype'! Possible values are: 'shaped' and 'shapeless'.");
                this.plugin_.getLogger().warning("Please change it or you will not be able to craft colored signs!");
            }
        } else {
            this.plugin_.isSignCrafting = false;
        }
    }

    /**
     * The ItemStack for colored signs.
     *
     * @return The ItemStack for colored signs.
     */
    private ItemStack coloredSignStack() {
        ItemStack i = new ItemStack(Material.SIGN, this.plugin_.getConfig().getInt("signamount.sc_sign"));
        ItemMeta im = i.getItemMeta();
        this.signLores_.clear();
        this.signLores_.add(Message.replaceColors(this.plugin_.getLanguageLoader().getLang("signlore")));
        im.setDisplayName(Message.replaceColors(this.plugin_.getLanguageLoader().getLang("signname")));
        im.setLore(this.signLores_);
        i.setItemMeta(im);
        return i;
    }

    /**
     * The ItemStack for colored signs.
     *
     * @param amount Amount if you do not want to use the config 'amount'. Else it can be null.
     * @return The ItemStack for colored signs.
     */
    private ItemStack coloredSignStack(int amount) {
        ItemStack i = new ItemStack(Material.SIGN, amount);
        ItemMeta im = i.getItemMeta();
        this.signLores_.clear();
        this.signLores_.add(Message.replaceColors(this.plugin_.getLanguageLoader().getLang("signlore")));
        im.setDisplayName(Message.replaceColors(this.plugin_.getLanguageLoader().getLang("signname")));
        im.setLore(this.signLores_);
        i.setItemMeta(im);
        return i;
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
        l.add(Message.replaceColors(this.plugin_.getLanguageLoader().getLang("signlore")));
        isim.setDisplayName(Message.replaceColors(this.plugin_.getLanguageLoader().getLang("signname")));
        isim.setLore(l);
        is.setItemMeta(isim);
        return is;
    }

    /**
     * Removes the created colored sign recipe.
     */
    public void removeRecipe() {
        Iterator<Recipe> it = this.plugin_.getServer().recipeIterator();
        Recipe recipe;
        while (it.hasNext()) {
            recipe = it.next();
            if (recipe != null && recipe.getResult().getType() == Material.SIGN && recipe.getResult().getAmount()
                    == oldSignAmount) {
                it.remove();
            }
        }
    }

}
