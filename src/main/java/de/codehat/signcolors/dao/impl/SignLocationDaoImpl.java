/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2020 CodeHat
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
package de.codehat.signcolors.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import de.codehat.signcolors.dao.SignLocationDao;
import de.codehat.signcolors.database.Database;
import de.codehat.signcolors.model.SignLocation;
import java.sql.SQLException;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class SignLocationDaoImpl extends BaseDaoImpl<SignLocation, Long>
    implements SignLocationDao {

  public SignLocationDaoImpl(JdbcConnectionSource connectionSource, Class<SignLocation> dataClass)
      throws SQLException {
    super(connectionSource, dataClass);
  }

  public SignLocationDaoImpl(Database database) throws SQLException {
    this(database.getConnectionSource(), SignLocation.class);
  }

  @Override
  public boolean exists(Block block) throws SQLException {
    return exists(block.getLocation());
  }

  @Override
  public boolean exists(Location location) throws SQLException {
    return exists(
        location.getWorld().getName(),
        location.getBlockX(),
        location.getBlockY(),
        location.getBlockZ());
  }

  @Override
  public boolean exists(String world, int x, int y, int z) throws SQLException {
    var queryBuilder =
        queryBuilder()
            .where()
            .eq("world", world)
            .and()
            .eq("x", x)
            .and()
            .eq("y", y)
            .and()
            .eq("z", z);

    return !query(queryBuilder.prepare()).isEmpty();
  }

  @Override
  public void create(Block block) throws SQLException {
    create(block.getLocation());
  }

  @Override
  public void create(Location location) throws SQLException {
    create(
        location.getWorld().getName(),
        location.getBlockX(),
        location.getBlockY(),
        location.getBlockZ());
  }

  @Override
  public void create(String world, int x, int y, int z) throws SQLException {
    create(new SignLocation(world, x, y, z));
  }

  @Override
  public void delete(Block block) throws SQLException {
    delete(block.getLocation());
  }

  @Override
  public void delete(Location location) throws SQLException {
    delete(
        location.getWorld().getName(),
        location.getBlockX(),
        location.getBlockY(),
        location.getBlockZ());
  }

  @Override
  public void delete(String world, int x, int y, int z) throws SQLException {
    var deleteBuilder = deleteBuilder();
    deleteBuilder.where().eq("world", world).and().eq("x", x).and().eq("y", y).and().eq("z", z);

    deleteBuilder.delete();
  }
}
