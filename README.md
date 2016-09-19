SignColors v0.8.* by CodeHat [![Build Status](https://ci.codehat.de/buildStatus/icon?job=SignColors)](https://ci.codehat.de/job/SignColors/)
========================================================================================================================================================

Repository of the SignColors plugin.

## Development Builds

**NOTE!:**
Development builds for SignColors can be found at the below server. These builds may be unstable and can cause errors. Use them at your own risk!
Link: [https://ci.codehat.de/job/SignColors/](https://ci.codehat.de/job/SignColors/ "SignColors Development Builds")

## Commands

For `/sc` you can alternatively use `/signcolors`

- `/sc` - Shows information about SignColors.
- `/sc help` - Shows a list of commands.
- `/sc reload` - Reloads the config.yml.
- `/sc colorsymbol [symbol]` - Changes the color symbol.
- `/sc givesign [player] [amount]` - Give a player colored signs.
- `/sc colorcodes` - Shows you a color/format codes list.
- `/sc upgrade` - Upgrades the old database format from version 0.7.1 and earlier to the new database format of version 0.8.0 and later.

## Permissions

- `signcolors.*` - Access to all features.
- `signcolors.color.[color/format]` - Allows you to write with `[color/format]` on signs. For example `singcolors.color.6` = *GOLD*
- `signcolors.colors` - Allows you to write with **all** colors on signs.
- `signcolors.colorsymbol` - Allows you to change the color symbol ingame.
- `signcolors.sign.create` - Allows you to create a [SignColors] sign.
- `signcolors.sign.use` - Allows you to use a [SignColors] sign.
- `signcolors.info` - Allows you to see the SignColors info.
- `signcolors.help` - Allows you to see the `/sc` help page.
- `signcolors.reload` - Allows you to reload the config.yml.
- `signcolors.updatemsg` - Shows you the update message, if an update is available.
- `signcolors.craftsign.bypass` - Allows you to write colors on a normal sign, even if signcrafting is true.
- `signcolors.listcodes` - Allows you to see the color/format codes list.
- `signcolors.givesign` - Allows you to use the `/sc givesign` command.
- `signcolors.blockedfirstlines.bypass` - Allows you to bypass the blocked first lines.

## Add your own language

To add your own language, you can copy the `EN.yml` in the `languages` folder and name it like
your language. For example `GB.yml` and change its content as you like. Then change the `language` key
in the `config.yml` to your language file code. In the example above it would be `GB`.

## SpigotMC

Link: [http://www.spigotmc.org/resources/signcolors.6135/](http://www.spigotmc.org/resources/signcolors.6135/ "SignColors on SpigotMC")

## Status for v0.7.* or v0.8

 * Add possibility to change the costs of a colored sign directly on [SC] sign.
 * Make the Logger more useful.
 * Make suggestions.

## License

This project is licensed under GPLv3.
