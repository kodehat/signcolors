/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2022 CodeHat
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.codehat.signcolors.managers

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.config.ConfigKey
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.manager.Manager
import de.codehat.signcolors.util.color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

class ColoredSignManager : Manager {

    private val namespacedKey =
        NamespacedKey(SignColors.instance, SignColors.instance.description.name)

    internal var oldSignAmount = 0
    internal var signCrafting =
        SignColors.instance.config.getBoolean(ConfigKey.SIGN_CRAFTING.toString())
    internal var coloredSignStack = getColoredSignStack()
        private set

    companion object {
        private const val MAX_INGREDIENTS = 9
        private const val MAX_SHAPES = 3

        private const val SHAPE_SIZE_ONE = 1
        private const val SHAPE_SIZE_TWO = 2
        private const val SHAPE_SIZE_THREE = 3
    }

    init {
        setup()
    }

    override fun setup() {
        // Check if crafting colored signs is enabled
        if (signCrafting) {

            // Check if recipe type is set to 'shapeless'
            val recipeType = SignColors.instance.config.getString(ConfigKey.RECIPES_TYPE.toString())
            when (recipeType) {
                "shapeless" -> addShapelessRecipe()
                "shaped" -> addShapedRecipe()
                else ->
                    with(SignColors.instance.logger) {
                        warning(
                            "Unknown config value for 'recipes.type'! Possible values are: 'shaped' and 'shapeless'."
                        )
                        warning("Please change it or you won't be able to craft colored signs!")
                    }
            }
        }
    }

    private fun addShapelessRecipe() {
        val ingredients =
            SignColors.instance.config
                .getList(ConfigKey.RECIPES_SHAPELESS_INGREDIENTS.toString())!!
                .filterIsInstance<String>()
        if (ingredients.size > MAX_INGREDIENTS) {
            SignColors.instance.logger.warning(
                "You have added more than nine crafting items to the config."
            )
            SignColors.instance.logger.warning(
                "Please change it or you won't be able to craft colored signs!"
            )
            return
        }
        val recipeStack = ItemStack(coloredSignStack)
        recipeStack.amount =
            SignColors.instance.config.getInt(ConfigKey.SIGN_AMOUNT_CRAFTING.toString())
        val shapelessRecipe = ShapelessRecipe(namespacedKey, recipeStack)

        ingredients.forEach {
            if (it.contains(":")) {
                val ingredientData = it.split(":")
                val material = Material.getMaterial(ingredientData[0])
                @Suppress("DEPRECATION")
                shapelessRecipe.addIngredient(material!!, ingredientData[1].toInt())
            } else {
                Material.getMaterial(it).apply { shapelessRecipe.addIngredient(this!!) }
            }
        }
        SignColors.instance.server.addRecipe(shapelessRecipe)
    }

    private fun addShapedRecipe() {
        val shapes =
            SignColors.instance.config
                .getList(ConfigKey.RECIPES_SHAPED_CRAFTING_SHAPE.toString())!!
                .filterIsInstance<String>()
        if (shapes.size > MAX_SHAPES) {
            with(SignColors.instance.logger) {
                warning("You have added more than three recipe shapes to the config.")
                warning("Please change it or you won't be able to craft colored signs!")
                return
            }
        }
        val recipeStack = ItemStack(coloredSignStack)
        recipeStack.amount =
            SignColors.instance.config.getInt(ConfigKey.SIGN_AMOUNT_CRAFTING.toString())
        val shapedRecipe = ShapedRecipe(namespacedKey, recipeStack)

        when (shapes.size) {
            SHAPE_SIZE_ONE -> shapedRecipe.shape(shapes[0])
            SHAPE_SIZE_TWO -> shapedRecipe.shape(shapes[0], shapes[1])
            SHAPE_SIZE_THREE -> shapedRecipe.shape(shapes[0], shapes[1], shapes[2])
            else -> {
                with(SignColors.instance.logger) {
                    warning("You defined too many or not enough recipe shapes.")
                    warning("Please change it or you won't be able to craft colored signs!")
                    return
                }
            }
        }
        val ingredientsSection =
            SignColors.instance.config.getConfigurationSection(
                ConfigKey.RECIPES_SHAPED_INGREDIENTS.toString()
            )

        ingredientsSection!!.getKeys(false).forEach {
            val value = ingredientsSection[it].toString()
            if (value.contains(":")) {
                val ingredientData = value.split(":")
                val material = Material.getMaterial(ingredientData[0])
                @Suppress("DEPRECATION")
                shapedRecipe.setIngredient(it.toString()[0], material!!, ingredientData[1].toInt())
            } else {
                Material.getMaterial(value).apply {
                    shapedRecipe.setIngredient(it.toString()[0], this!!)
                }
            }
        }
        SignColors.instance.server.addRecipe(shapedRecipe)
    }

    internal fun removeRecipe() {
        // Not working anymore since MC 1.12, because the recipe iterator is immutable now!
        /*val recipeIterator = SignColors.instance.server.recipeIterator()
        while (recipeIterator.hasNext()) {
            val recipe = recipeIterator.next()

            if (recipe != null && recipe.result.type == Material.SIGN && recipe.result.amount == oldSignAmount
                    && recipe.result.hasItemMeta() && recipe.result.itemMeta.hasLore()) {
                recipeIterator.remove()
            }
        }*/
    }

    private fun getColoredSignStack(amount: Int = 1): ItemStack {
        val signStack = ItemStack(Material.SPRUCE_SIGN, amount)
        val signStackMeta = signStack.itemMeta
        val signStackLore =
            arrayListOf(SignColors.languageConfig.get(LanguageKey.SIGN_LORE)?.color())

        with(signStackMeta) {
            this?.setDisplayName(SignColors.languageConfig.get(LanguageKey.SIGN_NAME)!!.color())
            this?.lore = signStackLore
        }
        signStack.itemMeta = signStackMeta

        return signStack
    }
}
