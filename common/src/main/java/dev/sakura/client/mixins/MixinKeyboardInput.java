package dev.sakura.client.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sakura.client.events.bus.EventBus;
import dev.sakura.client.events.impl.KeyboardInputEvent;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyboardInput.class)
public class MixinKeyboardInput {

    @ModifyExpressionValue(method = "tick", at = @At(value = "NEW", target = "(ZZZZZZZ)Lnet/minecraft/world/entity/player/Input;"))
    private Input redirectKeyPresses(Input original) {
        KeyboardInputEvent event = new KeyboardInputEvent(original.forward(), original.backward(), original.left(), original.right(), original.jump(), original.shift(), original.sprint());
        return EventBus.INSTANCE.post(event).toNewInput();
    }

}
