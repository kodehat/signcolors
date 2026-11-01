# Configuration

After the first server start, edit `plugins/SignColors/config.yml`. Apply changes with `/sc reload` (requires `signcolors.command.reload`) or restart the server. See [Languages](languages.md) for translation files.

## General settings

| Setting | Default | Description |
| --- | --- | --- |
| `language` | `en_US` | Translation file name without `.properties`; `en_US` and `de_DE` are included. |
| `prefixCharacter` | `&` | Prefix used before a color or formatting code, for example `&a`. Use one character. |

## Crafting

`crafting.enabled` defaults to `true`. When enabled, players need a colored sign to apply codes unless they have `signcolors.bypass.crafting`. Colored signs can be crafted, bought from an `[SC]` sign, or given with `/sc givesign`. When disabled, codes can be applied to ordinary signs; color and formatting permissions still apply.

| Setting | Default | Description |
| --- | --- | --- |
| `crafting.recipeType` | `shaped` | `shaped` or `shapeless`. |
| `crafting.signType` | `ANY_SIGN` | Result sign material when the recipe uses a specific sign ingredient. |
| `crafting.amount` | `1` | Number of colored signs produced by the recipe. |
| `crafting.ingredients` | See example below | Maps ingredient letters to Bukkit material names. `ANY_SIGN` makes recipes for supported regular and hanging sign types and returns the corresponding type. |
| `crafting.shape` | See example below | Up to three rows for a shaped recipe; spaces represent empty slots. Not used for shapeless recipes. |

The default recipe surrounds a sign with four dyes:

```yaml
crafting:
  enabled: true
  recipeType: shaped
  signType: 'ANY_SIGN'
  amount: 1
  ingredients:
    A: 'RED_DYE'
    B: 'GREEN_DYE'
    C: 'BLUE_DYE'
    D: 'YELLOW_DYE'
    S: 'ANY_SIGN'
  shape:
    - 'A B'
    - ' S '
    - 'C D'
```

For a shapeless recipe, list the ingredients under distinct keys; their letters and `shape` do not determine positions. Bukkit permits at most nine ingredients. If no ingredient is `ANY_SIGN`, set `crafting.signType` to a supported sign material such as `OAK_SIGN`.

Colored sign identity is saved in item and block data, not an external database. Signs and items created before the marker-based version are not automatically migrated.

## Buy signs

Buy signs require Vault and a Vault-compatible economy provider to make purchases. Their creation and use also require separate [permissions](permissions.md). The [usage guide](usage.md#creating-a-buy-sign) explains how to create one.

| Setting | Default | Description |
| --- | --- | --- |
| `buySign.amount` | `10` | Default number of colored signs sold. Must be positive. |
| `buySign.price` | `100.0` | Default price. Must be positive and finite. |
| `buySign.signType` | `OAK_SIGN` | Default supported sign item material, such as `OAK_SIGN` or `OAK_HANGING_SIGN`. |
| `buySign.blockedFirstLines` | `['&6[&3SC&6]', '&1[Buy]']` | Exact first lines blocked unless the player has `signcolors.bypass.blockedfirstlines`. Entries use `&` for color codes. |
| `buySign.creationSound.enabled` | `true` | Play a sound after creating a buy sign. |
| `buySign.creationSound.type` | `BLOCK_ANVIL_USE` | Bukkit sound constant or namespaced sound key. |
| `buySign.creationSound.volume` / `pitch` | `1.0` / `1.0` | Creation sound volume and pitch. |
| `buySign.receivingSound.enabled` | `true` | Play a sound after a purchase. |
| `buySign.receivingSound.type` | `ENTITY_PLAYER_LEVELUP` | Bukkit sound constant or namespaced sound key. |
| `buySign.receivingSound.volume` / `pitch` | `1.0` / `1.0` | Purchase sound volume and pitch. |

Blocked lines are compared after trimming and normalizing applied color codes to `&`. A player with `signcolors.buysign.create` can create the plugin's `[SC]` indicator. Test other blocked entries with the permissions and prefix used on your server.

## Other settings

| Setting | Default | Description |
| --- | --- | --- |
| `other.editableSignColors` | `true` | Show existing color and formatting codes when editing a sign. Restart after changing this setting. |
| `other.updateCheck` | `true` | Check for plugin updates on startup. |
| `other.errorReporting` | `false` | Enable the plugin's error-reporting path; the current implementation logs errors rather than sending them to an external service. |
| `other.debug` | `false` | Enable more verbose log messages. |
| `other.configVersion` | `7` | Internal configuration version; do not change it manually. |
