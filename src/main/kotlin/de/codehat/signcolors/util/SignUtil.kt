// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.util

import org.bukkit.Material

class SignUtil {
  companion object {
    fun getAllSignMaterials(): List<Material> =
      Material.entries
        .filter {
          it.name.endsWith("_SIGN") && !it.name.startsWith("LEGACY_") &&
            !it.name.contains(
              "_WALL_",
            ) && !it.name.contains("_HANGING_")
        }.toList()
  }
}
