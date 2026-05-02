package dev.sakura.fabric.addon;

import dev.sakura.client.addon.SakuraAddonSetupEvent;
import dev.sakura.fabric.FabricPlatformAddon;

/**
 * Registers Epsilon's built-in Fabric addon through Fabric custom entrypoint.
 */
public class FabricSelfAddonEntrypoint implements FabricSakuraAddonEntrypoint {

    @Override
    public void registerAddon(SakuraAddonSetupEvent event) {
        event.registerAddon(new FabricPlatformAddon());
    }

}

