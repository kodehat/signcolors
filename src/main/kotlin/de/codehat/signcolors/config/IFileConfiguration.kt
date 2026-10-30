// Copyright (C) 2024 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.config

import java.io.File

interface IFileConfiguration<T> {
  fun load(file: File): T

  fun save(
    configuration: T,
    file: File,
  )
}
