// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.config

interface IConfig<T : IFileConfiguration<V>, V, K> {
  fun load(extract: Boolean): V?

  fun save()

  fun reload()

  fun getFileName(): String

  fun getConfigurationData(): V?
}
