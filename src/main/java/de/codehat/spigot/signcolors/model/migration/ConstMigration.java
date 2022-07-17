package de.codehat.spigot.signcolors.model.migration;

import de.codehat.spigot.signcolors.api.model.migration.Migration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class ConstMigration implements Migration {

  private final Migration origin;
  private final String name;
  private final long version;
  private final LocalDateTime appliedAt;

  public ConstMigration(Migration origin, String name, long version, Date appliedAt) {
    this.origin = origin;
    this.name = name;
    this.version = version;
    this.appliedAt = LocalDateTime.ofInstant(appliedAt.toInstant(), ZoneId.systemDefault());
  }

  @Override
  public long id() {
    return this.origin.id();
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public long version() {
    return this.version;
  }

  @Override
  public LocalDateTime appliedAt() {
    return this.appliedAt;
  }
}
