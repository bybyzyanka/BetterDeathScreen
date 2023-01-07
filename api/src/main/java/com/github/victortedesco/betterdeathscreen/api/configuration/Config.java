package com.github.victortedesco.betterdeathscreen.api.configuration;

import java.util.List;

public interface Config {

    String getLanguage();

    int getRespawnTime();

    boolean useSafeTeleport();

    List<String> getCommandsOnDeath();

    List<String> getDeathSounds();

    List<String> getKillSounds();

    List<String> getCountdownSounds();

    List<String> getRespawnSounds();

    String getKillMessageType();

    String getKilledMessageType();

    String getKilledByPlayerMessageType();

    String getHardcoreCountdownMessageType();

    String getNonHardcoreCountdownMessageType();

    String getAnimationType();

    String getInstantRespawnPermission();

    String getAdminPermission();

    boolean useQueueTeleport();

    boolean canSpectate();

    boolean canFly();

    List<String> getAllowedCommands();
}