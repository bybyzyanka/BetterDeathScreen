package com.github.victortedesco.betterdeathscreen.api;

import com.github.victortedesco.betterdeathscreen.api.animations.Animations;
import com.github.victortedesco.betterdeathscreen.api.manager.EventManager;
import com.github.victortedesco.betterdeathscreen.api.manager.PlayerManager;
import com.github.victortedesco.betterdeathscreen.api.manager.PluginManager;
import com.github.victortedesco.betterdeathscreen.api.utils.DeathMessageCreator;
import com.github.victortedesco.betterdeathscreen.api.utils.Randomizer;

public class BetterDeathScreenAPI {

    private static final Animations ANIMATIONS = new Animations();
    private static final Randomizer RANDOMIZER = new Randomizer();
    private static final EventManager EVENT_MANAGER = new EventManager();
    private static final PlayerManager PLAYER_MANAGER = new PlayerManager();
    private static final PluginManager PLUGIN_MANAGER = new PluginManager();
    private static final DeathMessageCreator DEATH_MESSAGE_CREATOR = new DeathMessageCreator();

    public static Animations getAnimations() {
        return ANIMATIONS;
    }

    public static EventManager getEventManager() {
        return EVENT_MANAGER;
    }

    public static PlayerManager getPlayerManager() {
        return PLAYER_MANAGER;
    }

    public static PluginManager getPluginManager() {
        return PLUGIN_MANAGER;
    }

    public static DeathMessageCreator getDeathMessageCreator() {
        return DEATH_MESSAGE_CREATOR;
    }

    public static Randomizer getRandomizer() {
        return RANDOMIZER;
    }
}
