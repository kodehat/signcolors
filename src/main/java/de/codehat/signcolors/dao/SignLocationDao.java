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
package de.codehat.signcolors.dao;

import com.j256.ormlite.dao.Dao;
import de.codehat.signcolors.model.SignLocation;
import java.sql.SQLException;
import org.bukkit.Location;
import org.bukkit.block.Block;

public interface SignLocationDao extends Dao<SignLocation, Long> {

  /**
   * Checks if the given block exists in the database. Takes the location from the block.
   *
   * @param block the block to check
   * @return true if location of block exists in database, false otherwise
   * @throws SQLException if unable to query database
   */
  boolean exists(Block block) throws SQLException;

  /**
   * Checks if the given location exists in the database.
   *
   * @param location the location check
   * @return true if location exists in database, false otherwise
   * @throws SQLException if unable to query database
   */
  boolean exists(Location location) throws SQLException;

  /**
   * Checks if the given combination of coordinates exists in the database.
   *
   * @param world the world for the x, y, z coordinates
   * @param x the x coordinate
   * @param y the y coordinate
   * @param z the z coordinate
   * @return true if the combination of coordinates exists in database, false otherwise
   * @throws SQLException if unable to query database
   */
  boolean exists(String world, int x, int y, int z) throws SQLException;

  /**
   * Create a entry in the database for the given block. Takes the location from the block.
   *
   * @param block the block whose location is inserted into the database
   */
  void create(Block block) throws SQLException;

  /**
   * Create a entry in the database for the given location.
   *
   * @param location the location that is inserted into the database
   */
  void create(Location location) throws SQLException;

  /**
   * Create a entry in the database for the given coordinates.
   *
   * @param world the world for the x, y, z coordinates
   * @param x the x coordinate
   * @param y the y coordinate
   * @param z the z coordinate
   * @throws SQLException if unable to query database
   */
  void create(String world, int x, int y, int z) throws SQLException;

  /**
   * Deletes the given block from the database. Take the location from the block.
   *
   * @param block the block to delete
   * @throws SQLException if unable to query
   */
  void delete(Block block) throws SQLException;

  /**
   * Deletes the given location from the database.
   *
   * @param location the location to delete
   * @throws SQLException if unable to query
   */
  void delete(Location location) throws SQLException;

  /**
   * Delete the given combination of coordinates from the database.
   *
   * @param world the world for the x, y, z coordinates
   * @param x the x coordinate
   * @param y the y coordinate
   * @param z the z coordinate
   * @throws SQLException if unable to query database
   */
  void delete(String world, int x, int y, int z) throws SQLException;
}
