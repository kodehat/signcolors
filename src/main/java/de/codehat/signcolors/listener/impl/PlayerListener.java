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
package de.codehat.signcolors.listener.impl;

import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.dao.SignLocationDao;
import de.codehat.signcolors.listener.AbstractListener;
import de.codehat.signcolors.util.SimpleLogger;
import java.sql.SQLException;
import javax.inject.Inject;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener extends AbstractListener {

  private final SignLocationDao signLocationDao;

  @Inject
  public PlayerListener(SignColors plugin, SimpleLogger logger, SignLocationDao signLocationDao) {
    super(plugin, logger);
    this.signLocationDao = signLocationDao;
  }

  @EventHandler
  protected void onPlayerJoin(PlayerJoinEvent event) {
    getLogger().info("Player {0} joined the game.", event.getPlayer().getName());
  }

  @EventHandler
  protected void onPlayerCreateBlock(BlockPlaceEvent event) {
    final Location blockLocation = event.getBlock().getLocation();
    try {
      signLocationDao.create(blockLocation);
      getLogger()
          .info(
              "Saving block with coordinates World={0}, X={1}, Y={2}, Z={3}.",
              blockLocation.getWorld().getName(),
              blockLocation.getBlockX(),
              blockLocation.getBlockY(),
              blockLocation.getBlockZ());
    } catch (SQLException e) {
      getLogger().error("Unable to save block in db!", e);
    }
  }
}
