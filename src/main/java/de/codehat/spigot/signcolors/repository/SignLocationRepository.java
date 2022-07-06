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

import de.codehat.spigot.commons.repository.AbstractRepository;
import de.codehat.spigot.signcolors.model.SignLocation;
import javax.inject.Inject;
import org.bukkit.Location;
import org.springframework.jdbc.core.JdbcTemplate;

public final class SignLocationRepository extends AbstractRepository<SignLocation> {

  @Inject
  public SignLocationRepository(JdbcTemplate jdbcTemplate) {
    super(SignLocation.class, jdbcTemplate);
  }

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
    getJdbcTemplate().update(sql, params);
  }

  @Override
  public void delete(SignLocation model) {
    String sql =
        String.format(
            "DELETE FROM %s WHERE world = ? AND x = ? AND y = ? AND z = ?", model.getTableName());
    getJdbcTemplate().update(sql, model.getWorld(), model.getX(), model.getY(), model.getZ());
  }

  @Override
  public SignLocation find(Object... params) {
    String sql = "SELECT * FROM sc_sign_locations WHERE world = ? AND x = ? AND y = ? AND z = ?";
    return getJdbcTemplate().queryForObject(sql, SignLocation.class, params);
  }

  public SignLocation find(Location location) {
    return find(
        location.getWorld().getName(),
        location.getBlockX(),
        location.getBlockY(),
        location.getBlockZ());
  }
}
