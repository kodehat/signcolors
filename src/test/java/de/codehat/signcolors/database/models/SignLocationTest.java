/*
 * SignColors is a plugin for Spigot adding colors and formatting to signs.
 * Copyright (C) 2019 CodeHat
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

package de.codehat.signcolors.database.models;

import de.codehat.signcolors.database.daos.SignLocationDao;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.testing.JdbiRule;
import org.junit.Rule;
import org.junit.Test;

public class SignLocationTest {

  @Rule
  public final JdbiRule jdbiRule = JdbiRule.h2().withPlugin(new SqlObjectPlugin());

  @Test
  public void testInsertFindDelete() {
    final SignLocation signLocation = new SignLocation("world", 10, 20, -5);
    final List<SignLocation> signLocations = jdbiRule.getJdbi().withExtension(SignLocationDao.class, dao -> {
      dao.createTable();
      dao.insertBean(signLocation);
      return dao.listSignLocations();
    });
    assertThat(signLocations, CoreMatchers.hasItem(signLocation));
  }
}
