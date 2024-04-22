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
import de.codehat.signcolors.configs.TranslationConfigKey
import de.codehat.signcolors.manager.Manager
import de.codehat.signcolors.util.color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

class ColoredSignManager(plugin: SignColors) : Manager(plugin) {
  private val namespacedKey = NamespacedKey(plugin, plugin.description.name)

  internal var craftingEnabled = false
    private set
  internal lateinit var coloredSignStack: ItemStack
    private set

  companion object {
    private const val MAX_INGREDIENTS = 9
    private const val MAX_SHAPES = 3

    private const val SHAPE_SIZE_ONE = 1
    private const val SHAPE_SIZE_TWO = 2
    private const val SHAPE_SIZE_THREE = 3
  }

  init {
    start()
  }

  override fun start() {
    craftingEnabled = plugin.pluginConfig.getCraftingEnabled()!!
    coloredSignStack = getColoredSignStack()
    // Check if crafting colored signs is enabled
    if (craftingEnabled) {
      // Check if recipe type is set to 'shapeless'
      val recipeType = plugin.pluginConfig.getCraftingRecipeType()
      when (recipeType) {
        "shapeless" -> addShapelessRecipe()
        "shaped" -> addShapedRecipe()
        else ->
          with(plugin.logger) {
            warning(
              "Unknown config value for 'recipes.type'! Possible values are: 'shaped' and 'shapeless'.",
            )
            warning("Please change it or you won't be able to craft colored signs!")
          }
      }
    }
  }

  override fun stop() {
    removeRecipe()
  }

  private fun addShapelessRecipe() {
    val ingredients =
      plugin.pluginConfig
        .getCraftingIngredients()
        ?.getValues(false)
        ?.values
        ?.filterIsInstance<String>()
    if (ingredients?.size!! > MAX_INGREDIENTS) {
      plugin.logger.warning("You have added more than nine crafting items to the config.")
      plugin.logger.warning("Please change it or you won't be able to craft colored signs!")
      return
    }
    val recipeStack = ItemStack(coloredSignStack)
    recipeStack.amount = plugin.pluginConfig.getCraftingAmount() ?: 1
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
    plugin.server.addRecipe(shapelessRecipe)
  }

  private fun addShapedRecipe() {
    val shapes = plugin.pluginConfig.getCraftingShape()
    if (shapes!!.size > MAX_SHAPES) {
      with(plugin.logger) {
        warning("You have added more than three recipe shapes to the config.")
        warning("Please change it or you won't be able to craft colored signs!")
        return
      }
    }
    val recipeStack = ItemStack(coloredSignStack)
    recipeStack.amount = plugin.pluginConfig.getCraftingAmount() ?: 1
    val shapedRecipe = ShapedRecipe(namespacedKey, recipeStack)

    when (shapes.size) {
      SHAPE_SIZE_ONE -> shapedRecipe.shape(shapes[0])
      SHAPE_SIZE_TWO -> shapedRecipe.shape(shapes[0], shapes[1])
      SHAPE_SIZE_THREE -> shapedRecipe.shape(shapes[0], shapes[1], shapes[2])
      else -> {
        with(plugin.logger) {
          warning("You defined too many or not enough recipe shapes.")
          warning("Please change it or you won't be able to craft colored signs!")
          return
        }
      }
    }
    val ingredientsSection = plugin.pluginConfig.getCraftingIngredients()

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
    plugin.server.addRecipe(shapedRecipe)
  }

  internal fun removeRecipe() {
    plugin.server.removeRecipe(namespacedKey)
  }

  private fun getColoredSignStack(amount: Int = 1): ItemStack {
    val signType = Material.valueOf(plugin.pluginConfig.getCraftingSignType()!!)
    val signStack = ItemStack(signType, amount)
    val signStackMeta = signStack.itemMeta
    val signStackLore =
      arrayListOf((plugin.getTranslation()?.t(TranslationConfigKey.BUYSIGN_LORE))?.color())

    with(signStackMeta) {
      this?.setDisplayName(
        (plugin.getTranslation()?.t(TranslationConfigKey.BUYSIGN_NAME))?.color(),
      )
      this?.lore = signStackLore
    }
    signStack.itemMeta = signStackMeta

    return signStack
  }
}
