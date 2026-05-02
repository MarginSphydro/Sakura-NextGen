package dev.sakura.neoforge.mixins;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import dev.sakura.client.managers.ModuleManager;
import dev.sakura.neoforge.utils.render.SakuraMcGuiRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.neoforged.neoforge.client.gui.PictureInPictureRendererRegistration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiRenderer.class)
public class MixinGuiRenderer {

    @Unique
    private GuiRenderState sakura$renderState;

    @Unique
    private SakuraMcGuiRenderer sakura$guiRenderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(GuiRenderState renderState, MultiBufferSource.BufferSource bufferSource, SubmitNodeCollector submitNodeCollector, FeatureRenderDispatcher featureRenderDispatcher, List<PictureInPictureRendererRegistration<?>> pipRendererFactories, CallbackInfo ci) {
        if ((GuiRenderer) (Object) this instanceof SakuraMcGuiRenderer) return;

        this.sakura$renderState = new GuiRenderState();

        this.sakura$guiRenderer = new SakuraMcGuiRenderer(this.sakura$renderState, bufferSource, submitNodeCollector, featureRenderDispatcher, pipRendererFactories);
    }

    @Inject(method = "draw", at = @At("HEAD"))
    private void onDrawHead(GpuBufferSlice fogBuffer, CallbackInfo ci) {
        if ((GuiRenderer) (Object) this instanceof SakuraMcGuiRenderer) return;

        Minecraft mc = Minecraft.getInstance();

        GuiGraphicsExtractor guiGraphics = new GuiGraphicsExtractor(mc, sakura$renderState, (int) mc.mouseHandler.getScaledXPos(mc.getWindow()), (int) mc.mouseHandler.getScaledYPos(mc.getWindow()));

        ModuleManager.INSTANCE.flushHuds(guiGraphics);

        sakura$guiRenderer.render(fogBuffer);

        sakura$guiRenderer.endFrame();
    }

}
