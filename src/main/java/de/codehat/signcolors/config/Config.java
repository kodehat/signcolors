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
package de.codehat.signcolors.config;

import javax.inject.Inject;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

  private final FileConfiguration fileConfiguration;

  @Inject
  public Config(FileConfiguration fileConfiguration) {
    this.fileConfiguration = fileConfiguration;
  }

  @SuppressWarnings("unchecked")
  private <V> V getValue(ConfigKey<V> configKey) {
    return (V) fileConfiguration.get(configKey.getKey(), configKey.getDefaultValue());
  }

  public String getDatabaseType() {
    return getValue(ConfigKey.DATABASE_TYPE);
  }

  public String getDatabaseHost() {
    return getValue(ConfigKey.DATABASE_HOST);
  }

  public Integer getDatabasePort() {
    return getValue(ConfigKey.DATABASE_PORT);
  }

  public String getDatabaseName() {
    return getValue(ConfigKey.DATABASE_NAME);
  }

  public String getDatabaseUser() {
    return getValue(ConfigKey.DATABASE_USER);
  }

  public String getDatabasePassword() {
    return getValue(ConfigKey.DATABASE_PASSWORD);
  }

  public boolean isSqlite() {
    return "sqlite".equals(getDatabaseType());
  }

  public boolean isMysql() {
    return "mysql".equals(getDatabaseType());
  }
}
