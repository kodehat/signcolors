package de.codehat.signcolors.permission

enum class Permissions(private var permission: String) {
    ALL("signcolors.*"),
    CMD_HELP("signcolors.help"),
    CMD_COLORCODES("signcolors.listcodes"),
    CMD_INFO("signcolors.info"),
    CMD_RELOAD("signcolors.reload"),
    BYPASS_SIGN_CRAFTING("signcolors.craftsigns.bypass"),
    USE_SPECIFIC_COLOR("signcolors.color."),
    USE_ALL_COLORS("signcolors.colors"),

    CTB_HELP("capturethebanner.help"),
    CTB_RELOAD("cpaturethebanner.reload"),
    ARENA_LIST("capturethebanner.arena.list"),
    ARENA_STATUS("capturethebanner.arena.status"),
    ARENA_CREATE("capturethebanner.arena.create"),
    ARENA_REMOVE("capturethebanner.arena.remove"),
    ARENA_ENABLE("capturethebanner.arena.enable"),
    ARENA_DISABLE("capturethebanner.arena.disable"),
    ARENA_START("capturethebanner.arena.start"),
    ARENA_STOP("capturethebanner.arena.stop"),
    ARENA_HELP("capturethebanner.arena.help"),
    ARENA_JOIN("capturethebanner.arena.join"),
    ARENA_LEAVE("capturethebanner.arena.leave"),
    ARENA_SET("capturethebanner.arena.set"),
    ARENA_INFO("capturethebanner.arena.info"),
    ARENA_READY("capturethebanner.arena.ready"),
    ARENA_WAITING_FOR("capturethebanner.arena.waiting_for"),
    ARENA_CMD_BYPASS("capturethebanner.arena.cmd_bypass"),
    ARENA_ADD_SIGN("capturethebanner.arena.add_sign");

    fun value(): String = this.permission
}