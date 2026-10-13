package com.m1gt.thermal.mixin;

import com.m1gt.thermal.client.ThermalClientState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @ModifyArg(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"
        ),
        index = 4
    )
    private int thermal$whiteColor(int original) {
        if (ThermalClientState.isEnabled()) {
            return 0xFFFFFFFF; // 纯白，不透明
        }
        return original;
    }
}
