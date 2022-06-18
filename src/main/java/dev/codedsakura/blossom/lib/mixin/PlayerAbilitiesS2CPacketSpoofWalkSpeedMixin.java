package dev.codedsakura.blossom.lib.mixin;

import dev.codedsakura.blossom.lib.utils.PlayerSetFoV;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xyz.nucleoid.packettweaker.PacketContext;

@Mixin(PlayerAbilitiesS2CPacket.class)
public class PlayerAbilitiesS2CPacketSpoofWalkSpeedMixin {
    @ModifyArg(
            method = "write",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/PacketByteBuf;writeFloat(F)Lio/netty/buffer/ByteBuf;",
                    ordinal = 1
            ),
            index = 0
    )
    public float writeOverride(float in) {
        ServerPlayerEntity target = PacketContext.get().getTarget();
        if (target == null) {
            return in;
        }
        return PlayerSetFoV.getPlayerFoV(target).orElse(in);
    }
}
