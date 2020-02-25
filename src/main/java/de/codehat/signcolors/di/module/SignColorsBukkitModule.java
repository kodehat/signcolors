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
package de.codehat.signcolors.di.module;

import dagger.Module;
import dagger.Provides;
import de.codehat.signcolors.SignColors;
import java.util.logging.Logger;
import javax.inject.Singleton;

@Module
public interface SignColorsBukkitModule {
  @Provides
  @Singleton
  static Logger provideLogger(SignColors plugin) {
    return plugin.getLogger();
  }
}