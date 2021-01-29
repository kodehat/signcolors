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
package de.codehat.spigot.signcolors.repository;

import de.codehat.spigot.signcolors.model.SignLocation;
import java.util.List;
import javax.inject.Inject;
import org.bukkit.Location;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public final class SignLocationRepository implements ISignLocationRepository {

  private final JdbcTemplate jdbcTemplate;

  @Inject
  public SignLocationRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    createTable();
  }

  private void createTable() {
    String sql =
        "CREATE TABLE IF NOT EXISTS sc_sign_locations("
            + "id INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */,"
            + "world VARCHAR(255) NOT NULL,"
            + "x INTEGER NOT NULL,"
            + "y INTEGER NOT NULL,"
            + "z INTEGER NOT NULL"
            + ")";
    jdbcTemplate.execute(sql);
  }

  @Override
  public void insert(Location location) {
    insert(
        new SignLocation(
            location.getWorld().getName(),
            location.getBlockX(),
            location.getBlockY(),
            location.getBlockZ()));
  }

  @Override
  public void insert(SignLocation signLocation) {
    String sql = "INSERT INTO sc_sign_locations(world, x, y, z) VALUES (?, ?, ?, ?)";
    Object[] params =
        new Object[] {
          signLocation.getWorld(), signLocation.getX(), signLocation.getY(), signLocation.getZ()
        };
    jdbcTemplate.update(sql, params);
  }

  @Override
  public SignLocation find(Location location) {
    return null;
  }

  @Override
  public SignLocation find(String world, int x, int y, int z) {
    String sql = "SELECT * FROM sc_sign_locations WHERE world = ? AND x = ? AND y = ? AND z = ?";
    Object[] params = new Object[] {world, x, y, z};
    return jdbcTemplate.queryForObject(sql, SignLocation.class, params);
  }

  @Override
  public List<SignLocation> all() {
    String sql = "SELECT * FROM sc_sign_locations";
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SignLocation.class));
  }
}
