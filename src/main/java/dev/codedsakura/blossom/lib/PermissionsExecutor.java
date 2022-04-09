package dev.codedsakura.blossom.lib;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

class PermissionsExecutor {
    static Predicate<ServerCommandSource> require(@NotNull String permission, boolean defaultValue) {
        return Permissions.require(permission, defaultValue);
    }

    static Predicate<ServerCommandSource> require(@NotNull String permission, int level) {
        return Permissions.require(permission, level);
    }

    static boolean check(@NotNull ServerCommandSource source, @NotNull String permission, boolean fallback) {
        return Permissions.check(source, permission, fallback);
    }

    static boolean check(@NotNull ServerCommandSource source, @NotNull String permission, int level) {
        return Permissions.check(source, permission, level);
    }
}
