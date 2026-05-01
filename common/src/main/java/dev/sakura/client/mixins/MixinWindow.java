package dev.sakura.client.mixins;

import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.Window;
import dev.sakura.client.SakuraClient;
import dev.sakura.client.modules.impl.ClientSetting;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(Window.class)
public class MixinWindow {

    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/IconSet;getStandardIcons(Lnet/minecraft/server/packs/PackResources;)Ljava/util/List;"))
    private List<IoSupplier<InputStream>> onSetIcon(IconSet instance, PackResources resources) throws IOException {
        final InputStream stream16 = SakuraClient.class.getResourceAsStream("/assets/sakura/textures/icons/icon_16x16.png");
        final InputStream stream32 = SakuraClient.class.getResourceAsStream("/assets/sakura/textures/icons/icon_32x32.png");
        return ClientSetting.INSTANCE.customIcon() && stream16 != null && stream32 != null ?
                List.of(() -> stream16, () -> stream32) :
                instance.getStandardIcons(resources);
    }

    @ModifyArg(method = "setTitle", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetWindowTitle(JLjava/lang/CharSequence;)V"), index = 1)
    private CharSequence onSetTitle(CharSequence title) {
        return ClientSetting.INSTANCE.customTitle() ? "桜 " + SakuraClient.VERSION + " for " + title : title;
    }

}
