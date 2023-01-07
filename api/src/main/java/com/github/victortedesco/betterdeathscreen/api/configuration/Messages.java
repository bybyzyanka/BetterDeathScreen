package com.github.victortedesco.betterdeathscreen.api.configuration;

import java.util.List;

public interface Messages {

    List<String> getIncompatible();

    List<String> getEnabled();

    List<String> getDisabled();

    String getReloaded();

    String getWithoutPermission();

    String getBlockedCommand();

    List<String> getHelp();

    String getNonHardcoreCountdown();

    String getHardcoreCountdown();

    String getTimeSingular();

    String getTimePlural();

    List<String> getKilled();

    List<String> getKilledByPlayer();

    List<String> getKill();
}
