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
package de.codehat.spigot.signcolors.database;

import de.codehat.spigot.signcolors.api.database.Database;
import javax.sql.DataSource;

public class ConstSqliteDatabase implements Database {

  private final Database origin;
  private final DataSource dbase;

  public ConstSqliteDatabase(Database origin) {
    this.origin = origin;
    this.dbase = origin.dataSource();
  }

  @Override
  public boolean connect() {
    return this.origin.connect();
  }

  @Override
  public DataSource dataSource() {
    return this.dbase;
  }
}
