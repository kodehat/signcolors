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
package de.codehat.spigot.signcolors.database.migration;

import de.codehat.spigot.commons.database.migration.AbstractMigration;
import javax.annotation.Nonnull;
import org.springframework.jdbc.core.JdbcTemplate;

public class CreateSignLocationsTableMigration extends AbstractMigration {

  public CreateSignLocationsTableMigration(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Nonnull
  @Override
  public String getSql() {
    return "CREATE TABLE sc_sign_locations("
        + "id INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */,"
        + "world VARCHAR(255) NOT NULL,"
        + "x INTEGER NOT NULL,"
        + "y INTEGER NOT NULL,"
        + "z INTEGER NOT NULL"
        + ")";
  }

  @Override
  public long getVersion() {
    return 2;
  }

  @Override
  public String getName() {
    return "create_sc_sign_locations_table";
  }
}
