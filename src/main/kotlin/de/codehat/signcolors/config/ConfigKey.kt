package de.codehat.signcolors.config

enum class ConfigKey(private val configKey: String) {
    LANGUAGE("language"),
    COLOR_CHARACTER("color_character"),
    SIGN_CRAFTING("sign_crafting"),
    RECIPES_TYPE("recipes.type"),
    RECIPES_SHAPED_INGREDIENTS("recipes.shaped.ingredients"),
    RECIPES_SHAPED_CRAFTING_SHAPE("recipes.shaped.crafting_shape"),
    RECIPES_SHAPELESS_INGREDIENTS("recipes.shapeless.ingredients"),
    BLOCKED_FIRST_LINES("blocked_first_lines"),
    SIGN_AMOUNT_CRAFTING("sign_amount.crafting"),
    SIGN_AMOUNT_SPECIAL_SIGN("sign_amount.sign"),
    PRICE("price"),
    SOUNDS_CREATE_SPECIAL_SIGN_ENABLED("sounds.create_special_sign.enabled"),
    SOUNDS_CREATE_SPECIAL_SIGN_TYPE("sounds.create_special_sign.type"),
    SOUNDS_CREATE_SPECIAL_SIGN_VOLUME("sounds.create_special_sign.volume"),
    SOUNDS_CREATE_SPECIAL_SIGN_PITCH("sounds.create_special_sign.pitch"),
    SOUNDS_RECEIVE_SIGNS_FROM_SPECIAL_SIGN_ENABLED("sounds.receive_signs_from_special_sign.enabled"),
    SOUNDS_RECEIVE_SIGNS_FROM_SPECIAL_SIGN_TYPE("sounds.receive_signs_from_special_sign.type"),
    SOUNDS_RECEIVE_SIGNS_FROM_SPECIAL_SIGN_VOLUME("sounds.receive_signs_from_special_sign.volume"),
    SOUNDS_RECEIVE_SIGNS_FROM_SPECIAL_SIGN_PITCH("sounds.receive_signs_from_special_sign.pitch"),
    DATABASE_TYPE("database.type"),
    DATABASE_HOST("database.host"),
    DATABASE_PORT("database.port"),
    DATABASE_NAME("database_name"),
    DATABASE_USER("database.user"),
    DATABASE_PASSWORD("database.password"),
    OTHER_UPDATE_CHECK("other.update_check"),
    OTHER_DEBUG("other.debug"),
    OTHER_CONFIG_VERSION("other.config_version");

    override fun toString() = configKey
}