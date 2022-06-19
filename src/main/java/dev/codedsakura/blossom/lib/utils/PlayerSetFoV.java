package dev.codedsakura.blossom.lib.utils;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerSetFoV {
    public static void setPlayerFoV(ServerPlayerEntity player, float multiplier) {

        // invert math done in AbstractClientPlayerEntity#getFovMultiplier
        float genericMovementSpeed = (float) player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        multiplier = (genericMovementSpeed / 2f) / (multiplier - .5f);

        player.getAbilities().setWalkSpeed(multiplier);
        player.sendAbilitiesUpdate();
    }
}
