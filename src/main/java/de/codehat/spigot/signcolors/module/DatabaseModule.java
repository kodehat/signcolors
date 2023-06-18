package de.codehat.spigot.signcolors.module;

import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.file.Path;

@Module
public interface DatabaseModule {

  @Provides
  @Singleton
  static Jdbi provideJdbi(@Named("dataFolder") Path dataFolder) {
    Jdbi jdbi = Jdbi.create("jdbc:sqlite:" + dataFolder.resolve("data.sqlite").toAbsolutePath());
    jdbi.installPlugin(new SqlObjectPlugin());
    return jdbi;
  }

  @Provides
  static Handle provideHandle(Jdbi jdbi) {
    return jdbi.open();
  }
}
