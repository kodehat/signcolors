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
package de.codehat.spigot.signcolors.listener;

import de.codehat.spigot.signcolors.api.check.Checks;
import de.codehat.spigot.signcolors.api.model.signlocation.SignLocations;
import de.codehat.spigot.signcolors.check.BlockIsSignCheck;
import de.codehat.spigot.signcolors.check.LocationHasWorldCheck;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

  private final Logger logger;
  private final SignLocations signLocations;

  public BlockPlaceListener(Logger logger, SignLocations signLocations) {
    this.logger = logger;
    this.signLocations = signLocations;
  }

  @EventHandler
  public void saveBlock(BlockPlaceEvent event) {
    final Location location = event.getBlock().getLocation();
    final var locationHasWorld = new LocationHasWorldCheck(location);
    final var blockIsSignCheck = new BlockIsSignCheck(event.getBlock());
    if (new Checks(locationHasWorld, blockIsSignCheck).check()) {
      var signLocation =
          signLocations.add(
              locationHasWorld.value().getName(),
              location.getBlockX(),
              location.getBlockY(),
              location.getBlockZ());
      logger.info(() -> "Saved sign with id: " + signLocation.id());
    }
  }
}
