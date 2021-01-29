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
import org.bukkit.Location;

public interface ISignLocationRepository {
  void insert(Location location);

  void insert(SignLocation signLocation);

  SignLocation find(Location location);

  SignLocation find(String world, int x, int y, int z);

  List<SignLocation> all();
}
