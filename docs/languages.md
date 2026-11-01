# Languages

The plugin comes with built-in *English* and *German* translations. More can be added manually.

Bundled translations have a `translation.version` key. When a newer bundled version is installed, SignColors backs up the existing file in the `translations` folder (with a `.bak` suffix) and replaces it with the new version on startup or `/sc reload`. Existing unversioned bundled files are also backed up and replaced. Copy any customizations back from the backup if needed. Custom language files are not replaced by this translation update process. However, a configuration version upgrade backs up and removes all YAML and properties files, including custom languages; see [Configuration](configuration.md).

## Adding a custom language

1. Copy `en_US.properties` in the `translations` folder and name the copy after your language (for example, `fr.properties`).
2. Adjust the translation values in the file.
3. Set the `language` key in `config.yml` to your language file code (for example, `fr`).
4. Reload the plugin or restart the server to use the new translations.
