# BlossomLib

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
`actionBarMessageEnabled`: boolean - whether to show a message in the action bar when counting down  
`titleMessageEnabled`: boolean - whether to show a title message when counting down
`fovEffectEnabled`: boolean - whether to make a FOV (field of view) animation when teleporting
`particleAnimation`: [ParticleAnimation]() - which particle animation to use

### BossBar

`enabled`: boolean - whether the boss bar is enabled  
`color`: String (`pink`, `blue`, `red`, `green`, `yellow`, `purple`, `white`) - the color of the boss bar

## Translation keys