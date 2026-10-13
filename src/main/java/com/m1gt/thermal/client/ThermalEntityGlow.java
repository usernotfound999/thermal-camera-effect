package com.m1gt.thermal.client;

import com.m1gt.thermal.ThermalCameraEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

@EventBusSubscriber(modid = ThermalCameraEffect.MODID, value = Dist.CLIENT)
public class ThermalEntityGlow {

    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Pre<?, ?, ?> event) {
        if (!ThermalClientState.isEnabled()) return;
        LivingEntity entity = event.getEntity();
        // 让生物发光，原版会把它渲染成实心剪影到 entityEffect 的 final target
        entity.setGlowingTag(true);
    }
}
