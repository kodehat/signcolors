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
package de.codehat.signcolors.di.module;

import com.j256.ormlite.table.TableUtils;
import dagger.Module;
import dagger.Provides;
import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.config.Config;
import de.codehat.signcolors.dao.SignLocationDao;
import de.codehat.signcolors.dao.impl.SignLocationDaoImpl;
import de.codehat.signcolors.database.Database;
import de.codehat.signcolors.database.impl.SqliteDatabase;
import de.codehat.signcolors.model.SignLocation;
import de.codehat.signcolors.util.SimpleLogger;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Singleton;
import org.bukkit.configuration.file.FileConfiguration;

@Module
public interface SignColorsBukkitModule {
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
  static File provideDataFolder(SimpleLogger logger, SignColors plugin) {
    File dataFolder = plugin.getDataFolder();
    if (dataFolder.mkdirs()) {
      logger.warn("Created data folder as it did not exist!");
    }
    return dataFolder;
  }

  @Provides
  @Singleton
  static Database provideDatabase(Config config, SqliteDatabase sqliteDatabase) {
    // TODO: Create a hook or something different for initial table creation.
    try {
      TableUtils.createTableIfNotExists(sqliteDatabase.getConnectionSource(), SignLocation.class);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (config.isSqlite()) {
      return sqliteDatabase;
    }
    return null;
  }

  @Provides
  @Singleton
  static SignLocationDao provideSignLocationDao(SimpleLogger logger, Database database) {
    try {
      return new SignLocationDaoImpl(database);
    } catch (SQLException e) {
      logger.error("Unable to provide Dao for SignLocation model!", e);
      return null;
    }
  }
}
