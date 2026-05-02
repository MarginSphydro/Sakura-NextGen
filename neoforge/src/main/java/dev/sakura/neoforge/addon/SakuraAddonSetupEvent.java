package dev.sakura.neoforge.addon;

import dev.sakura.client.addon.SakuraAddon;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * NeoForge EVENT_BUS event for collecting Epsilon addons.
 */
public class SakuraAddonSetupEvent extends Event {

    private final ArrayList<SakuraAddon> addons = new ArrayList<>();

    public void registerAddon(SakuraAddon addon) {
        if (addon != null) {
            addons.add(addon);
        }
    }

    public List<SakuraAddon> getAddons() {
        return addons;
    }

}
