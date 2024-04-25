package de.codehat.signcolors.util

import org.bukkit.Material

class SignUtil {
  companion object {
    fun getAllSignMaterials(): List<Material> {
      return Material.entries.filter {
        it.name.endsWith("_SIGN") && !it.name.startsWith("LEGACY_") &&
          !it.name.contains(
            "_WALL_",
          ) && !it.name.contains("_HANGING_")
      }.toList()
    }
  }
}
