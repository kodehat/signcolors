SignColors [![Build Status](https://ci.codehat.de/buildStatus/icon?job=SignColors)](https://ci.codehat.de/job/SignColors) [![codebeat badge](https://codebeat.co/badges/d4b23321-b230-42b6-91d9-39dd7083817a)](https://codebeat.co/projects/github-com-pixelhash-signcolors-master)
==========

Repository of the SignColors plugin.

## Development Builds

**NOTE!:**
Development builds for SignColors can be found at the server below. These builds may be unstable and may cause errors. Use them at your own risk!
Link: [https://ci.codehat.de/job/SignColors/](https://ci.codehat.de/job/SignColors/ "SignColors Development Builds")

## Commands

For `/sc` you can alternatively use `/signcolors`

- `/sc` - Shows information about SignColors.
- `/sc help` - Shows a list of commands.
- `/sc reload` - Reloads the config.yml.
- `/sc givesign [player] [amount]` - Give a player colored signs.
- `/sc colorcodes` - Shows you a color/format codes list.
- `/sc upgrade` - Upgrades the old database format from version 0.7.1 and earlier to the new database format of version 1.0.0 and later.

## Permissions

- `signcolors.*` - Access to all features.
- `signcolors.color.[color/format]` - Allows you to write with `[color/format]` on signs. For example `singcolors.color.6` = *GOLD*
- `signcolors.colors` - Allows you to write with **all** colors on signs.
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
- `signcolors.upgrade` - Allows you to execute the `/sc upgrade` command.

## Add your own language

To add your own language, you can copy the `EN.yml` in the `languages` folder and name it like
your language. For example `GB.yml` and change its content as you like. Then change the `language` key
in the `config.yml` to your language file code. In the example above it would be `GB`.

## Plugin at SpigotMC

[http://www.spigotmc.org/resources/signcolors.6135/](http://www.spigotmc.org/resources/signcolors.6135/ "SignColors on SpigotMC")

## Status for v1.2.0

 * Add a `/sc migrate` command, to migrate from MySQL to SQLite and vise versa.
 * Add a `/sc dbstatus` command, which shows current database size.
  
 Make suggestions either here or at Spigot.

## License

This project is licensed under GPLv3.
