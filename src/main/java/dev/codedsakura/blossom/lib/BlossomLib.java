package dev.codedsakura.blossom.lib;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;


class BlossomLibConfig {
    String fileLogLevel = Level.WARN.name();
    String consoleLogLevel = Level.INFO.name();
    String fileLogPath = "logs/BlossomMods.log";
    boolean fileLogAppend = true;
}

public class BlossomLib implements ModInitializer {
    static final BlossomLibConfig CONFIG = BlossomConfig.load(BlossomLibConfig.class, "BlossomLib.json");
    public static final Logger LOGGER = CustomLogger.createLogger("BlossomLib");

    @Override
    public void onInitialize() {
    }
}
