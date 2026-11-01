# Permissions

## Example permission packages

These are suggestions, not built-in groups. Grant the nodes with your permission manager and adjust them to suit your server. The VIP package below assumes VIPs inherit the player package; otherwise, grant both lists to VIPs.

### Players

Give regular players a selection of colors, access to the code list, and the ability to buy colored signs:

- `signcolors.command.info`
- `signcolors.command.help`
- `signcolors.command.codes`
- `signcolors.buysign.use`
- `signcolors.color.a` — green
- `signcolors.color.b` — aqua
- `signcolors.color.c` — red
- `signcolors.color.e` — yellow
- `signcolors.color.f` — white
- `signcolors.formatting.r` — reset

### VIPs

In addition to the player package, give VIPs all colors and a few formatting options:

- `signcolors.color.*` — all colors
- `signcolors.formatting.l` — bold
- `signcolors.formatting.n` — underline
- `signcolors.formatting.o` — italic

Optionally grant `signcolors.bypass.crafting` if VIPs should be able to color ordinary signs without obtaining colored signs. Grant `signcolors.formatting.*` instead of the individual formatting nodes if VIPs should also have magic and strikethrough.

With the default `crafting.enabled: true`, players without the crafting bypass need colored signs from crafting, buy signs, or an administrator. Buy signs additionally require Vault and a compatible economy provider. See [Usage](usage.md) for details. Neither package grants buy-sign creation, administrative commands, or the blocked-line bypass; reserve those for trusted staff.

## Permission reference

| Permission | Description |
| --- | --- |
| `signcolors.*` | Access to all features; granted to operators by default |
| `signcolors.command.*` | Access to all commands |
| `signcolors.buysign.*` | Create and use `[SC]` buy signs |
| `signcolors.color.*` | Apply all colors on signs |
| `signcolors.formatting.*` | Apply all formatting options on signs |
| `signcolors.bypass.*` | Bypass the crafting requirement and blocked first lines |
| `signcolors.command.info` | Access to `/sc` |
| `signcolors.command.help` | Access to `/sc help` |
| `signcolors.command.reload` | Access to `/sc reload` |
| `signcolors.command.give` | Access to `/sc givesign` |
| `signcolors.command.codes` | Access to `/sc codes` |
| `signcolors.buysign.create` | Create `[SC]` buy signs |
| `signcolors.buysign.use` | Use `[SC]` buy signs |
| `signcolors.bypass.crafting` | Apply codes to ordinary signs when crafting is required |
| `signcolors.bypass.blockedfirstlines` | Bypass blocked sign lines |
| `signcolors.updatemessage` | Receive a notification on joining when an update is available |
| `signcolors.color.0` | Access to *BLACK* color: `&0` |
| `signcolors.color.1` | Access to *DARK BLUE* color: `&1` |
| `signcolors.color.2` | Access to *DARK GREEN* color: `&2` |
| `signcolors.color.3` | Access to *DARK AQUA* color: `&3` |
| `signcolors.color.4` | Access to *DARK RED* color: `&4` |
| `signcolors.color.5` | Access to *DARK PURPLE* color: `&5` |
| `signcolors.color.6` | Access to *GOLD* color: `&6` |
| `signcolors.color.7` | Access to *GRAY* color: `&7` |
| `signcolors.color.8` | Access to *DARK GRAY* color: `&8` |
| `signcolors.color.9` | Access to *BLUE* color: `&9` |
| `signcolors.color.a` | Access to *GREEN* color: `&a` |
| `signcolors.color.b` | Access to *AQUA* color: `&b` |
| `signcolors.color.c` | Access to *RED* color: `&c` |
| `signcolors.color.d` | Access to *LIGHT PURPLE* color: `&d` |
| `signcolors.color.e` | Access to *YELLOW* color: `&e` |
| `signcolors.color.f` | Access to *WHITE* color: `&f` |
| `signcolors.formatting.k` | Access to *MAGIC* formatting: `&k` |
| `signcolors.formatting.l` | Access to *BOLD* formatting: `&l` |
| `signcolors.formatting.m` | Access to *STRIKETHROUGH* formatting: `&m` |
| `signcolors.formatting.n` | Access to *UNDERLINE* formatting: `&n` |
| `signcolors.formatting.o` | Access to *ITALIC* formatting: `&o` |
| `signcolors.formatting.r` | Access to *RESET* formatting: `&r` |
