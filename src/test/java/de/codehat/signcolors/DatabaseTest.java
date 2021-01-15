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
package de.codehat.signcolors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.codehat.signcolors.dao.ISignLocationDao;
import de.codehat.signcolors.dao.SignLocationDao;
import de.codehat.signcolors.model.SignLocation;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DatabaseTest {

  private static JdbcConnectionSource connectionSource;
  private static ISignLocationDao dao;

  @BeforeAll
  public static void setup() throws SQLException {
    connectionSource = new JdbcConnectionSource("jdbc:h2:mem:sign_colors");
    // TODO: Provide H2 database here
    dao = new SignLocationDao(null);

    TableUtils.createTableIfNotExists(connectionSource, SignLocation.class);
  }

  @AfterAll
  public static void cleanup() throws IOException {
    connectionSource.close();
  }

  @Test
  void testInsert() throws SQLException {
    final SignLocation signLocation = new SignLocation("world", 1, 10, 100);
    dao.create(signLocation);

    final SignLocation loadedSignLocation = dao.queryForId(signLocation.getId());
    assertThat(loadedSignLocation, is(signLocation));
  }
}
