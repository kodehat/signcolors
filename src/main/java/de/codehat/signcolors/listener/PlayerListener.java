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
package de.codehat.signcolors.listener;

import de.codehat.signcolors.SignColors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener extends AbstractListener {

  @Inject
  public PlayerListener(SignColors plugin, Logger logger) {
    super(plugin, logger);
  }

  @EventHandler
  protected void onPlayerJoin(PlayerJoinEvent event) {
    getLogger().log(Level.INFO, "Player {0} joined the game.", event.getPlayer().getName());
  }
}
