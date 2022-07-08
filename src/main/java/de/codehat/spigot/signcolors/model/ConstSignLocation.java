package de.codehat.spigot.signcolors.model;

import de.codehat.spigot.signcolors.api.model.SignLocation;

public final class ConstSignLocation implements SignLocation {

  private final SignLocation origin;
  private final String world;
  private final int x;
  private final int y;
  private final int z;

  public ConstSignLocation(SignLocation signLocation, String world, int x, int y, int z) {
    this.origin = signLocation;
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public long id() {
    return this.origin.id();
  }

  @Override
  public String world() {
    return this.world;
  }

  @Override
  public int x() {
    return this.x;
  }

  @Override
  public int y() {
    return this.y;
  }

  @Override
  public int z() {
    return this.z;
  }
}
