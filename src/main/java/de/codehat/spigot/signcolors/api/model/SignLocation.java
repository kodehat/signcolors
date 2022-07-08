package de.codehat.spigot.signcolors.api.model;

public interface SignLocation {
  long id();
  String world();
  int x();
  int y();
  int z();
}
