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
package de.codehat.spigot.signcolors.api.check;

import java.util.Arrays;
import java.util.List;

public class Checks implements Check {

  private final List<Check> checks;

  public Checks(Check... checks) {
    this(Arrays.asList(checks));
  }

  public Checks(List<Check> checks) {
    this.checks = checks;
  }

  @Override
  public boolean check() {
    return checks.stream().allMatch(Check::check);
  }
}
