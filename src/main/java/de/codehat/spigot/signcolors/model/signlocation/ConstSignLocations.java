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
import com.jcabi.jdbc.ListOutcome;
import de.codehat.spigot.signcolors.api.model.signlocation.SignLocation;
import de.codehat.spigot.signcolors.api.model.signlocation.SignLocations;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ConstSignLocations implements SignLocations {

  private final DataSource dbase;

  public ConstSignLocations(DataSource dbase) {
    this.dbase = dbase;
  }

  @Override
  public void createTable() {
    new SlSignLocations(this.dbase).createTable();
  }

  @Override
  public Iterable<SignLocation> iterate() {
    try {
      return new JdbcSession(this.dbase)
          .sql("SELECT * FROM sign_locations")
          .select(
              new ListOutcome<>(
                  rset ->
                      new ConstSignLocation(
                          new SlSignLocation(ConstSignLocations.this.dbase, rset.getInt(1)),
                          rset.getString(2),
                          rset.getInt(3),
                          rset.getInt(4),
                          rset.getInt(5))));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public SignLocation add(String world, int x, int y, int z) {
    return new SlSignLocations(this.dbase).add(world, x, y, z);
  }
}
