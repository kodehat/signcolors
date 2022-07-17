package de.codehat.spigot.signcolors.model.migration;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import de.codehat.spigot.signcolors.api.model.migration.Migration;
import de.codehat.spigot.signcolors.api.model.migration.Migrations;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class ConstMigrations implements Migrations {

  private final DataSource dbase;

  public ConstMigrations(DataSource dbase) {
    this.dbase = dbase;
  }

  @Override
  public void createTable() {
    new SlMigrations(this.dbase).createTable();
  }

  @Override
  public Iterable<Migration> iterate() {
    try {
      return new JdbcSession(this.dbase)
        .sql("SELECT * FROM migrations")
        .select(
          new ListOutcome<>(
            rset ->
              new ConstMigration(
                new SlMigration(ConstMigrations.this.dbase, rset.getInt(1)),
                rset.getString(2),
                rset.getLong(3),
                rset.getDate(4))));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Migration add(String name, long version, long appliedAt) {
    return new SlMigrations(this.dbase).add(name, version, appliedAt);
  }
}
