package com.m1gt.thermal.command;

import com.m1gt.thermal.network.ThermalNetwork;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ThermalCommand {

    /** 每个玩家的热成像状态 */
    public static class PlayerState {
        public boolean enabled = false;
        public boolean playersOnly = true;   // 默认：仅玩家

        public PlayerState(boolean enabled, boolean playersOnly) {
            this.enabled = enabled;
            this.playersOnly = playersOnly;
        }
    }

    public static final Map<UUID, PlayerState> THERMAL_STATE = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("thermal")
                .requires(src -> src.hasPermission(2))
                // /thermal on
                .then(Commands.literal("on")
                    .executes(ctx -> setEnabled(ctx.getSource(), true)))
                // /thermal off
                .then(Commands.literal("off")
                    .executes(ctx -> setEnabled(ctx.getSource(), false)))
                // /thermal mode all | players
                .then(Commands.literal("mode")
                    .then(Commands.literal("all")
                        .executes(ctx -> setMode(ctx.getSource(), false)))
                    .then(Commands.literal("players")
                        .executes(ctx -> setMode(ctx.getSource(), true))))
        );
    }

    /** 玩家退出时清理状态 */
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            THERMAL_STATE.remove(sp.getUUID());
        }
    }

    private static int setEnabled(CommandSourceStack source, boolean on) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("该命令只能由玩家执行"));
            return 0;
        }
        PlayerState state = THERMAL_STATE.computeIfAbsent(
                player.getUUID(), k -> new PlayerState(false, true));
        state.enabled = on;

        ThermalNetwork.sendToPlayer(player, state.enabled, state.playersOnly);
        source.sendSuccess(
            () -> Component.literal("热成像已" + (on ? "开启" : "关闭")),
            false
        );
        return 1;
    }

    private static int setMode(CommandSourceStack source, boolean playersOnly) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("该命令只能由玩家执行"));
            return 0;
        }
        PlayerState state = THERMAL_STATE.computeIfAbsent(
                player.getUUID(), k -> new PlayerState(false, true));
        state.playersOnly = playersOnly;

        ThermalNetwork.sendToPlayer(player, state.enabled, state.playersOnly);
        source.sendSuccess(
            () -> Component.literal("发光模式：" + (playersOnly ? "仅玩家" : "所有实体")),
            false
        );
        return 1;
    }
}
