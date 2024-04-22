package de.codehat.signcolors.manager

import de.codehat.signcolors.SignColors

abstract class Manager(protected val plugin: SignColors) : IManager {
  override fun stop() {
    // Nothing by default.
  }

  override fun reload() {
    stop()
    start()
  }
}
