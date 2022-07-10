/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2022 CodeHat
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
package de.codehat.spigot.signcolors;

import de.codehat.spigot.signcolors.api.database.Database;
import de.codehat.spigot.signcolors.database.SqliteDatabase;
import de.codehat.spigot.signcolors.model.SlSignLocations;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class SignColors extends JavaPlugin {

  private static Logger logger;

  @Override
  public void onEnable() {
    logger = getLogger();
    getDataFolder().mkdirs();
    final Database dBase =
        new SqliteDatabase(getDataFolder().toPath().resolve("data.sqlite").toString());
    final SlSignLocations signs = new SlSignLocations(dBase.dataSource());
    signs.createTable();
    signs.add("world", 1, 2, 3);
    logger.info("Added sign");
    signs.iterate().forEach(s -> logger.info("ID: " + s.id()));
  }

  @Override
  public void onDisable() {
    // HINT: Clean-up.
  }
}
