package dev.sakura.fabric.addon;

import dev.sakura.client.addon.SakuraAddonSetupEvent;

/**
 * Custom Fabric entrypoint contract for Epsilon addons.
 */
public interface FabricSakuraAddonEntrypoint {

    void registerAddon(SakuraAddonSetupEvent event);

}

