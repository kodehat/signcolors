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
package de.codehat.signcolors.database.impl;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import de.codehat.signcolors.database.Database;
import de.codehat.signcolors.util.SimpleLogger;
import java.io.File;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class SqliteDatabase implements Database {

  private static final String SQLITE_JDBC_CONNECTION_TEMPLATE = "jdbc:sqlite:%s";

  private final SimpleLogger logger;
  private final File dataFolder;

  @Inject
  public SqliteDatabase(SimpleLogger logger, @Named("dataFolder") File dataFolder) {
    this.logger = logger;
    this.dataFolder = dataFolder;
  }

  @Override
  public JdbcConnectionSource getConnectionSource() {
    try {
      return new JdbcConnectionSource(
          String.format(
              SQLITE_JDBC_CONNECTION_TEMPLATE,
              dataFolder.toPath().resolve("sign_locations.db").toAbsolutePath().toString()));
    } catch (SQLException e) {
      logger.error("Unable to load SQLite driver!", e);
      return null;
    }
  }
}
