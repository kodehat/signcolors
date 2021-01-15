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
package de.codehat.signcolors.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import de.codehat.signcolors.dao.SignLocationDao;
import java.util.Objects;

@DatabaseTable(tableName = SignLocation.TABLE_NAME, daoClass = SignLocationDao.class)
public class SignLocation {

  public static final String TABLE_NAME = "sc_sign_locations";

  @DatabaseField(useGetSet = true, generatedId = true, canBeNull = false)
  private Long id;

  @DatabaseField(useGetSet = true, canBeNull = false)
  private String world;

  @DatabaseField(useGetSet = true, canBeNull = false)
  private Integer x;

  @DatabaseField(useGetSet = true, canBeNull = false)
  private Integer y;

  @DatabaseField(useGetSet = true, canBeNull = false)
  private Integer z;

  public SignLocation(String world, Integer x, Integer y, Integer z) {
    this.id = null; // Used for auto id generation of database.
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public SignLocation() {}

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

  public Integer getX() {
    return x;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public Integer getY() {
    return y;
  }

  public void setY(Integer y) {
    this.y = y;
  }

  public Integer getZ() {
    return z;
  }

  public void setZ(Integer z) {
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
