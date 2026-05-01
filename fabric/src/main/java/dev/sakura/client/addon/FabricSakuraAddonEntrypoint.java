package dev.sakura.client.addon;

/**
 * Custom Fabric entrypoint contract for Epsilon addons.
 */
public interface FabricSakuraAddonEntrypoint {

    void registerAddon(SakuraAddonSetupEvent event);

}

