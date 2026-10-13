package com.m1gt.thermal.command;

import com.m1gt.thermal.network.ThermalNetwork;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ThermalCommand {

    public static final Map<UUID, Boolean> THERMAL_STATE = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("thermal")
                .requires(src -> src.hasPermission(2))
                .then(Commands.literal("on")
                    .executes(ctx -> setThermal(ctx.getSource(), true)))
                .then(Commands.literal("off")
                    .executes(ctx -> setThermal(ctx.getSource(), false)))
        );
    }

    private static int setThermal(CommandSourceStack source, boolean on) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("该命令只能由玩家执行"));
            return 0;
        }
        THERMAL_STATE.put(player.getUUID(), on);
        ThermalNetwork.sendToPlayer(player, on);
        source.sendSuccess(
            () -> Component.literal("热成像已" + (on ? "开启" : "关闭")),
            false
        );
        return 1;
    }
}
