package com.m1gt.thermal.mixin;

import com.m1gt.thermal.ThermalCameraEffect;
import com.m1gt.thermal.client.ThermalClientState;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    private static final ResourceLocation THERMAL_EFFECT =
        ResourceLocation.fromNamespaceAndPath(
            ThermalCameraEffect.MODID, "shaders/post/thermal.json");

    /**
     * 当热成像开启时，把任何 loadEffect 的调用重定向到我们的 thermal 效果。
     * 这样原版加载 entity_outline（发光）时，实际加载的是 thermal。
     */
    @ModifyVariable(method = "loadEffect", at = @At("HEAD"), argsOnly = true)
    private ResourceLocation thermal$redirectEffect(ResourceLocation original) {
        if (ThermalClientState.isEnabled()) {
            return THERMAL_EFFECT;
        }
        return original;
    }
}
