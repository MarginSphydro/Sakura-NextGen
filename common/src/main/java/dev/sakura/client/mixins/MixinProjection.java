package dev.sakura.client.mixins;

import com.mojang.blaze3d.ProjectionType;
import dev.sakura.client.modules.impl.render.AspectRatio;
import net.minecraft.client.renderer.Projection;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Projection.class)
public class MixinProjection {

    @Shadow
    private ProjectionType projectionType;

    @Shadow
    private float width;

    @Shadow
    private float height;

    @Inject(method = "getMatrix", at = @At("RETURN"))
    private void sakura$aspectRatio$getMatrix(Matrix4f dest, CallbackInfoReturnable<Matrix4f> cir) {
        if (projectionType != ProjectionType.PERSPECTIVE) return;
        if (!AspectRatio.INSTANCE.isEnabled()) return;

        double targetAspect = AspectRatio.INSTANCE.ratio.getValue();
        if (targetAspect <= 0.0) return;
        if (height == 0.0f) return;

        float currentAspect = width / height;
        float scale = (float) (currentAspect / targetAspect);

        Matrix4f m = cir.getReturnValue();
        m.m00(m.m00() * scale);
    }

}
