SignColors v0.6.* by CodeHat [![Build Status](https://travis-ci.org/Pixelhash/SignColors.svg?branch=master)](https://travis-ci.org/Pixelhash/SignColors)
==============================================================================================

Repository of the SignColors plugin.

## Development Builds

**NOTE!:**
Development builds for SignColors can be found at the below server. These builds may be unstable and can cause errors. Use them at your own risk!
Link: [https://pablo7777.de/signcolors/](https://pablo7777.de/signcolors/ "SignColors Development Builds")

## Commands

For `/sc` you can alternatively use `/signcolors`

- `/sc` - Shows information about SignColors.
- `/sc help` - Shows a list of commands.
- `/sc reload` - Reloads the config.yml.
- `/sc colorsymbol [symbol]` - Changes the color symbol.
- `/sc givesign [player] [amount]` - Give a player colored signs.
- `/sc colorcodes` - Shows you a color/format codes list.

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

## Add your own language

To add your own language, you can copy the `EN.yml` in the `languages` folder and name it like
your language. For example `GB.yml` and change its content as you like. Then change the `language` key
in the `config.yml` to your language file code. In the example above it would be `GB`.

## SpigotMC

Link: [http://www.spigotmc.org/resources/signcolors.6135/](http://www.spigotmc.org/resources/signcolors.6135/ "SignColors on SpigotMC")

## Status for v0.6.* or v0.7

 * Make suggestions.

## License

This project is licensed under GPLv3.
