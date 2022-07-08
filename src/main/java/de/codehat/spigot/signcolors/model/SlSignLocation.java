package de.codehat.spigot.signcolors.model;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import de.codehat.spigot.signcolors.api.model.SignLocation;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class SlSignLocation implements SignLocation {

  private final DataSource dbase;
  private final long number;

  public SlSignLocation(DataSource data, long id) {
    this.dbase = data;
    this.number = id;
  }

  @Override
  public long id() {
    return this.number;
  }

  @Override
  public String world() {
    try {
      return new JdbcSession(this.dbase)
        .sql("SELECT world FROM sign_locations WHERE id = ?")
        .set(this.number)
        .select(new SingleOutcome<>(String.class));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int x() {
    try {
      return new JdbcSession(this.dbase)
        .sql("SELECT x FROM sign_locations WHERE id = ?")
        .set(this.number)
        .select(new SingleOutcome<>(Integer.class));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int y() {
    try {
      return new JdbcSession(this.dbase)
        .sql("SELECT y FROM sign_locations WHERE id = ?")
        .set(this.number)
        .select(new SingleOutcome<>(Integer.class));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int z() {
    try {
      return new JdbcSession(this.dbase)
        .sql("SELECT z FROM sign_locations WHERE id = ?")
        .set(this.number)
        .select(new SingleOutcome<>(Integer.class));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
