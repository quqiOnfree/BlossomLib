package dev.codedsakura.blossom.lib;

import org.apache.logging.log4j.Level;

public class BlossomLibConfig {
    LoggingConfig logging = new LoggingConfig();

    static class LoggingConfig {
        String consoleLogLevel = Level.INFO.name();
        String fileLogLevel = Level.WARN.name();
        String fileLogPath = "logs/BlossomMods.log";
        boolean fileLogAppend = true;
    }

    TeleportConfig baseTeleportation = new TeleportConfig(true);

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
