/*-
 * -\-\-
 * DBeam Core
 * --
 * Copyright (C) 2016 - 2018 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.dbeam.args;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

public class JdbcConnectionUtil {

  private static Map<String, String> driverMapping =
      ImmutableMap.of(
          "postgresql", "org.postgresql.Driver",
          "mysql", "com.mysql.cj.jdbc.Driver",
          "mssql","com.microsoft.sqlserver.jdbc.SQLServerDriver",
          "h2", "org.h2.Driver");
  /**
   * <p>You need to pass some jdbc conction as</p>
   * <li>jdbc:mysql</li>
   * <li>jdbc:sqlserver</li>
   * <li>jdbc:postgresql</li>
   * <p>To have your class class Driver</p>
   * @param url 
   * @return Driver class
   * @throws ClassNotFoundException 
   */
  public static String getDriverClass(final String url) throws ClassNotFoundException {
    final String[] parts = url.split(":", 3);
    Preconditions.checkArgument(
        parts.length > 1 && "jdbc".equals(parts[0]) && driverMapping.get(parts[1]) != null,
        "Invalid jdbc connection URL: %s. Expect one of then jdbc:postgresql or jdbc:mysql, jdbc:sqlserver as prefix.",
        url);
    return Class.forName(driverMapping.get(parts[1])).getCanonicalName();
  }
}
