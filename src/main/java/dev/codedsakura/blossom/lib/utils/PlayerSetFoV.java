package dev.codedsakura.blossom.lib.utils;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PlayerSetFoV {
    private static final HashMap<UUID, Float> PLAYER_FOV_MAP = new HashMap<>();

    public static void setPlayerFoV(ServerPlayerEntity player, float multiplier) {

        // invert math done in AbstractClientPlayerEntity#getFovMultiplier
        float genericMovementSpeed = (float) player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        multiplier = (genericMovementSpeed / 2f) / (multiplier - .5f);

        PLAYER_FOV_MAP.put(player.getUuid(), multiplier);
        player.sendAbilitiesUpdate();
    }

    public static Optional<Float> getPlayerFoV(ServerPlayerEntity player) {
        return Optional.ofNullable(PLAYER_FOV_MAP.get(player.getUuid()));
    }

    public static void clearPlayerFoV(ServerPlayerEntity player) {
        PLAYER_FOV_MAP.remove(player.getUuid());
        player.sendAbilitiesUpdate();
    }
}
