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
    <a href="https://github.com/kodehat/SignColors/blob/master/.github/CONTRIBUTING.md">
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
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Commands](#commands)
- [Permissions](#permissions)
- [See Also](#see-also)

## Features

- **shiny:** apply colors and formatting on signs
- **"no" java:** completely written in Kotlin
- **restrict features:** high diversity of permissions
- **storage:** full MySQL and SQLite support out of the box

## Installation

1. Download the `signcolors-x.x.x.jar` file.
2. Put it into the server's `plugins` folder.
3. Start the server and stop it right after finish loading.
4. Change the settings in `plugins/SignColors/config.yml` to your likings.
5. Start the server again and have fun!

## Screenshots

<details>
  <summary>Click to view screenshots</summary>

  #### Color and formatting codes

  ![ColorAndFormattingCodes](https://static.codehat.de/pictures/signcolors/color_preview.png "Color and formatting codes")
  
  #### How to create a special sign

  ![HowToSpecialSign](https://static.codehat.de/pictures/signcolors/sign_howto.png "Creation of a special sign")

  #### Look of a special sign
  
  ![LookOfSpeicalSign](https://static.codehat.de/pictures/signcolors/sign_after.png "Look of special sign")

</details>

## Commands

You can also use `/signcolors` instead of `/sc`.

`[ ]`: **required** argument, `< >`: *optional* argument

| Command | Permission | Description |
| --- | :---: | --- |
| `/sc` | `signcolors.command.info` | Get some information about the plugin like the plugin's version |
| `/sc help` | `signcolors.command.help` | List all available commands |
| `/sc reload` | `signcolors.command.reload` | Reload the plugin's configuration file |
| `/sc givesign [player] [amount]` | `signcolors.command.givesign` | Give a *player* a specific *amount* of colored signs |
| `/sc colorcodes` | `signcolors.command.colorcodes` | List all color and formatting codes |

## Permissions

| Permission | Description |
| --- | --- |
| `signcolors.all` | Access to **all** features and command |
| `signcolors.command.all` | Access to **all** commands |
| `signcolors.specialsign.all` | Use and create special `[SC]` signs |
| `signcolors.color.all` | Apply **all** colors on signs |
| `signcolors.formatting.all` | Apply **all** formatting options on signs |
| `signcolors.command.info` | Access to `/sc` |
| `signcolors.command.help` | Access to `/sc help` |
| `signcolors.command.reload` | Access to `/sc reload` |
| `signcolors.command.givesign` | Access to `/sc givesign` |
| `signcolors.command.colorcodes` | Access to `/sc colorcodes` |
| `signcolors.specialsign.create` | Create `[SC]` special signs |
| `signcolors.specialsign.use` | Use `[SC]` special signs |
| `signcolors.signcrafting.bypass` | Apply colors and formatting on normal signs, although `sign_crafting` is activated |
| `signcolors.blockedfirstlines.bypass` | Bypass blocked sign lines |
| `signcolors.updatemessage` | Get a notification when joining, as soon as an update is available |
| `signcolors.color.0` | Access to *BLACK* color: `&0` |
| `signcolors.color.1` | Access to *DARK BLUE* color: `&1` |
| `signcolors.color.2` | Access to *DARK GREEN* color: `&2` |
| `signcolors.color.3` | Access to *DARK AQUA* color: `&3` |
| `signcolors.color.4` | Access to *DARK RED* color: `&4` |
| `signcolors.color.5` | Access to *DARK PURPLE* color: `&5` |
| `signcolors.color.6` | Access to *GOLD* color: `&6` |
| `signcolors.color.7` | Access to *GRAY* color: `&7` |
| `signcolors.color.8` | Access to *DARK GRAY* color: `&8` |
| `signcolors.color.9` | Access to *BLUE* color: `&9` |
| `signcolors.color.a` | Access to *GREEN* color: `&a` |
| `signcolors.color.b` | Access to *AQUA* color: `&b` |
| `signcolors.color.c` | Access to *RED* color: `&c` |
| `signcolors.color.d` | Access to *LIGHT PURPLE* color: `&d` |
| `signcolors.color.e` | Access to *YELLOW* color: `&e` |
| `signcolors.color.f` | Access to *WHITE* color: `&f` |
| `signcolors.formatting.k` | Access to *MAGIC* formatting: `&k` |
| `signcolors.formatting.l` | Access to *BOLD* formatting: `&l` |
| `signcolors.formatting.m` | Access to *STRIKETHROUGH* formatting: `&m` |
| `signcolors.formatting.n` | Access to *UNDERLINE* formatting: `&n` |
| `signcolors.formatting.o` | Access to *ITALIC* formatting: `&o` |
| `signcolors.formatting.r` | Access to *RESET* formatting: `&r` |

## Languages

The plugins comes with built-in *English*, *German* and *Russian* language support.

### Adding a custom language

1. Copy the `EN.yml` in the `languages` folder and name it like your language (e.g. `FR.yml`).
2. Change the values in the file to your liking.
3. Set the `language` key in the `config.yml` to your language file code (e.g. `FR` from the example above).

## See Also

For more information about color/formatting codes, take a look [here](https://minecraft.gamepedia.com/Formatting_codes).

The style of this README was inspired by [choojs/choo](https://github.com/choojs/choo).

## License

[GPLv3](https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3))
