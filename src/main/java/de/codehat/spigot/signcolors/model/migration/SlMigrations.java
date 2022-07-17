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
package de.codehat.spigot.signcolors.model.migration;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import de.codehat.spigot.signcolors.api.model.migration.Migration;
import de.codehat.spigot.signcolors.api.model.migration.Migrations;
import java.sql.SQLException;
import javax.sql.DataSource;

public class SlMigrations implements Migrations {

  private final DataSource dbase;

  public SlMigrations(DataSource data) {
    this.dbase = data;
  }

  @Override
  public void createTable() {
    try {
      new JdbcSession(this.dbase)
          .sql(
              """
          CREATE TABLE IF NOT EXISTS migrations(
            id INTEGER PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            version INTEGER NOT NULL,
            applied_at INTEGER NOT NULL
          )
          """)
          .execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Iterable<Migration> iterate() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT id FROM migrations")
          .select(new ListOutcome<>(rSet -> new SlMigration(this.dbase, rSet.getInt(1))));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Migration add(String name, long version, long appliedAt) {
    return null;
  }
}
