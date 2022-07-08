package de.codehat.spigot.signcolors.model;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import de.codehat.spigot.signcolors.api.model.SignLocation;
import de.codehat.spigot.signcolors.api.model.SignLocations;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConstSignLocations implements SignLocations {

  private final DataSource dbase;

  public ConstSignLocations(DataSource dbase) {
    this.dbase = dbase;
  }

  @Override
  public Iterable<SignLocation> iterate() {
    try {
      return new JdbcSession(this.dbase)
        .sql("SELECT * FROM sign_locations")
        .select(
          new ListOutcome<>(
            rset -> new ConstSignLocation(
              new SlSignLocation(ConstSignLocations.this.dbase,
                rset.getInt(1)
              ),
              rset.getString(2),
              rset.getInt(3),
              rset.getInt(4),
              rset.getInt(5))
          )
        );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public SignLocation add(String world, int x, int y, int z) {
    return null;
  }
}
