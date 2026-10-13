package com.m1gt.thermal.network;

import com.m1gt.thermal.ThermalCameraEffect;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ThermalPayload(boolean enabled) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ThermalPayload> TYPE =
        new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(ThermalCameraEffect.MODID, "thermal_state")
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ThermalPayload> STREAM_CODEC =
        StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ThermalPayload::enabled,
            ThermalPayload::new
        );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
