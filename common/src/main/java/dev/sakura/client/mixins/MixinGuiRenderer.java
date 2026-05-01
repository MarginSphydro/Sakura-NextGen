package dev.sakura.client.mixins;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.events.bus.EventBus;
import dev.sakura.client.events.impl.Render2DEvent;
import dev.sakura.client.managers.ModuleManager;
import dev.sakura.client.managers.RenderManager;
import dev.sakura.client.utils.render.SakuraMcGuiRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(GuiRenderer.class)
public class MixinGuiRenderer {

    @Unique
    private GuiRenderState sakura$renderState;

    @Unique
    private SakuraMcGuiRenderer sakura$guiRenderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(GuiRenderState renderState, MultiBufferSource.BufferSource bufferSource, SubmitNodeCollector submitNodeCollector, FeatureRenderDispatcher featureRenderDispatcher, List<PictureInPictureRenderer<?>> pictureInPictureRenderers, CallbackInfo ci) {
        if ((GuiRenderer) (Object) this instanceof SakuraMcGuiRenderer) return;

        this.sakura$renderState = new GuiRenderState();

        this.sakura$guiRenderer = new SakuraMcGuiRenderer(this.sakura$renderState, bufferSource, submitNodeCollector, featureRenderDispatcher, new ArrayList<>(pictureInPictureRenderers));
    }

    @Inject(method = "draw", at = @At("HEAD"))
    private void renderHead(GpuBufferSlice fogBuffer, CallbackInfo ci) {
        if ((GuiRenderer) (Object) this instanceof SakuraMcGuiRenderer) return;

        Minecraft mc = Minecraft.getInstance();

        GuiGraphicsExtractor guiGraphics = new GuiGraphicsExtractor(mc, sakura$renderState, (int) mc.mouseHandler.getScaledXPos(mc.getWindow()), (int) mc.mouseHandler.getScaledYPos(mc.getWindow()));

        ModuleManager.INSTANCE.flushHuds(guiGraphics);

        sakura$guiRenderer.render(fogBuffer);

        sakura$guiRenderer.endFrame();
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;executeDrawRange(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/pipeline/RenderTarget;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;II)V", shift = At.Shift.BEFORE, ordinal = 0))
    private void renderInGameGuiPre(GpuBufferSlice fogBuffer, CallbackInfo ci) {
        EventBus.INSTANCE.post(new Render2DEvent.BeforeInGameGui());
        RenderSystem.backupProjectionMatrix();
        RenderManager.INSTANCE.callInGameGui(Minecraft.getInstance().getDeltaTracker());
        RenderSystem.restoreProjectionMatrix();
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;executeDrawRange(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/pipeline/RenderTarget;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;II)V", shift = At.Shift.AFTER, ordinal = 0))
    private void renderInGameGuiPost(GpuBufferSlice fogBuffer, CallbackInfo ci) {
        EventBus.INSTANCE.post(new Render2DEvent.AfterInGameGui());
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;executeDrawRange(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/pipeline/RenderTarget;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;II)V", shift = At.Shift.BEFORE, ordinal = 1))
    private void renderGuiPre(GpuBufferSlice fogBuffer, CallbackInfo ci) {
        EventBus.INSTANCE.post(new Render2DEvent.BeforeGui());
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;executeDrawRange(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/pipeline/RenderTarget;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;II)V", shift = At.Shift.AFTER, ordinal = 1))
    private void renderGuiPost(GpuBufferSlice fogBuffer, CallbackInfo ci) {
        EventBus.INSTANCE.post(new Render2DEvent.AfterGui());
    }

}
