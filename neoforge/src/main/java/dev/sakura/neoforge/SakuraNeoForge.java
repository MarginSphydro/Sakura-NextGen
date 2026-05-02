package dev.sakura.neoforge;

import dev.sakura.client.SakuraClient;
import dev.sakura.client.addon.AddonBootstrap;
import dev.sakura.client.assets.i18n.LanguageReloadListener;
import dev.sakura.client.assets.resources.ResourceLocationUtils;
import dev.sakura.neoforge.addon.SakuraAddonSetupEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = SakuraClient.MOD_ID, value = Dist.CLIENT)
public class SakuraNeoForge {

    public static void init() {
        SakuraAddonSetupEvent addonEvent = NeoForge.EVENT_BUS.post(new SakuraAddonSetupEvent());
        AddonBootstrap.registerAddons(addonEvent.getAddons());

        SakuraClient.init();
    }

    @SubscribeEvent
    private static void onResourcesReload(AddClientReloadListenersEvent event) {
        event.addListener(ResourceLocationUtils.getIdentifier("objects/reload_listener"), new LanguageReloadListener());
    }

}
