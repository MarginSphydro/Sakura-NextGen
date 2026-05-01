package dev.sakura.client.mixins;

import dev.sakura.client.gui.screen.MainMenuScreen;
import dev.sakura.client.modules.impl.ClientSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void redirectToMainMenu(CallbackInfo ci) {
        if (ClientSetting.INSTANCE.shouldUseMainMenu()) {
            ci.cancel();
            Minecraft.getInstance().setScreen(MainMenuScreen.INSTANCE);
        }
    }

}
