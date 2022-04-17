package dev.codedsakura.blossom.lib;

import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.BossBar.Color;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static dev.codedsakura.blossom.lib.BlossomLib.LOGGER;

public class TeleportUtils {
    private static final ArrayList<CounterRunnable> TASKS = new ArrayList<>();
    private static final String IDENTIFIER = "blossom:standstill";

    static void tick() {
        TASKS.forEach(CounterRunnable::run);
        TASKS.removeIf(CounterRunnable::shouldRemove);
    }

    public static void genericCountdown(@Nullable TeleportConfig customConfig, double standStillTime, ServerPlayerEntity who, Runnable onDone) {
        LOGGER.debug("Create new genericCountdown for {} ({} s)", who.getUuid(), standStillTime);
        MinecraftServer server = who.getServer();
        assert server != null;
        final Vec3d[] lastPos = {who.getPos()};

        final TeleportConfig config = customConfig == null ? BlossomLib.CONFIG.baseTeleportation : customConfig.mergeWithDefaults();

        CommandBossBar commandBossBar = null;
        if (config.bossBar.enabled) {
            commandBossBar = server.getBossBarManager().add(new Identifier(IDENTIFIER), new TranslatableText(""));
            commandBossBar.addPlayer(who);
            commandBossBar.setColor(BossBar.Color.byName(config.bossBar.color));
        }
        who.networkHandler.sendPacket(new TitleFadeS2CPacket(0, 10, 5));
        int standTicks = (int) (standStillTime * 20);

        CommandBossBar finalCommandBossBar = commandBossBar;
        TASKS.add(new CounterRunnable(standTicks, who.getUuid()) {
            @Override
            void run() {
                if (counter == 0) {
                    LOGGER.debug("genericCountdown for {} has ended", player);
                    if (finalCommandBossBar != null) {
                        finalCommandBossBar.removePlayer(who);
                        server.getBossBarManager().remove(finalCommandBossBar);
                    }
                    if (config.actionBarMessageEnabled) {
                        who.sendMessage(new TranslatableText(""), true);
                    }
                    if (config.titleMessageEnabled) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                who.networkHandler.sendPacket(new ClearTitleS2CPacket(true));
                            }
                        }, 500);
                    }
                    onDone.run();
                    counter = -1;
                    return;
                }

                Vec3d pos = who.getPos();
                double dist = lastPos[0].distanceTo(pos);
                if (dist < .05) {
                    if (dist != 0) lastPos[0] = pos;
                    counter--;
                } else {
                    LOGGER.debug("genericCountdown for {} has been reset after {} ticks", player, standTicks);
                    lastPos[0] = pos;
                    counter = standTicks;
                }

                if (finalCommandBossBar != null) {
                    finalCommandBossBar.setPercent((float) counter / standTicks);
                }
                if (config.actionBarMessageEnabled) {
                    who.sendMessage(new TranslatableText(""), true);
                }
                if (config.titleMessageEnabled) {
                    who.networkHandler.sendPacket(new SubtitleS2CPacket(new TranslatableText("")));
                    who.networkHandler.sendPacket(new TitleS2CPacket(new TranslatableText("")));
                }
            }
        });
    }

    public static void cancelCountdowns(UUID player) {
        TASKS.stream()
                .filter(task -> task.player.compareTo(player) == 0)
                .forEach(task -> task.counter = -1);
    }

    public static boolean hasCountdowns(UUID player) {
        return TASKS.stream().anyMatch(task -> task.player.compareTo(player) == 0);
    }

    private static abstract class CounterRunnable {
        int counter;
        UUID player;

        public CounterRunnable(int counter, UUID player) {
            this.counter = counter;
            this.player = player;
        }

        abstract void run();

        boolean shouldRemove() {
            return counter < 0;
        }
    }

    public static class TeleportConfig {
        public TeleportConfig(boolean defaults) {
            bossBar = defaults ? new BossBar(true) : null;
        }

        BossBar bossBar;

        static class BossBar {
            boolean enabled = true;
            String color;

            public BossBar(boolean defaults) {
                color = defaults ? Color.PINK.getName() : null;
            }
        }

        boolean actionBarMessageEnabled = false;

        boolean titleMessageEnabled = true;

        boolean fovEffectEnabled = false;

        ParticleAnimation particleAnimation = ParticleAnimation.OFF;

        enum ParticleAnimation {
            OFF
        }

        TeleportConfig mergeWithDefaults() {
            TeleportConfig defaultConf = BlossomLib.CONFIG.baseTeleportation;

            TeleportConfig newOne = new TeleportConfig(false);
            newOne.bossBar = this.bossBar == null ? defaultConf.bossBar : this.bossBar;
            newOne.bossBar.color = newOne.bossBar.color == null ? defaultConf.bossBar.color : newOne.bossBar.color;

            return newOne;
        }
    }
}
