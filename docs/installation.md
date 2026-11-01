# Installation

This guide covers the initial installation and setup. The current build targets Spigot 26.3 and requires Java 25.

## Download

<div class="warning">
Download a plugin version compatible with your server version.
</div>

Download the plugin JAR from [SpigotMC](https://www.spigotmc.org/resources/signcolors.6135/history) or [GitHub Releases](https://github.com/kodehat/signcolors/releases). Put it in the server's `plugins` directory.

## Start the server

Start the server. Once it has finished loading, you should find a `SignColors` folder in the `plugins` directory.

## Configure

Edit `plugins/SignColors/config.yml` as needed, then use `/sc reload` or restart the server. See [Configuration](configuration.md) for the available settings.

To use buy signs, install Vault and a compatible economy provider. Vault is optional for other features.
