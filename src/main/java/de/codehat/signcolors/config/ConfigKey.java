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
package de.codehat.signcolors.config;

import java.util.List;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Contains all necessary configuration keys of the plug-in with their default values.
 *
 * @author CodeHat
 * @param <V> has to be either {@link String}, {@link List}, an implementation of {@link
 *     ConfigurationSerializable} or primitive type
 */
public final class ConfigKey<V> {

  public static final ConfigKey<String> DATABASE_TYPE = new ConfigKey<>("database.type", "sqlite");
  public static final ConfigKey<String> DATABASE_HOST =
      new ConfigKey<>("database.host", "127.0.0.1");
  public static final ConfigKey<Integer> DATABASE_PORT = new ConfigKey<>("database.port", 3306);
  public static final ConfigKey<String> DATABASE_NAME =
      new ConfigKey<>("database.name", "signcolors");
  public static final ConfigKey<String> DATABASE_USER = new ConfigKey<>("database.user", "steve");
  public static final ConfigKey<String> DATABASE_PASSWORD =
      new ConfigKey<>("database.name", "JustAnExamplePassword");

  private final String key;
  private final V defaultValue;

  private ConfigKey(String key, V defaultValue) {
    this.key = key;
    this.defaultValue = defaultValue;
  }

  public String getKey() {
    return key;
  }

  public V getDefaultValue() {
    return defaultValue;
  }
}
