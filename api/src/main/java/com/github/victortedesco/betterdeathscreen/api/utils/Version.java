package com.github.victortedesco.betterdeathscreen.api.utils;

import org.bukkit.Bukkit;

public enum Version {

    v1_21(21),
    v1_20(20),
    v1_19(19),
    v1_18(18),
    v1_17(17),
    v1_16(16),
    v1_15(15),
    v1_14(14),
    v1_13(13),
    v1_12(12),
    v1_11(11),
    v1_10(10),
    v1_9(9),
    v1_8(8),
    UNKNOWN(-1);

    private final int value;

    Version(int value) {
        this.value = value;
    }

    public static Version getServerVersion() {
        Version version = getServerVersion(getMinecraftVersion());
        if (version == UNKNOWN) {
            version = getServerVersion(Bukkit.getVersion());
        }
        return version;
    }

    public static String getMinecraftVersion() {
        try {
            String info = Bukkit.getVersion();
            return info.split("MC: ")[1].split("\\)")[0];
        } catch (Throwable throwable) {
            return "???";
        }
    }

    public static Version getServerVersion(String ver) {
        if (ver.contains("1.21")) return v1_21;
        if (ver.contains("1.20")) return v1_20;
        if (ver.contains("1.19")) return v1_19;
        if (ver.contains("1.18")) return v1_18;
        if (ver.contains("1.17")) return v1_17;
        if (ver.contains("1.16")) return v1_16;
        if (ver.contains("1.15")) return v1_15;
        if (ver.contains("1.14")) return v1_14;
        if (ver.contains("1.13")) return v1_13;
        if (ver.contains("1.12")) return v1_12;
        if (ver.contains("1.11")) return v1_11;
        if (ver.contains("1.10")) return v1_10;
        if (ver.contains("1.9")) return v1_9;
        if (ver.contains("1.8")) return v1_8;
        return UNKNOWN;
    }

    /**
     * 1.8 to 1.12
     */
    public static boolean isOldVersion() {
        return getServerVersion().getValue() < Version.v1_13.getValue() && getServerVersion().getValue() > Version.v1_8.getValue();
    }

    /**
     * 1.13 to 1.15
     */
    public static boolean isNewVersion() {
        return getServerVersion().getValue() < Version.v1_16.getValue() && getServerVersion().getValue() > Version.v1_12.getValue();
    }

    /**
     * 1.16 and above
     */
    public static boolean isVeryNewVersion() {
        return getServerVersion().getValue() > Version.v1_15.getValue();
    }

    public int getValue() {
        return this.value;
    }
}