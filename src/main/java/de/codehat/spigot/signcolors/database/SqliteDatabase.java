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
import org.sqlite.SQLiteDataSource;

public final class SqliteDatabase implements Database {

  private final DataSource dbase;

  public SqliteDatabase(String dbasePath) {
    final SQLiteDataSource dbase = new SQLiteDataSource();
    dbase.setUrl("jdbc:sqlite:%s".formatted(dbasePath));
    this.dbase = dbase;
  }

  @Override
  public boolean connect() {
    // There is no need to connect to the SQLite database.
    return true;
  }

  @Override
  public DataSource dataSource() {
    return dbase;
  }
}
