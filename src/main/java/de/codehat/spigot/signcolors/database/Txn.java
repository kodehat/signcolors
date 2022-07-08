package de.codehat.spigot.signcolors.database;

import com.jcabi.jdbc.JdbcSession;

import javax.sql.DataSource;
import java.util.concurrent.Callable;

public final class Txn {

  private final DataSource dbase;

  public Txn(DataSource dbase) {
    this.dbase = dbase;
  }

  public <T> T call(Callable<T> callable) throws Exception {
    JdbcSession session = new JdbcSession(this.dbase);
    try {
      session.sql("START TRANSACTION").execute();
      T result = callable.call();
      session.sql("COMMIT").execute();
      return result;
    } catch (Exception e) {
      session.sql("ROLLBACK").execute();
      throw e;
    }
  }
}
