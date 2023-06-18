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
package de.codehat.spigot.signcolors.module;

import dagger.Module;
import dagger.Provides;
import de.codehat.spigot.signcolors.SignColors;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Singleton;
import org.bukkit.configuration.file.FileConfiguration;

@Module
public interface SignColorsModule {

  @Provides
  @Singleton
  static Logger provideLogger(SignColors plugin) {
    return plugin.getLogger();
  }

  @Provides
  @Singleton
  static FileConfiguration provideFileConfiguration(SignColors plugin) {
    return plugin.getConfig();
  }

  @Provides
  @Singleton
  @Named("dataFolder")
  static Path provideDataFolder(Logger logger, SignColors plugin) {
    File dataFolder = plugin.getDataFolder();
    if (dataFolder.mkdirs()) {
      logger.info("Created data folder as it did not exist.");
    }
    return dataFolder.toPath();
  }
}
