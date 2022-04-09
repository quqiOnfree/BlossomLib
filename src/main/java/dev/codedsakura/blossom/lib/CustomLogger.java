package dev.codedsakura.blossom.lib;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import static org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory.newConfigurationBuilder;

public class CustomLogger {
    private static final LoggerContext CONTEXT;
    public static final String FILENAME = "logs/blossom.log";

    static {
        Configurator.reconfigure();
        Appender sysOut = LoggerContext.getContext(false).getConfiguration().getAppender("SysOut");
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();

        builder.add(
                builder.newAppender("fileLog", "File")
                        .addAttribute("fileName", FILENAME)
                        .add(builder
                                .newLayout("PatternLayout")
                                .addAttribute("pattern", "[%d{yyyy-MM-dd HH:mm:ss}] [%t/%5level] (%logger{1}): %msg%n%throwable"))
        );

        builder.add(
                builder.newRootLogger(Level.TRACE)
                        .add(builder.newAppenderRef("fileLog"))
        );

        CONTEXT = LoggerContext.getContext(true);
        CONTEXT.setConfiguration(builder.build(false));

        Configuration configuration = CONTEXT.getConfiguration();
        configuration.addAppender(sysOut);
        configuration.getRootLogger().addAppender(sysOut, Level.INFO, null);
    }

    /**
     * A {@link Logger} which writes to both STDOUT and a log file
     *
     * @param name the name of the custom {@link Logger}
     * @return a custom {@link Logger} with the provided name
     */
    public static Logger createLogger(String name) {
        return CONTEXT.getLogger(name);
    }
}
