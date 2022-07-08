package de.codehat.spigot.signcolors.model;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.SingleOutcome;
import de.codehat.spigot.signcolors.api.model.SignLocation;
import de.codehat.spigot.signcolors.api.model.SignLocations;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class SlSignLocations implements SignLocations {

  private final DataSource dbase;

  public SlSignLocations(DataSource data) {
    this.dbase = data;
  }

  @Override
  public Iterable<SignLocation> iterate() {
    try {
      return new JdbcSession(this.dbase)
        .sql("SELECT id FROM sign_locations")
        .select(
          new ListOutcome<>(
            rSet -> new SlSignLocation(this.dbase,
              rSet.getInt(1))
          )
        );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public SignLocation add(String world, int x, int y, int z) {
    try {
      return new SlSignLocation(this.dbase, new JdbcSession(this.dbase)
        .sql("INSERT INTO sign_locations (world, x, y, z) VALUES (?, ?, ?, ?)")
        .set(world).set(x).set(y).set(z)
        .insert(new SingleOutcome<>(Integer.class)));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
