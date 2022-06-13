package dev.codedsakura.blossom.lib.teleport;

import dev.codedsakura.blossom.lib.BlossomLib;
import dev.codedsakura.blossom.lib.text.TextUtils;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class TeleportConfig {
    public TeleportConfig(boolean defaults) {
        bossBar = defaults ? new BossBar(true) : null;
        titleMessage = defaults ? new TitleMessage(true) : null;
    }

    public BossBar bossBar;

    public static class BossBar {
        public boolean enabled = true;
        public String color;
        public String textColor;

        public BossBar(boolean defaults) {
            color = defaults ? net.minecraft.entity.boss.BossBar.Color.BLUE.getName() : null;
            textColor = defaults ? "white" : null;
        }

        public BossBar cloneMerge(BossBar old) {
            BossBar newOne = new BossBar(false);

            newOne.color = this.color != null ? this.color : old.color;
            newOne.textColor = this.textColor != null ? this.textColor : old.textColor;

            return newOne;
        }
    }

    public TitleMessage titleMessage;

    public static class TitleMessage {
        public boolean enabled = true;

        public CounterTitleConfig titleCounting;
        public CounterTitleConfig subtitleCounting;
        public TitleConfig titleDone;
        public TitleConfig subtitleDone;

        public static class TitleConfig {
            public String color;
            public String modifiers;

            public TitleConfig(String color, String modifiers) {
                this.color = color;
                this.modifiers = modifiers;
            }

            public Style getStyle(Style style) {
                return style
                        .withColor(TextColor.parse(color))
                        .withBold(modifiers.indexOf('b') >= 0)
                        .withItalic(modifiers.indexOf('i') >= 0)
                        .withUnderline(modifiers.indexOf('u') >= 0)
                        .withObfuscated(modifiers.indexOf('o') >= 0)
                        .withStrikethrough(modifiers.indexOf('s') >= 0);
            }

            public MutableText getText(String key) {
                return TextUtils.translation(key).styled(this::getStyle);
            }
        }

        public static class CounterTitleConfig extends TitleConfig {
            public String counterColor;

            public CounterTitleConfig(String color, String modifiers, String counterColor) {
                super(color, modifiers);
                this.counterColor = counterColor;
            }

            public MutableText getText(String key, int counter) {
                return TextUtils.translation(
                        key,
                        Text.literal(Integer.toString(counter))
                                .styled(style -> style.withColor(TextColor.parse(counterColor)))
                ).styled(this::getStyle);
            }
        }

        public TitleMessage(boolean defaults) {
            titleCounting = defaults ? new CounterTitleConfig("light_purple", "b", "gold") : null;
            subtitleCounting = defaults ? new CounterTitleConfig("red", "i", "gold") : null;
            titleDone = defaults ? new TitleConfig("green", "b") : null;
            subtitleDone = defaults ? new TitleConfig("green", "i") : null;
        }

        public TitleMessage cloneMerge(TitleMessage old) {
            TitleMessage newOne = new TitleMessage(false);

            newOne.titleCounting = this.titleCounting != null ? this.titleCounting : old.titleCounting;
            newOne.subtitleCounting = this.subtitleCounting != null ? this.subtitleCounting : old.subtitleCounting;
            newOne.titleDone = this.titleDone != null ? this.titleDone : old.titleDone;
            newOne.subtitleDone = this.subtitleDone != null ? this.subtitleDone : old.subtitleDone;

            return newOne;
        }
    }

    public boolean actionBarMessageEnabled = false;

    public boolean fovEffectEnabled = false;

    public ParticleAnimation particleAnimation = ParticleAnimation.OFF;

    public boolean allowBack = true;

    public enum ParticleAnimation {
        OFF
    }

    public TeleportConfig cloneMerge() {
        TeleportConfig defaultConf = BlossomLib.CONFIG.baseTeleportation;

        TeleportConfig newOne = new TeleportConfig(false);

        newOne.bossBar = this.bossBar == null ? defaultConf.bossBar : defaultConf.bossBar.cloneMerge(this.bossBar);
        newOne.titleMessage = this.titleMessage == null ? defaultConf.titleMessage : defaultConf.titleMessage.cloneMerge(this.titleMessage);

        newOne.allowBack = this.allowBack && defaultConf.allowBack;

        return newOne;
    }
}
