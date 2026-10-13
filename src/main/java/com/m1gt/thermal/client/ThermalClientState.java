package com.m1gt.thermal.client;

public class ThermalClientState {
    private static boolean enabled = false;

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }
}
