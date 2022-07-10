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
package de.codehat.spigot.signcolors.model;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.SingleOutcome;
import de.codehat.spigot.signcolors.api.model.SignLocation;
import de.codehat.spigot.signcolors.api.model.SignLocations;
import java.sql.SQLException;
import javax.sql.DataSource;

public final class SlSignLocations implements SignLocations {

  private final DataSource dbase;

  public SlSignLocations(DataSource data) {
    this.dbase = data;
  }

  @Override
  public void createTable() {
    try {
      new JdbcSession(this.dbase)
          .sql(
              """
        CREATE TABLE IF NOT EXISTS sign_locations(
          id INTEGER PRIMARY KEY,
          world VARCHAR(255) NOT NULL,
          x INTEGER NOT NULL,
          y INTEGER NOT NULL,
          z INTEGER NOT NULL
        )
        """)
          .execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Iterable<SignLocation> iterate() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT id FROM sign_locations")
          .select(new ListOutcome<>(rSet -> new SlSignLocation(this.dbase, rSet.getInt(1))));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public SignLocation add(String world, int x, int y, int z) {
    try {
      return new SlSignLocation(
          this.dbase,
          new JdbcSession(this.dbase)
              .sql("INSERT INTO sign_locations (world, x, y, z) VALUES (?, ?, ?, ?)")
              .set(world)
              .set(x)
              .set(y)
              .set(z)
              .insert(new SingleOutcome<>(Long.class)));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
