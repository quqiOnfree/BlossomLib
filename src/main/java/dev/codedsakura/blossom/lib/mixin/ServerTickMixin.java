package dev.codedsakura.blossom.lib.mixin;

import dev.codedsakura.blossom.lib.TeleportUtils;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class ServerTickMixin {
    @Inject(at = @At("TAIL"), method = "tick")
    void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        TeleportUtils.tick();
    }
}
