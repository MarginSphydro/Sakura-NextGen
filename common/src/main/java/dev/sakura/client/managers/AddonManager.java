package dev.sakura.client.managers;

import dev.sakura.client.SakuraClient;
import dev.sakura.client.addon.SakuraAddon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddonManager {

    public static final AddonManager INSTANCE = new AddonManager();

    private final List<SakuraAddon> addons = new ArrayList<>();
    private final Set<String> addonIds = new HashSet<>();

    private boolean setupComplete;

    private AddonManager() {
    }

    public synchronized void registerAddon(SakuraAddon addon) {
        if (addon == null) {
            return;
        }

        String addonId = addon.getAddonId();
        if (addonId == null || addonId.isBlank()) {
            SakuraClient.LOGGER.warn("Ignoring Sakura addon with blank addonId: {}", addon.getClass().getName());
            return;
        }

        if (!addonIds.add(addonId)) {
            SakuraClient.LOGGER.warn("Duplicate Sakura addon id ignored: {}", addonId);
            return;
        }

        addons.add(addon);
    }

    public synchronized void registerAddons(Iterable<SakuraAddon> addonIterable) {
        if (addonIterable == null) {
            return;
        }
        for (SakuraAddon addon : addonIterable) {
            registerAddon(addon);
        }
    }

    public synchronized void setupAddons() {
        if (setupComplete) {
            return;
        }
        setupComplete = true;

        for (SakuraAddon addon : addons) {
            try {
                addon.initAddonI18n();
                addon.onSetup();
                SakuraClient.LOGGER.info("Loaded Sakura addon: {}", addon.getAddonId());
            } catch (Throwable throwable) {
                SakuraClient.LOGGER.error("Failed to setup Sakura addon: {}", addon.getAddonId(), throwable);
            }
        }
    }

    public synchronized List<SakuraAddon> getAddons() {
        return List.copyOf(addons);
    }

}
