package dev.codedsakura.blossom.lib;

import dev.codedsakura.blossom.lib.teleport.TeleportConfig;
import org.apache.logging.log4j.Level;

public class BlossomLibConfig {
    public LoggingConfig logging = new LoggingConfig();

    public static class LoggingConfig {
        public String consoleLogLevel = Level.INFO.name();
        public String fileLogLevel = Level.WARN.name();
        public String fileLogPath = "logs/BlossomMods.log";
        public boolean fileLogAppend = true;
    }

    public TeleportConfig baseTeleportation = new TeleportConfig(true);

    public Colors colors = new Colors();

    public static class Colors {
        public String base = "light_purple";
        public String warn = "yellow";
        public String error = "red";
        public String success = "green";
        public String variable = "gold";
        public String player = "aqua";
        public String command = "gold";
        public String commandDescription = "white";
    }
}
