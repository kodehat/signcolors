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
package de.codehat.signcolors.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class SimpleLogger {

  private final Logger logger;

  @Inject
  public SimpleLogger(Logger logger) {
    this.logger = logger;
  }

  public void info(String msg, Object... params) {
    logger.log(Level.INFO, msg, params);
  }

  public void warn(String msg, Object... params) {
    logger.log(Level.WARNING, msg, params);
  }

  public void error(String msg, Throwable thrown) {
    logger.log(Level.SEVERE, msg, thrown);
  }
}
