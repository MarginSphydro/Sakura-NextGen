package dev.sakura.client.addon;

import dev.sakura.client.managers.AddonManager;

/**
 * Shared addon bootstrap utility used by multiple loaders.
 */
public final class AddonBootstrap {

    private AddonBootstrap() {
    }

    public static void registerAddons(SakuraAddonSetupEvent addonEvent) {
        if (addonEvent != null) {
            registerAddons(addonEvent.getAddons());
        }
    }

    public static void registerAddons(Iterable<SakuraAddon> addons) {
        AddonManager.INSTANCE.registerAddons(addons);
    }

    public static void setupAddons(SakuraAddonSetupEvent addonEvent) {
        registerAddons(addonEvent);
        AddonManager.INSTANCE.setupAddons();
    }

    public static void setupAddons(Iterable<SakuraAddon> addons) {
        registerAddons(addons);
        AddonManager.INSTANCE.setupAddons();
    }

}
