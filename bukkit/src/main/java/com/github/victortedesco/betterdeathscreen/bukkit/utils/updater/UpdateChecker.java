package com.github.victortedesco.betterdeathscreen.bukkit.utils.updater;

import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateChecker {

    private static final String GITHUB_LINK = "https://api.github.com/repos/VictorTedesco/BetterDeathScreen/releases";
    private static final Gson SERIALIZER = new GsonBuilder().serializeNulls().create();
    private final String currentVersion = BetterDeathScreen.getInstance().getDescription().getVersion();
    private GitHubRelease lastRelease;
    private String response;

    public UpdateChecker() {
        this.check();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (canUpdate() && BetterDeathScreen.getConfiguration().canNotifyUpdates()) {
                    BetterDeathScreen.getMessages().getUpdateAvailable().forEach(
                            message -> Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message
                                    .replace("%release_link%", lastRelease.getDownloadURL())
                                    .replace("%current_version%", currentVersion)
                                    .replace("%latest_version%", lastRelease.getVersion())))
                    );
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (!player.hasPermission(BetterDeathScreen.getConfiguration().getAdminPermission()))
                            continue;
                        BetterDeathScreen.getMessages().getUpdateAvailable().forEach(
                                message -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        message
                                                .replace("%release_link%", lastRelease.getDownloadURL())
                                                .replace("%current_version%", currentVersion)
                                                .replace("%latest_version%", lastRelease.getVersion()))
                                )
                        );
                    }
                }
            }
        }.runTaskTimerAsynchronously(BetterDeathScreen.getInstance(), 20L * 5L, 20L * 60L * 30L);
    }

    public void connect(URL url) {
        StringBuilder responseContent = new StringBuilder();
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) responseContent.append(line);
            reader.close();
        } catch (IOException exception) {
            return;
        }
        response = responseContent.toString();
    }

    public void check() {
        try {
            this.connect(new URL(GITHUB_LINK));
        } catch (MalformedURLException ignored) {
        }
        if (this.response == null) return;

        JsonElement element = new JsonParser().parse(response);
        JsonArray array = element.getAsJsonArray();
        if (array.size() == 0) return;

        GitHubRelease lastRelease = SERIALIZER.fromJson(array.get(0), GitHubRelease.class);

        if (lastRelease != null) updateRelease(lastRelease);
    }

    private void updateRelease(@Nullable GitHubRelease release) {
        this.lastRelease = release;
    }

    @NotNull
    public String getCurrentVersion() {
        return currentVersion;
    }

    @Nullable
    public GitHubRelease getLastRelease() {
        return lastRelease;
    }

    public boolean canUpdate() {
        return getLastRelease() != null && !lastRelease.getVersion().equalsIgnoreCase(currentVersion) && !lastRelease.isPreRelease() && !lastRelease.isDraft();
    }
}
