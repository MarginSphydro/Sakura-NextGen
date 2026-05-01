package dev.sakura.client.addon;

import dev.sakura.client.FabricPlatformAddon;

/**
 * Registers Epsilon's built-in Fabric addon through Fabric custom entrypoint.
 */
public class FabricSelfAddonEntrypoint implements FabricSakuraAddonEntrypoint {

    @Override
    public void registerAddon(SakuraAddonSetupEvent event) {
        event.registerAddon(new FabricPlatformAddon());
    }

}

