package de.codehat.spigot.signcolors.module;

import dagger.Module;
import dagger.Provides;
import de.codehat.spigot.signcolors.SignColors;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Module
public interface SignColorsModule {

  @Provides
  @Singleton
  static Logger providerLogger(SignColors plugin) {
    return plugin.getLogger();
  }

  @Provides
  @Singleton
  static FileConfiguration provideFileConfiguration(SignColors plugin) {
    return plugin.getConfig();
  }

  @Provides
  @Singleton
  @Named("dataFolder")
  static Path provideDataFolder(Logger logger, SignColors plugin) {
    File dataFolder = plugin.getDataFolder();
    if (dataFolder.mkdirs()) {
      logger.info("Created data folder as it did not exist.");
    }
    return dataFolder.toPath();
  }

}
