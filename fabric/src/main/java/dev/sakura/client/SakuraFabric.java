package dev.sakura.client;

import dev.sakura.client.assets.i18n.LanguageReloadListener;
import dev.sakura.client.assets.resources.ResourceLocationUtils;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SakuraFabric {

    public static void init() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(
                new FabricReloadListenerWrapper(
                        ResourceLocationUtils.getIdentifier("objects/reload_listener"),
                        new LanguageReloadListener()
                )
        );
        SakuraClient.init();
    }

    private record FabricReloadListenerWrapper(Identifier id,
                                               PreparableReloadListener delegate) implements IdentifiableResourceReloadListener {

        @Override
        public Identifier getFabricId() {
            return id;
        }

        @Override
        public CompletableFuture<Void> reload(
                PreparableReloadListener.SharedState sharedState,
                Executor backgroundExecutor,
                PreparableReloadListener.PreparationBarrier barrier,
                Executor gameExecutor
        ) {
            return delegate.reload(sharedState, backgroundExecutor, barrier, gameExecutor);
        }
    }

}
