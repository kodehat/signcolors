/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2020 CodeHat
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
package de.codehat.signcolors.component.database;

import com.dieselpoint.norm.Database;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

  @Test
  public void testSQLiteDatabaseConnection() throws IOException {
    final String tempFile = Files.createTempFile(null, null).toAbsolutePath().toString();
    final String url = "jdbc:sqlite:" + tempFile + ".db";
    Database db = new Database();
    db.setJdbcUrl(url);
    System.out.println(url);
  }
}
