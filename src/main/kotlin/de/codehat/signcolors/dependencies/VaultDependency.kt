// Copyright (C) 2022 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.dependencies

import de.codehat.signcolors.dependency.Dependency
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.java.JavaPlugin

class VaultDependency(
  plugin: JavaPlugin,
) : Dependency(plugin) {
  var economy: Economy? = null
    private set

  init {
    setupEconomy()
  }

  private fun setupEconomy() {
    if (plugin.server.pluginManager.getPlugin("Vault") == null) {
      return
    }
    economy =
      plugin.server.servicesManager
        .getRegistration(Economy::class.java)
        ?.provider
  }
}
