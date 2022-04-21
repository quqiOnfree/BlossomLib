package dev.codedsakura.blossom.lib;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;

import java.util.Arrays;

import static dev.codedsakura.blossom.lib.BlossomLib.CONFIG;

public class TextUtils {
    public static MutableText variable(String str) {
        return new LiteralText(str)
                .styled(style -> style.withColor(TextColor.parse(CONFIG.colors.variable)));
    }

    public static MutableText variable(int var) {
        return variable(Integer.toString(var));
    }

    public static MutableText variable(long var) {
        return variable(Long.toString(var));
    }

    public static MutableText variable(float var) {
        return variable(Float.toString(var));
    }

    public static MutableText variable(double var) {
        return variable(Double.toString(var));
    }

    public static MutableText variable(Object var) {
        if (var instanceof PlayerEntity) {
            return player((PlayerEntity) var);
        }
        return variable(var.toString());
    }


    public static MutableText player(PlayerEntity player) {
        return player.getDisplayName().copy()
                .styled(style -> {
                    if (style.getColor() != null && style.getColor().equals(TextColor.parse("white"))) {
                        style.withColor(TextColor.parse(CONFIG.colors.player));
                    }
                    return style;
                });
    }


    public enum Type {ERROR, WARN, INFO, SUCCESS}

    public static MutableText fTranslation(String key, Type t, Object... args) {
        return new TranslatableText(
                key,
                Arrays.stream(args).map(TextUtils::variable).toArray()
        ).styled(style -> {
            switch (t) {
                case ERROR -> style.withColor(TextColor.parse(CONFIG.colors.error));
                case WARN, INFO -> style.withColor(TextColor.parse(CONFIG.colors.base));
                case SUCCESS -> style.withColor(TextColor.parse(CONFIG.colors.success));
            }
            return style;
        });
    }

    public static MutableText fTranslation(String key, Type t) {
        return fTranslation(key, t, new Object[]{});
    }


    public static MutableText translation(String key) {
        return fTranslation(key, Type.INFO);
    }

    public static MutableText translation(String key, Object... args) {
        return fTranslation(key, Type.INFO, args);
    }
}
