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
package de.codehat.spigot.signcolors.model;

import de.codehat.spigot.commons.database.model.AbstractModel;
import java.util.Objects;

public class SignLocation extends AbstractModel {

  private Long id;
  private String world;
  private int x;
  private int y;
  private int z;

  public SignLocation() {
    // Required.
  }

  public SignLocation(String world, int x, int y, int z) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public String getTableName() {
    return "sc_sign_locations";
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getWorld() {
    return world;
  }

  public void setWorld(String world) {
    this.world = world;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getZ() {
    return z;
  }

  public void setZ(int z) {
    this.z = z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(world, x, y, z);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof SignLocation)) {
      return false;
    }
    SignLocation other = (SignLocation) obj;
    return Objects.equals(world, other.world)
        && Objects.equals(x, other.x)
        && Objects.equals(y, other.y)
        && Objects.equals(z, other.z);
  }
}
