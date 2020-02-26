/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2020 CodeHat
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.codehat.signcolors;

import de.codehat.signcolors.config.Config;
import de.codehat.signcolors.di.DaggerSignColorsBukkitComponent;
import de.codehat.signcolors.di.SignColorsBukkitComponent;
import de.codehat.signcolors.util.SimpleLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class SignColors extends JavaPlugin {

  private SimpleLogger logger;
  private Config config;

  @Override
  public void onEnable() {
    SignColorsBukkitComponent component =
        DaggerSignColorsBukkitComponent.builder().signColors(this).build();

    logger = component.logger();
    config = component.config();

    getServer().getPluginManager().registerEvents(component.playerListener(), this);

    logger.info(
        "Database URL is: jdbc://{0}:{1}/{2} with user {3} and password {4}.",
        config.getDatabaseHost(),
        config.getDatabasePort(),
        config.getDatabaseName(),
        config.getDatabaseUser(),
        config.getDatabasePassword());
  }

  @Override
  public void onDisable() {
    // HINT: Clean-up.
  }
}
