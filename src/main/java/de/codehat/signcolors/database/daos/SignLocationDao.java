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

package de.codehat.signcolors.database.daos;

import de.codehat.signcolors.database.models.SignLocation;
import java.util.List;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SignLocationDao {

  @SqlUpdate("CREATE TABLE sign_location (id INTEGER PRIMARY KEY, world VARCHAR, x INTEGER, y INTEGER, z INTEGER)")
  void createTable();

  @SqlUpdate("INSERT INTO sign_location(world, x, y, z) VALUES (:world, :x, :y, :z)")
  void insertBean(@BindBean SignLocation signLocation);

  @SqlQuery("SELECT * from sign_location")
  List<SignLocation> listSignLocations();
}
