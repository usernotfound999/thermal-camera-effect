package com.m1gt.thermal.network;

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
            (payload, context) -> context.enqueueWork(() ->
                com.m1gt.thermal.client.ThermalClientState.setEnabled(payload.enabled())
            )
        );
    }

    public static void sendToPlayer(ServerPlayer player, boolean enabled) {
        PacketDistributor.sendToPlayer(player, new ThermalPayload(enabled));
    }
}
