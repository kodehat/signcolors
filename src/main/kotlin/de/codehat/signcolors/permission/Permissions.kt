package de.codehat.signcolors.permission

enum class Permissions(private var permission: String) {
    ALL("signcolors.all"),
    CMD_INFO("signcolors.command.info"),
    CMD_HELP("signcolors.command.help"),
    CMD_RELOAD("signcolors.command.reload"),
    CMD_COLOR_CODES("signcolors.command.colorcodes"),
    CMD_GIVE_SIGN("signcolors.command.givesign"),
    CMD_MIGRATE_DATABASE("signcolors.command.migratedatabase"),
    SPECIAL_SIGN_CREATE("signcolors.specialsign.create"),
    SPECIAL_SIGN_USE("signcolors.specialsign.use"),
    USE_SPECIFIC_COLOR("signcolors.color."),
    USE_SPECIFIC_FORMAT("signcolors.formatting."),
    USE_ALL_COLORS("signcolors.color.all"),
    USE_ALL_FORMATS("signcolors.formatting.all"),
    BYPASS_SIGN_CRAFTING("signcolors.craftsigns.bypass"),
    BYPASS_BLOCKED_FIRST_LINES("signcolors.blockedfirstlines.bypass"),
    SHOW_UPDATE_MESSAGE("signcolors.updatemessage");

    override fun toString() = permission
}
