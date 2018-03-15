package de.codehat.signcolors.config.abstraction

import de.codehat.signcolors.SignColors
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

abstract class Config(protected val fileName: String) {

    private val file = File(SignColors.instance.dataFolder, this.fileName)
    protected lateinit var cfg: FileConfiguration
        private set

    protected fun setup(extract: Boolean) {
        if (!file.exists()) {
            if (extract) SignColors.instance.saveResource(this.fileName, false)
            else this.file.createNewFile()
        }

        this.cfg = YamlConfiguration.loadConfiguration(this.file)
    }

    open fun save() {
        this.cfg.save(this.file)
    }

    open fun reload() {
        this.setup(false)
    }

    protected fun getFilename(): String {
        return this.file.name.substring(0, this.file.name.lastIndexOf("."))
    }

}