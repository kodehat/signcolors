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
package de.codehat.signcolors.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.codehat.signcolors.config.Config;
import de.codehat.signcolors.database.IDatabase;
import de.codehat.signcolors.database.MysqlDatabase;
import de.codehat.signcolors.database.SqliteDatabase;
import java.util.Map;
import javax.inject.Singleton;

@Module
public interface DatabaseModule {

  @Binds
  @IntoMap
  @StringKey(SqliteDatabase.CONFIGURATION_KEY)
  IDatabase provideSqliteDatabase(SqliteDatabase sqliteDatabase);

  @Binds
  @IntoMap
  @StringKey(MysqlDatabase.CONFIGURATION_KEY)
  IDatabase provideMysqlDatabase(MysqlDatabase mysqlDatabase);

  @Provides
  @Singleton
  static IDatabase provideDatabase(Config config, Map<String, IDatabase> allDatabases) {
    return allDatabases.get(config.getDatabaseType());
  }
}
