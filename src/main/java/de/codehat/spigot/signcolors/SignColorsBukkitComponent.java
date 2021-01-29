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
package de.codehat.spigot.signcolors;

import dagger.BindsInstance;
import dagger.Component;
import de.codehat.spigot.signcolors.listener.PlayerListener;
import de.codehat.spigot.signcolors.module.DatabaseModule;
import de.codehat.spigot.signcolors.module.RepositoryModule;
import de.codehat.spigot.signcolors.module.SignColorsBukkitModule;
import de.codehat.spigot.signcolors.util.SimpleLogger;
import javax.inject.Singleton;

@Singleton
@Component(modules = {SignColorsBukkitModule.class, DatabaseModule.class, RepositoryModule.class})
public interface SignColorsBukkitComponent {

  PlayerListener playerListener();

  SimpleLogger logger();

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder signColors(SignColors signColors);

    SignColorsBukkitComponent build();
  }
}
