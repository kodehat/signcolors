package de.codehat.signcolors.permission

enum class Permissions(private var permission: String) {
    ALL("signcolors.*"),
    CMD_HELP("signcolors.help"),
    CMD_GIVE_SIGN("signcolors.givesign"),
    CMD_COLOR_CODES("signcolors.listcodes"),
    CMD_INFO("signcolors.info"),
    CMD_RELOAD("signcolors.reload"),
    BYPASS_SIGN_CRAFTING("signcolors.craftsigns.bypass"),
    BYPASS_BLOCKED_FIRST_LINES("signcolors.blockedfirstlines.bypass"),
    USE_SPECIFIC_COLOR("signcolors.color."),
    USE_ALL_COLORS("signcolors.colors"),
    SPECIAL_SIGN_CREATE("signcolors.specialsign.create"),
    SPECIAL_SIGN_USE("signcolors.specialsign.use"),
    SHOW_UPDATE_MESSAGE("signcolors.updatemessage");

    fun value(): String = this.permission
}