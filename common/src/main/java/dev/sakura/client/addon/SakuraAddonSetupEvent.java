package dev.sakura.client.addon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SakuraAddonSetupEvent {

    private final ArrayList<SakuraAddon> addons = new ArrayList<>();

    public void registerAddon(SakuraAddon addon) {
        if (addon != null) {
            addons.add(addon);
        }
    }

    public List<SakuraAddon> getAddons() {
        return Collections.unmodifiableList(addons);
    }

}
