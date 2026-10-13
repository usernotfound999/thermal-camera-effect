package com.m1gt.thermal.mixin;

import com.m1gt.thermal.client.ThermalClientState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityGlowMixin {

    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    private void thermal$forceGlow(CallbackInfoReturnable<Boolean> cir) {
        if (!ThermalClientState.isEnabled()) {
            return;
        }

        Entity self = (Entity) (Object) this;

        switch (ThermalClientState.getGlowMode()) {
            case ALL_ENTITIES -> {
                if (self instanceof LivingEntity) {
                    cir.setReturnValue(true);
                }
            }
            case PLAYERS_ONLY -> {
                if (self instanceof Player) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
