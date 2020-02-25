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
package de.codehat.signcolors.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import de.codehat.signcolors.dao.impl.SignLocationDaoImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@DatabaseTable(tableName = SignLocation.TABLE_NAME, daoClass = SignLocationDaoImpl.class)
@NoArgsConstructor
@AllArgsConstructor
public class SignLocation {

  public static final String TABLE_NAME = "sc_sign_locations";

  @DatabaseField(generatedId = true, canBeNull = false)
  private Long id;

  @DatabaseField(canBeNull = false)
  private String world;

  @DatabaseField(canBeNull = false)
  private Integer x;

  @DatabaseField(canBeNull = false)
  private Integer y;

  @DatabaseField(canBeNull = false)
  private Integer z;
}
