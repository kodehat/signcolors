<h1 align="center">SignColors</h1>

<div align="center">
  :clipboard::black_nib::pencil2:
</div>
<div align="center">
  <strong>Repository of the SignColors plugin</strong>
</div>
<div align="center">
  A Spigot plugin to let your players use colors on signs
</div>

<br />

<div align="center">
  <!-- Spigot version -->
  <a href="https://www.spigotmc.org/resources/signcolors.6135">
    <img src="https://img.shields.io/badge/spigot-v1.2.0-orange.svg?style=flat-square"
      alt="Spigot version" />
  </a>
  <!-- Build Status -->
  <a href="https://travis-ci.org/choojs/choo">
    <img src="https://img.shields.io/travis/kodehat/SignColors/master.svg?style=flat-square"
      alt="Build Status" />
  </a>
</div>

<div align="center">
  <h3>
    <a href="https://signcolors.codehat.de">
      Website
    </a>
    <span> | </span>
    <a href="https://github.com/choojs/choo/blob/master/.github/CONTRIBUTING.md">
      Contributing
    </a>
  </h3>
</div>

<div align="center">
  <sub>Small plugin made with Kotlin. Built with ❤︎ by
  <a href="https://twitter.com/codehat">CodeHat</a> and
  <a href="https://github.com/kodehat/SignColors/graphs/contributors">
    contributors
  </a>
</div>


## Table of Contents
- [Features](#features)
- [Example](#example)
- [Installation](#installation)
- [Commands](#commands)
- [Permissions](#permissions)
- [See Also](#see-also)
- [Support](#support)

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

## Languages

The plugins comes with built-in *English*, *German* and *Russian* support.

### Adding a language

To add a language, you can copy the `EN.yml` in the `languages` folder and name it like
your language. For example `GB.yml` and change its content as you like. Then change the `language` key
in the `config.yml` to your language file code. In the example above it would be `GB`.

## License

[GPLv3](https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3))
