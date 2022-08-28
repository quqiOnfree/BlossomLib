# BlossomLib

BlossomLib is a Minecraft Fabric library mod build for the Blossom-series mods.

## Table of contents

- [Dependencies](#dependencies)
- [Config](#config)
- [Commands & their permissions](#commands--their-permissions)
- [Translation keys](#translation-keys)

## Dependencies

* [Fabric API](https://github.com/FabricMC/fabric/tree/1.18.2)
  ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
  / [Modrinth](https://modrinth.com/mod/fabric-api)),
  or [Quilt Standard Libraries](https://github.com/QuiltMC/quilt-standard-libraries) ([Modrinth](https://modrinth.com/mod/qsl))
  if using [Quilt](https://quiltmc.org/)
* [fabric-permissions-api](https://github.com/lucko/fabric-permissions-api) / [LuckPerms](https://luckperms.net/) /
  etc. (Optional)
* [server-translations-api](https://github.com/arthurbambou/Server-Translations) (Embedded)

## Config

This library's config file can be found at `config/BlossomMods/BlossomLib.json`, after running the server with
the mod at least once.

`logging`: [LoggingConfig](#loggingconfig) - settings to do with BlossomMods logging  
`baseTeleportation`: [TeleportationConfig](#teleportationconfig) - default teleportation settings  
`colors`: [Colors](#colors) - text colors  
`dimNameOverrides`: Map<String, String> - a map of dimension name overrides, for example set to
`{ "minecraft:the_nether": "The Underworld" }` to display The Nether as The Underworld  
`enableMC124177Fix`: boolean - Enable a fix for [MC-124177](https://bugs.mojang.com/browse/MC-124177) / CrossDimTPFix

### LoggingConfig

`consoleLogLevel`: String (`OFF`, `FATAL`, `ERROR`, `WARN`, `INFO`, `DEBUG`, `TRACE`, `ALL`) - BlossomMods logging level
for the console output  
`fileLogLevel`: String (`OFF`, `FATAL`, `ERROR`, `WARN`, `INFO`, `DEBUG`, `TRACE`, `ALL`) - BlossomMods logging level
for the file output  
`fileLogPath`: String - where to put the BlossomMods log file  
`fileLogAppend`: boolean - whether to keep the old BlossomMods logs on server startup  
`disableCustomLogger`: boolean - whether to completely bypass the custom logger in case of a mod incompatibility

### TeleportationConfig

`bossBar`: [BossBar](#bossbar) - settings altering the boss bar  
`titleMessage`: [TitleMessage](#titlemessage) - settings altering the title message  
`actionBarMessageEnabled`: boolean - whether to show a message in the action bar when counting down  
`fovEffectBefore`: [CubicBezierCurve](#cubicbeziercurve) - FOV animation before the teleportation  
`fovEffectAfter`: [CubicBezierCurve](#cubicbeziercurve) - FOV animation after the teleportation  
`cancelOnMove`: boolean - whether to cancel the countdown if the player moves  
~~`particleAnimation`: [ParticleAnimation]() - which particle animation to use~~

### BossBar

`enabled`: boolean - whether the boss bar is enabled  
`color`: String (`pink`, `blue`, `red`, `green`, `yellow`, `purple`, `white`) - the color of the boss bar    
`textColor`: String (valid text color) - the color of the boss bar name

### TitleMessage

`titleCounting`: [TitleConfig*](#titleconfig) - settings altering the display of the counting Title  
`subtitleCounting`: [TitleConfig*](#titleconfig) - settings altering the display of the counting Subtitle  
`titleDone`: [TitleConfig](#titleconfig) - settings altering the display of the done Title  
`subitleDone`: [TitleConfig](#titleconfig) - settings altering the display of the done Subtitle

### TitleConfig

`color`: String (valid text color) - color of the title / subtitle  
`modifiers`: String - characters `b` - bold, `i` - italics, `u` - underline, `o` - obfuscated, `s` - strikethrough, can
appear in any order  
*`counterColor`: String (valid text color) - color of the counter in the title / subtitle

### CubicBezierCurve

[Cubic bezier curve generator](https://cubic-bezier.com/)

`enabled`: boolean - whether to even play the animation at all  
`values`: float\[4] - 4 values defining the cubic-bezier curve; 1st & 3rd values **must** be between 1 and 0, weird
things will happen if they're not!  
`start`: float - beginning value  
`end`: float - final value  
`stepCount`: int - how many steps the animation should generate

### Colors

These colors will be only applied to Blossom mods chat responses

`base`: String - the text color in which most text responses will be  
`warn`: String - the text color in which warning messages will be  
`error`: String - the text color in which error messages will be  
`success`: String - the text color in which success messages will be  
`variable`: String - the text color in which variables will be  
`player`: String - the text color in which player names will be  
`command`: String - the text color in which commands will be  
`commandDescription`: String - the text color in which command descriptions will be

## Commands & their permissions

- `/tpcancel` - cancel any ongoing teleport / countdown  
  Permission: `blossom.tpcancel` (default: true)
- `/blossomlib` - library specific commands  
  Permission: `blossom.lib.base-command` (default: OP level 2)
  - `reload-config` - reload and apply config from the config file  
    Permission `blossom.lib.base-command.reload-config` (default: OP level 3)
  - `clear-countdowns [<player>]` - clear all or specific players countdowns  
    Permission `blossom.lib.base-command.clear.countdowns` (default: OP level 2)
  - `clear-cooldowns [<player>] [<type>]` - clear all or specific players specific type cooldowns  
    Permission `blossom.lib.base-command.clear.cooldowns` (default: OP level 2)
  - `debug` - debug commands  
    Permission `blossom.lib.base-command.debug` (default: OP level 4)
    - `countdown <standStill>` - create an arbitrary countdown
    - `teleport <standStill> [<cooldown>] <pos> <rot>` - create an arbitrary teleport
    - `fov <multiplier>` - set player FOV

## Translation keys

only keys with available arguments are shown, for full list, please see
[`src/main/resources/data/blossom/lang/en_us.json`](src/main/resources/data/blossom/lang/en_us.json)

- `blossom.countdown.boss_bar.name`: 1 argument - seconds remaining
- `blossom.countdown.action_bar.counting`: 1 argument - seconds remaining
- `blossom.countdown.title.counting.title`: 1 argument - seconds remaining
- `blossom.countdown.title.counting.subtitle`: 1 argument - seconds remaining
- `blossom.clear-countdowns.one`: 1 argument - player whose countdown was cleared
- `blossom.clear-cooldowns.one`: 1 argument - player whose cooldown was cleared
- `blossom.clear-cooldowns.type`: 2 arguments - player whose cooldown was cleared, type of cleared cooldown
- `blossom.debug.countdown.start`: 1 argument - standstill time
- `blossom.debug.teleport.no-cooldown`: 1 argument - standstill time
- `blossom.debug.teleport.cooldown`: 2 arguments - standstill time, cooldown time
- `blossom.config-reload`: 1 argument - module name
- `blossom.text.command.display`: 1 argument - display text
- `blossom.text.command.plain`: 1 argument - display text/command run
- `blossom.text.command.description`: 2 arguments - display text/command run, description

`zh_cn` (Chinese, Simplified), `zh_tw` (Chinese, Traditional) - added by @BackWheel
