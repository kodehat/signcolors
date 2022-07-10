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
package de.codehat.spigot.signcolors.model;

import de.codehat.spigot.signcolors.api.model.SignLocation;

public final class ConstSignLocation implements SignLocation {

  private final SignLocation origin;
  private final String world;
  private final int x;
  private final int y;
  private final int z;

  public ConstSignLocation(SignLocation signLocation, String world, int x, int y, int z) {
    this.origin = signLocation;
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public long id() {
    return this.origin.id();
  }

  @Override
  public String world() {
    return this.world;
  }

  @Override
  public int x() {
    return this.x;
  }

  @Override
  public int y() {
    return this.y;
  }

  @Override
  public int z() {
    return this.z;
  }
}
