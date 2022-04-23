# BlossomLib

Dependencies:

* [Fabric API]()
* [fabric-permissions-api]() / [Luckperms]() / etc. (Optional)
* [server-translations-api]() (Embedded)

## Config

`config/BlossomMods/BlossomLib.json`

`logging`: [LoggingConfig](#loggingconfig) - settings to do with BlossomMods logging  
`baseTeleportation`: [BaseTeleportationConfig](#baseteleportationconfig) - default teleportation settings

### LoggingConfig

`consoleLogLevel`: String (`OFF`, `FATAL`, `ERROR`, `WARN`, `INFO`, `DEBUG`, `TRACE`, `ALL`) - BlossomMods logging level
for the console output  
`fileLogLevel`: String (`OFF`, `FATAL`, `ERROR`, `WARN`, `INFO`, `DEBUG`, `TRACE`, `ALL`) - BlossomMods logging level
for the file output  
`fileLogPath`: String - where to put the BlossomMods log file  
`fileLogAppend`: boolean - whether to keep the old BlossomMods logs on server startup

### BaseTeleportationConfig

`bossBar`: [BossBar](#bossbar) - settings altering the boss bar  
`titleMessage`: [TitleMessage](#titlemessage) - settings altering the title message  
`actionBarMessageEnabled`: boolean - whether to show a message in the action bar when counting down  
`fovEffectEnabled`: boolean - whether to make a FOV (field of view) animation when teleporting  
`particleAnimation`: [ParticleAnimation]() - which particle animation to use

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

## Translation keys

only keys with available arguments are shown, to see full list, please
see `src/main/resources/data/blossom/lang/en_us.json`

- `blossom.countdown.boss_bar.name`: 1 argument - seconds remaining
- `blossom.countdown.action_bar.counting`: 1 argument - seconds remaining
- `blossom.countdown.title.counting.title`: 1 argument - seconds remaining
- `blossom.countdown.title.counting.subtitle`: 1 argument - seconds remaining
