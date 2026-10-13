package com.m1gt.thermal;

import com.m1gt.thermal.command.ThermalCommand;
import com.m1gt.thermal.network.ThermalNetwork;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ThermalCameraEffect.MODID)
public class ThermalCameraEffect {
    public static final String MODID = "thermalcameraeffect";

    public ThermalCameraEffect(IEventBus modEventBus) {
        modEventBus.addListener(ThermalNetwork::register);
        NeoForge.EVENT_BUS.register(ThermalCommand.class);
    }
}
