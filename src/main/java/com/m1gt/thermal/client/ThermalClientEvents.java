package com.m1gt.thermal.client;

import com.m1gt.thermal.ThermalCameraEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = ThermalCameraEffect.MODID, value = Dist.CLIENT)
public class ThermalClientEvents {

    private static final ResourceLocation THERMAL_EFFECT =
        ResourceLocation.fromNamespaceAndPath(
            ThermalCameraEffect.MODID, "shaders/post/thermal.json");

    private static boolean lastState = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean current = ThermalClientState.isEnabled();
        if (current == lastState) return;
        lastState = current;

        if (current) {
            mc.gameRenderer.loadEffect(THERMAL_EFFECT);
        } else {
            mc.gameRenderer.shutdownEffect();
        }
    }
}