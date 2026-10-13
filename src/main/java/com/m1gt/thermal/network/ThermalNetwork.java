package com.m1gt.thermal.network;

import com.m1gt.thermal.client.ThermalClientState;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ThermalNetwork {

    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
            ThermalPayload.TYPE,
            ThermalPayload.STREAM_CODEC,
            (payload, context) -> context.enqueueWork(() -> {
                ThermalClientState.setEnabled(payload.enabled());
                ThermalClientState.setGlowMode(
                    payload.playersOnly()
                        ? ThermalClientState.GlowMode.PLAYERS_ONLY
                        : ThermalClientState.GlowMode.ALL_ENTITIES
                );
            })
        );
    }

    public static void sendToPlayer(ServerPlayer player, boolean enabled, boolean playersOnly) {
        PacketDistributor.sendToPlayer(player, new ThermalPayload(enabled, playersOnly));
    }
}
