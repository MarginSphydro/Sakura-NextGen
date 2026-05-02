package dev.sakura.fabric;

import dev.sakura.client.SakuraClient;
import dev.sakura.client.assets.i18n.LanguageReloadListener;
import dev.sakura.client.assets.resources.ResourceLocationUtils;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.server.packs.PackType;

public class SakuraFabric {

    public static void init() {
        SakuraClient.init();

        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(
                ResourceLocationUtils.getIdentifier("objects/reload_listener"),
                new LanguageReloadListener()
        );
    }

}
