package com.m1gt.thermal.client;

public class ThermalClientState {

    public enum GlowMode {
        ALL_ENTITIES,
        PLAYERS_ONLY
    }

    private static boolean enabled = false;
    private static GlowMode glowMode = GlowMode.PLAYERS_ONLY;  // 默认仅玩家

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static GlowMode getGlowMode() {
        return glowMode;
    }

    public static void setGlowMode(GlowMode mode) {
        glowMode = mode;
    }
}
