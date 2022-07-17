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
package de.codehat.spigot.signcolors.model.signlocation;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import de.codehat.spigot.signcolors.api.model.signlocation.SignLocation;
import java.sql.SQLException;
import javax.sql.DataSource;

public final class SlSignLocation implements SignLocation {

  private final DataSource dbase;
  private final long number;

  public SlSignLocation(DataSource data, long id) {
    this.dbase = data;
    this.number = id;
  }

  @Override
  public long id() {
    return this.number;
  }

  @Override
  public String world() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT world FROM sign_locations WHERE id = ?")
          .set(this.number)
          .select(new SingleOutcome<>(String.class));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int x() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT x FROM sign_locations WHERE id = ?")
          .set(this.number)
          .select(new SingleOutcome<>(Long.class))
          .intValue();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int y() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT y FROM sign_locations WHERE id = ?")
          .set(this.number)
          .select(new SingleOutcome<>(Long.class))
          .intValue();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int z() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT z FROM sign_locations WHERE id = ?")
          .set(this.number)
          .select(new SingleOutcome<>(Long.class))
          .intValue();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
