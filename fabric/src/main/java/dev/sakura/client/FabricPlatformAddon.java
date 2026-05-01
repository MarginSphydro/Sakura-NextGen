package dev.sakura.client;

import dev.sakura.client.addon.SakuraAddon;

import java.util.List;

/**
 * Built-in Fabric addon for Fabric-only features.
 */
public class FabricPlatformAddon extends SakuraAddon {

    public FabricPlatformAddon() {
        super("sakura_fabric");
    }

    @Override
    public void onSetup() {
        SakuraClient.LOGGER.info("Fabric platform addon initialized.");
    }

    @Override
    public String getDisplayName() {
        return "Fabric Platform";
    }

    @Override
    public String getDescription() {
        return "Built-in addon for Fabric-specific integrations.";
    }

    @Override
    public String getVersion() {
        return SakuraClient.VERSION;
    }

    @Override
    public List<String> getAuthors() {
        return List.of("Sakura");
    }

}