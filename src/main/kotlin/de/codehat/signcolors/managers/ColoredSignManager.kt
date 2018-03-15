package de.codehat.signcolors.managers

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.language.LanguageKey
import de.codehat.signcolors.manager.abstraction.Manager
import de.codehat.signcolors.util.color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

class ColoredSignManager: Manager {

    private val namespacedKey = NamespacedKey(SignColors.instance, SignColors.instance.description.name)

    internal var oldSignAmount = 0
    internal var signCrafting = SignColors.instance.config.getBoolean("sign_crafting")
    internal var coloredSignStack = getColoredSignStack()
        private set

    companion object {
        const val MAX_INGREDIENTS = 9
        const val MAX_SHAPES = 3
    }

    init {
        setup()
    }

    override fun setup() {
        // Check if crafting colored signs is enabled
        if (signCrafting) {

            // Check if recipe type is set to 'shapeless'
            val recipeType = SignColors.instance.config.getString("recipes.type")
            when (recipeType) {
                "shapeless" -> addShapelessRecipe()
                "shaped" -> addShapedRecipe()
                else -> with(SignColors.instance.logger) {
                    warning("Unknown config value for 'recipes.type'! Possible values are: 'shaped' and 'shapeless'.")
                    warning("Please change it or you won't be able to craft colored signs!")
                }
            }
        }
    }

    private fun addShapelessRecipe() {
        val ingredients = SignColors.instance.config.getList("recipes.shapeless.ingredients").filterIsInstance<String>()
        if (ingredients.size > MAX_INGREDIENTS) {
            with(SignColors.instance.logger) {
                warning("You have added more than nine crafting items to the config.")
                warning("Please change it or you won't be able to craft colored signs!")
                return
            }
        }
        val recipeStack = ItemStack(coloredSignStack)
        recipeStack.amount = SignColors.instance.config.getInt("sign_amount.crafting")
        val shapelessRecipe = ShapelessRecipe(namespacedKey, coloredSignStack)

        ingredients.forEach {
            if (it.contains(":")) {
                val ingredientData = it.split(":")
                val material = Material.getMaterial(ingredientData[0])
                shapelessRecipe.addIngredient(material, ingredientData[1].toInt())
            } else {
                Material.getMaterial(it).apply {
                    shapelessRecipe.addIngredient(this)
                }
            }
        }
        SignColors.instance.server.addRecipe(shapelessRecipe)
    }

    private fun addShapedRecipe() {
        val shapes = SignColors.instance.config.getList("recipes.shaped.crafting_shape").filterIsInstance<String>()
        if (shapes.size > MAX_SHAPES) {
            with(SignColors.instance.logger) {
                warning("You have added more than three recipe shapes to the config.")
                warning("Please change it or you won't be able to craft colored signs!")
                return
            }
        }
        val recipeStack = ItemStack(coloredSignStack)
        recipeStack.amount = SignColors.instance.config.getInt("sign_amount.crafting")
        val shapedRecipe = ShapedRecipe(namespacedKey, recipeStack)

        when(shapes.size) {
            1 -> shapedRecipe.shape(shapes[0])
            2 -> shapedRecipe.shape(shapes[0], shapes[1])
            3 -> shapedRecipe.shape(shapes[0], shapes[1], shapes[2])
            else -> {
                with(SignColors.instance.logger) {
                    warning("You defined too many or not enough recipe shapes.")
                    warning("Please change it or you won't be able to craft colored signs!")
                    return
                }
            }
        }
        val ingredientsSection = SignColors.instance.config.getConfigurationSection("recipes.shaped.ingredients")

        ingredientsSection.getKeys(false).forEach {
            val value = ingredientsSection[it].toString()
            if (value.contains(":")) {
                val ingredientData = value.split(":")
                val material = Material.getMaterial(ingredientData[0])
                shapedRecipe.setIngredient(it.toString()[0], material, ingredientData[1].toInt())
            } else {
                Material.getMaterial(value).apply {
                    shapedRecipe.setIngredient(it.toString()[0], this)
                }
            }
        }
        SignColors.instance.server.addRecipe(shapedRecipe)
    }

    internal fun removeRecipe() {
        val recipeIterator = SignColors.instance.server.recipeIterator()
        while (recipeIterator.hasNext()) {
            val recipe = recipeIterator.next()

            //TODO: Check if only the correct recipe is removed!?
            if (recipe != null && recipe.result.type == Material.SIGN && recipe.result.amount == oldSignAmount) {
                recipeIterator.remove()
            }
        }
    }

    private fun getColoredSignStack(amount: Int = 1): ItemStack {
        val signStack = ItemStack(Material.SIGN, amount)
        val signStackMeta = signStack.itemMeta
        val signStackLore = arrayListOf(SignColors.languageConfig.get(LanguageKey.SIGN_LORE).color())

        with(signStackMeta) {
            displayName = SignColors.languageConfig.get(LanguageKey.SIGN_NAME).color()
            lore = signStackLore
        }
        signStack.itemMeta = signStackMeta

        return signStack
    }
}