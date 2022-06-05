package dev.codedsakura.blossom.lib;

import net.minecraft.text.*;

import static dev.codedsakura.blossom.lib.BlossomLib.CONFIG;

public class CommandTextBuilder {
    private String commandDisplay;
    private String commandRun;
    private Text description = null;
    private boolean suggest = true;

    public CommandTextBuilder(String command) {
        this.commandDisplay = command;
        this.commandRun = command;
    }

    public CommandTextBuilder setCommandDisplay(String commandDisplay) {
        this.commandDisplay = commandDisplay;
        return this;
    }

    public CommandTextBuilder setCommandRun(String commandRun) {
        this.commandRun = commandRun;
        return this;
    }

    public CommandTextBuilder setSuggest(boolean suggest) {
        this.suggest = suggest;
        return this;
    }

    public CommandTextBuilder setDescription(Text description) {
        this.description = description;
        return this;
    }

    Text asColoredText() {
        return new TranslatableText(
            "blossom.text.command.display",
            commandDisplay
        ).styled(style -> style
            .withColor(TextColor.parse(CONFIG.colors.command))
            .withHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new TranslatableText(
                    "blossom.text.command.description",
                    new LiteralText(commandDisplay)
                        .styled(descriptionStyle -> descriptionStyle.withColor(TextColor.parse(CONFIG.colors.command))),
                    description.shallowCopy()
                        .styled(descriptionStyle -> descriptionStyle.withColor(TextColor.parse(CONFIG.colors.commandDescription)))
                )))
            .withClickEvent(new ClickEvent(
                suggest ? ClickEvent.Action.SUGGEST_COMMAND : ClickEvent.Action.RUN_COMMAND,
                commandRun
            )));
    }
}
