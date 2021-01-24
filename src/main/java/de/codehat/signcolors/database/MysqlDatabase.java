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
package de.codehat.signcolors.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import de.codehat.signcolors.config.Config;
import de.codehat.signcolors.util.SimpleLogger;
import java.sql.SQLException;
import javax.inject.Inject;

public class MysqlDatabase implements IDatabase {

  public static final String CONFIGURATION_KEY = "mysql";

  private static final String MYSQL_JDBC_CONNECTION_TEMPLATE =
      "jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false&autoReconnect=true";

  private final SimpleLogger logger;
  private final Config config;

  @Inject
  public MysqlDatabase(SimpleLogger logger, Config config) {
    this.logger = logger;
    this.config = config;
  }

  @Override
  public String getConfigurationKey() {
    return CONFIGURATION_KEY;
  }

  @Override
  public JdbcConnectionSource getConnectionSource() {
    try {
      return new JdbcConnectionSource(
          String.format(
              MYSQL_JDBC_CONNECTION_TEMPLATE,
              config.getDatabaseHost(),
              config.getDatabasePort(),
              config.getDatabaseName(),
              config.getDatabaseUser(),
              config.getDatabasePassword()));
    } catch (SQLException e) {
      logger.error("Unable to load MySQL driver!", e);
      return null;
    }
  }
}
