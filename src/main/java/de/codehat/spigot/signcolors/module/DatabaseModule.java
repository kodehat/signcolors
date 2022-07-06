/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2021 CodeHat
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
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.codehat.spigot.commons.database.IDatabase;
import de.codehat.spigot.commons.database.MysqlDatabase;
import de.codehat.spigot.commons.database.SqliteDatabase;
import de.codehat.spigot.commons.database.manager.DatabaseManager;
import de.codehat.spigot.commons.database.manager.IDatabaseManager;
import de.codehat.spigot.signcolors.config.MainConfig;
import de.codehat.spigot.signcolors.util.SimpleLogger;
import java.nio.file.Path;
import java.util.Map;
import javax.inject.Named;
import javax.inject.Singleton;
import org.springframework.jdbc.core.JdbcTemplate;

@Module
public interface DatabaseModule {

  @Provides
  @IntoMap
  @StringKey("sqlite")
  static IDatabase provideSqliteDatabase(@Named("dataFolder") Path dataFolder) {
    return new SqliteDatabase(dataFolder.resolve("sign_locations.db"));
  }

  @Provides
  @IntoMap
  @StringKey("mysql")
  static IDatabase provideMysqlDatabase(MainConfig config) {
    return new MysqlDatabase(
        config.getDatabaseHost(),
        config.getDatabasePort(),
        config.getDatabaseName(),
        config.getDatabaseUser(),
        config.getDatabasePassword());
  }

  @Provides
  @Singleton
  static IDatabaseManager provideDatabaseManager(Map<String, IDatabase> databases) {
    return new DatabaseManager(databases);
  }

  @Provides
  @Singleton
  static IDatabase provideActiveDatabase(IDatabaseManager databaseManager, MainConfig config) {
    return databaseManager.getDatabase(config.getDatabaseType());
  }

  @Provides
  @Singleton
  static JdbcTemplate provideJdbcTemplate(IDatabase activeDatabase, SimpleLogger logger) {
    try {
      return new JdbcTemplate(activeDatabase.create());
    } catch (ClassNotFoundException e) {
      logger.error("Unable to find database driver class!", e);
    }
    return null;
  }
}
