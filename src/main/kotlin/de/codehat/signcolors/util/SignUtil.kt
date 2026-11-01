// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors.util

import org.bukkit.Material
import java.util.Locale

class SignUtil {
  companion object {
    fun getAllSignMaterials(): List<Material> =
      Material.entries
        .filter {
          it.name.endsWith("_SIGN") && !it.name.startsWith("LEGACY_") && !it.name.contains("_WALL_")
        }.toList()

    /** Compact, reversible name for the limited space on buy signs. */
    fun buySignMaterialName(material: Material): String =
      material.name.removeSuffix("_SIGN").let { name ->
        if (name.endsWith("_HANGING")) name.removeSuffix("_HANGING") + "_H" else name
      }

    /** Accept full Bukkit names, names without _SIGN, and compact hanging-sign names. */
    fun parseBuySignMaterial(name: String): Material? {
      val normalized = name.trim().uppercase(Locale.ROOT)
      val fullName =
        when {
          normalized.endsWith("_SIGN") -> normalized
          normalized.endsWith("_H") -> normalized.removeSuffix("_H") + "_HANGING_SIGN"
          else -> normalized + "_SIGN"
        }
      return Material.getMaterial(fullName)?.takeIf { it in getAllSignMaterials() }
    }
  }
}
