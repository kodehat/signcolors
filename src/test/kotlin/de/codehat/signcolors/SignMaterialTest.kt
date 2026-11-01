// Copyright (C) 2026 CodeHat
// SPDX-License-Identifier: GPL-3.0-or-later
package de.codehat.signcolors

import de.codehat.signcolors.util.SignUtil
import org.bukkit.Material
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SignMaterialTest {
  @Test
  fun includesHangingItemsButNotWallBlocks() {
    val materials = SignUtil.getAllSignMaterials()
    assertTrue(Material.OAK_SIGN in materials)
    assertTrue(Material.OAK_HANGING_SIGN in materials)
    assertFalse(Material.OAK_WALL_SIGN in materials)
    assertFalse(Material.OAK_WALL_HANGING_SIGN in materials)
    assertFalse(materials.any { it.name.startsWith("LEGACY_") })
  }

  @Test
  fun allSupportedMaterialsRoundTripThroughBuySignNames() {
    val materials = SignUtil.getAllSignMaterials()
    val names = materials.map(SignUtil::buySignMaterialName)
    assertEquals(names.size, names.toSet().size)
    for (material in materials) {
      assertEquals(material, SignUtil.parseBuySignMaterial(SignUtil.buySignMaterialName(material)), material.name)
      assertEquals(material, SignUtil.parseBuySignMaterial(material.name), material.name)
    }
  }

  @Test
  fun acceptsFullAndShortNamesAndRejectsUnsupportedMaterials() {
    assertEquals(Material.OAK_HANGING_SIGN, SignUtil.parseBuySignMaterial("oak_hanging"))
    assertEquals(Material.OAK_HANGING_SIGN, SignUtil.parseBuySignMaterial(" oak_h "))
    assertEquals("OAK_H", SignUtil.buySignMaterialName(Material.OAK_HANGING_SIGN))
    assertEquals(Material.OAK_SIGN, SignUtil.parseBuySignMaterial("OAK"))
    assertNull(SignUtil.parseBuySignMaterial("OAK_WALL_HANGING_SIGN"))
    assertNull(SignUtil.parseBuySignMaterial("STONE"))
    assertNull(SignUtil.parseBuySignMaterial(""))
  }
}
