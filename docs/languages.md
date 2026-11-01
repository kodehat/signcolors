# Languages

The plugin comes with built-in *English* and *German* translations. More can be added manually.

Bundled translations have a `translation.version` key. When a newer bundled version is installed, SignColors backs up the existing file in the `translations` folder (with a `.bak` suffix) and replaces it with the new version on startup or `/sc reload`. Existing unversioned bundled files are also backed up and replaced. Copy any customizations back from the backup if needed. Custom language files are not replaced by this translation update process.

## Adding a custom language

1. Copy the `en_US.properties` in the `translations` folder
and name it after your language (e.g. `fr.properties`).
2. Adjust the translation values within the file.
3. Set the `language` key in the `config.yml` to your language file code
(e.g. `fr` from the example above).
4. Reload the plugin or restart the server to make use of the new translations.
