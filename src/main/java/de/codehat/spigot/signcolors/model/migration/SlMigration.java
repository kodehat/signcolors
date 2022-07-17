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
import com.jcabi.jdbc.SingleOutcome;
import de.codehat.spigot.signcolors.api.model.migration.Migration;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.sql.DataSource;

public final class SlMigration implements Migration {

  private final DataSource dbase;
  private final long number;

  public SlMigration(DataSource data, long id) {
    this.dbase = data;
    this.number = id;
  }

  @Override
  public long id() {
    return this.number;
  }

  @Override
  public String name() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT name FROM migrations WHERE id = ?")
          .set(this.number)
          .select(new SingleOutcome<>(String.class));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public long version() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT version FROM migrations WHERE id = ?")
          .set(this.number)
          .select(new SingleOutcome<>(Long.class));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public LocalDateTime appliedAt() {
    try {
      return LocalDateTime.ofInstant(
          new JdbcSession(this.dbase)
              .sql("SELECT appliedAt FROM migrations WHERE id = ?")
              .set(this.number)
              .select(new SingleOutcome<>(Date.class))
              .toInstant(),
          ZoneId.systemDefault());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
