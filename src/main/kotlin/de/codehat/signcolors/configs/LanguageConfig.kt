package de.codehat.signcolors.configs

import de.codehat.signcolors.config.Config
import de.codehat.signcolors.language.LanguageKey

class LanguageConfig(language: String = "en"): Config("lang-$language.yml") {

    init {
        this.setup(true)
    }

    fun get(languageKey: LanguageKey): String {
        return cfg.getString(languageKey.toString())
    }
}