package dev.sakura.client;

import dev.sakura.client.addon.AddonBootstrap;
import dev.sakura.client.addon.SakuraAddonSetupEvent;
import dev.sakura.client.assets.i18n.LanguageReloadListener;
import dev.sakura.client.assets.resources.ResourceLocationUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = SakuraClient.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SakuraClient.MOD_ID, value = Dist.CLIENT)
public class SakuraNeoForge {

    @SubscribeEvent
    private static void onClientSetup(FMLClientSetupEvent event) {
        SakuraAddonSetupEvent addonEvent = new SakuraAddonSetupEvent();
        NeoForge.EVENT_BUS.post(addonEvent);
        AddonBootstrap.registerAddons(addonEvent.getAddons());

        SakuraClient.init();
    }

    @SubscribeEvent
    private static void onResourcesReload(AddClientReloadListenersEvent event) {
        event.addListener(ResourceLocationUtils.getIdentifier("objects/reload_listener"), new LanguageReloadListener());
    }

}
