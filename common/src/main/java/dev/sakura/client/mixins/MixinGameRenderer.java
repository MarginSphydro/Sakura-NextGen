package dev.sakura.client.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.events.bus.EventBus;
import dev.sakura.client.events.impl.RenderFrameEvent;
import dev.sakura.client.managers.RenderManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderPre(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci) {
        EventBus.INSTANCE.post(new RenderFrameEvent.Pre(deltaTracker));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void onRenderPost(DeltaTracker deltaTracker, boolean advanceGameTime, CallbackInfo ci) {
        EventBus.INSTANCE.post(new RenderFrameEvent.Post());
        RenderSystem.backupProjectionMatrix();
        RenderManager.INSTANCE.callAfterFrame(Minecraft.getInstance().getDeltaTracker());
        RenderSystem.restoreProjectionMatrix();
        RenderManager.INSTANCE.clear();
    }

}
