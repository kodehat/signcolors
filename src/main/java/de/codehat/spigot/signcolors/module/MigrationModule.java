/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2021 CodeHat
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.codehat.spigot.signcolors.module;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import de.codehat.spigot.commons.database.migration.IMigration;
import de.codehat.spigot.commons.database.migration.manager.IMigrationManager;
import de.codehat.spigot.commons.database.migration.manager.MigrationManager;
import de.codehat.spigot.signcolors.database.migration.CreateSignLocationsTableMigration;
import java.util.Set;
import javax.inject.Singleton;
import org.springframework.jdbc.core.JdbcTemplate;

@Module
public interface MigrationModule {

  @Provides
  @Singleton
  static IMigrationManager provideMigrationManager(
      JdbcTemplate jdbcTemplate, Set<IMigration> migrations) {
    final IMigrationManager migrationManager = new MigrationManager(jdbcTemplate);
    migrations.forEach(migrationManager::addMigration);
    return migrationManager;
  }

  @Provides
  @IntoSet
  static IMigration provideSignLocationsTableMigration(JdbcTemplate jdbcTemplate) {
    return new CreateSignLocationsTableMigration(jdbcTemplate);
  }
}
