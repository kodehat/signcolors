package de.codehat.signcolors.managers

import de.codehat.signcolors.SignColors
import de.codehat.signcolors.daos.SignLocationDao
import de.codehat.signcolors.manager.Manager

class ModelManager(plugin: SignColors) : Manager(plugin) {
  internal lateinit var signLocationDao: SignLocationDao
    private set

  init {
    start()
  }

  override fun start() {
    signLocationDao = SignLocationDao(plugin.databaseManager.database.connectionSource)
  }
}
