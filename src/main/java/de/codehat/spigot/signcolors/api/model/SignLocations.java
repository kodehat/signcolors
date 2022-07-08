package de.codehat.spigot.signcolors.api.model;

public interface SignLocations {
  Iterable<SignLocation> iterate();
  SignLocation add(String world, int x, int y, int z);
}
