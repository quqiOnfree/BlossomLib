package dev.codedsakura.blossom.lib;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RotationArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.util.math.Vec2f;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;

import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


class BlossomLibConfig {
    LoggingConfig logging = new LoggingConfig();

    static class LoggingConfig {
        String consoleLogLevel = Level.INFO.name();
        String fileLogLevel = Level.WARN.name();
        String fileLogPath = "logs/BlossomMods.log";
        boolean fileLogAppend = true;
    }

    TeleportConfig baseTeleportation = new TeleportConfig(true);

    Colors colors = new Colors();

    static class Colors {
        String base = "light_purple";
        String error = "red";
        String success = "green";
        String variable = "gold";
        String player = "aqua";
    }
}

public class BlossomLib implements ModInitializer {
    static BlossomLibConfig CONFIG = BlossomConfig.load(BlossomLibConfig.class, "BlossomLib.json");
    public static final Logger LOGGER = CustomLogger.createLogger("BlossomLib");

    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(_server -> TeleportUtils.tick());

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
                dispatcher.register(literal("blossomlib")
                        .requires(Permissions.require("blossom.lib.base-command", 2))
                        .then(literal("reload-config")
                                .requires(Permissions.require("blossom.lib.base-command.reload-config", 3))
                                .executes(ctx -> {
                                    CONFIG = BlossomConfig.load(BlossomLibConfig.class, "BlossomLib.json");
                                    return 1;
                                }))
                        .then(literal("clear-countdowns")
                                .requires(Permissions.require("blossom.lib.base-command.clear.countdowns", 2))
                                .executes(ctx -> {
                                    TeleportUtils.clearAll();
                                    return 1;
                                })
                                .then(argument("player", EntityArgumentType.player())
                                        .executes(ctx -> {
                                            TeleportUtils.cancelCountdowns(EntityArgumentType.getPlayer(ctx, "player").getUuid());
                                            return 1;
                                        })))
                        .then(literal("clear-cooldowns")
                                .requires(Permissions.require("bolssom.lib.base-command.clear.cooldowns", 2))
                                .executes(ctx -> {
                                    TeleportUtils.cancelAllCooldowns();
                                    return 1;
                                })
                                .then(argument("player", EntityArgumentType.player())
                                        .executes(ctx -> {
                                            TeleportUtils.cancelCooldowns(EntityArgumentType.getPlayer(ctx, "player").getUuid());
                                            return 1;
                                        })
                                        .then(argument("type", StringArgumentType.greedyString())
                                                .suggests((ctx, builder) -> {
                                                    String start = builder.getRemaining().toLowerCase();
                                                    TeleportUtils.getCooldowns(EntityArgumentType.getPlayer(ctx, "player").getUuid())
                                                            .stream()
                                                            .map(Class::getSimpleName)
                                                            .sorted(String::compareToIgnoreCase)
                                                            .filter(c -> c.toLowerCase().startsWith(start))
                                                            .forEach(builder::suggest);
                                                    return builder.buildFuture();
                                                })
                                                .executes(ctx -> {
                                                    String type = StringArgumentType.getString(ctx, "type");
                                                    UUID player = EntityArgumentType.getPlayer(ctx, "player").getUuid();
                                                    Class<?> target = TeleportUtils.getCooldowns(player)
                                                            .stream()
                                                            .filter(c -> c.getSimpleName().equals(type))
                                                            .findFirst().orElseThrow();
                                                    TeleportUtils.cancelCooldown(player, target);
                                                    return 1;
                                                }))))
                        .then(literal("debug")
                                .requires(Permissions.require("blossom.lib.base-command.debug", 4))
                                .then(literal("countdown")
                                        .then(argument("standStill", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    TeleportUtils.genericCountdown(
                                                            null,
                                                            IntegerArgumentType.getInteger(ctx, "standStill"),
                                                            ctx.getSource().getPlayer(),
                                                            () -> LOGGER.info("debug countdown done")
                                                    );
                                                    return 1;
                                                })))
                                .then(literal("teleport")
                                        .then(argument("standStill", IntegerArgumentType.integer(0))
                                                .then(argument("pos", Vec3ArgumentType.vec3(true))
                                                        .then(argument("rot", RotationArgumentType.rotation())
                                                                .executes(ctx -> {
                                                                    Vec2f rot = RotationArgumentType.getRotation(ctx, "rot")
                                                                            .toAbsoluteRotation(ctx.getSource());
                                                                    return TeleportUtils.teleport(
                                                                            null,
                                                                            IntegerArgumentType.getInteger(ctx, "standStill"),
                                                                            ctx.getSource().getPlayer(),
                                                                            () -> new TeleportUtils.TeleportDestination(
                                                                                    ctx.getSource().getWorld(),
                                                                                    Vec3ArgumentType.getVec3(ctx, "pos"),
                                                                                    rot.y, rot.x
                                                                            )
                                                                    ) ? 1 : 0;
                                                                })))
                                                .then(argument("cooldown", IntegerArgumentType.integer())
                                                        .then(argument("pos", Vec3ArgumentType.vec3(true))
                                                                .then(argument("rot", RotationArgumentType.rotation())
                                                                        .executes(ctx -> {
                                                                            Vec2f rot = RotationArgumentType.getRotation(ctx, "rot")
                                                                                    .toAbsoluteRotation(ctx.getSource());
                                                                            return TeleportUtils.teleport(
                                                                                    null,
                                                                                    IntegerArgumentType.getInteger(ctx, "standStill"),
                                                                                    IntegerArgumentType.getInteger(ctx, "cooldown"),
                                                                                    BlossomLib.class,
                                                                                    ctx.getSource().getPlayer(),
                                                                                    () -> new TeleportUtils.TeleportDestination(
                                                                                            ctx.getSource().getWorld(),
                                                                                            Vec3ArgumentType.getVec3(ctx, "pos"),
                                                                                            rot.y, rot.x
                                                                                    )
                                                                            ) ? 1 : 0;
                                                                        })))))))));
    }
}
