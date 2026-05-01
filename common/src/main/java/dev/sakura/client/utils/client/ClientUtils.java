package dev.sakura.client.utils.client;

import dev.sakura.client.mixins.IMinecraft;
import dev.sakura.client.mixins.IReloadState;
import dev.sakura.client.mixins.IResourceLoadStateTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ResourceLoadStateTracker;

public class ClientUtils {

    private static final Minecraft mc = Minecraft.getInstance();

    public static boolean isLoading() {
        ResourceLoadStateTracker.ReloadState state = ((IResourceLoadStateTracker) ((IMinecraft) mc).sakura$getReloadStateTracker()).sakura$getReloadState();
        return state == null || !((IReloadState) state).sakura$isFinished();
    }

}
