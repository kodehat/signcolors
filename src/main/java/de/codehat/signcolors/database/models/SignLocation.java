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

import java.util.Objects;
import lombok.Data;
import org.bukkit.Location;

@Data
public class SignLocation {

  protected static final String TABLE_NAME = "sc_sign_locations";

  private long id;
  private String world;
  private int x;
  private int y;
  private int z;

  public SignLocation(final String world, final int x, final int y, final int z) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public SignLocation(final Location location) {
    this(Objects.requireNonNull(location.getWorld()).getName(),
        location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }
}
