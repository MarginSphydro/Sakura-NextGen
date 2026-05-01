package dev.sakura.client.modules.impl.combat;

import dev.sakura.client.events.bus.EventHandler;
import dev.sakura.client.events.impl.TickEvent;
import dev.sakura.client.managers.RotationManager;
import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.BoolSetting;
import dev.sakura.client.settings.impl.EnumSetting;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtils;
import dev.sakura.client.utils.rotation.Priority;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import org.joml.Vector2f;

public class AutoMend extends Module {

    public static final AutoMend INSTANCE = new AutoMend();

    private AutoMend() {
        super("Auto Mend", Category.COMBAT);
    }

    private enum SwitchMode {
        Normal,
        Silent
    }

    private final EnumSetting<SwitchMode> switchMode = enumSetting("Switch Mode", SwitchMode.Normal);
    private final BoolSetting swingHand = boolSetting("Swing Hand", false);

    private boolean shouldSwapBack;

    @Override
    protected void onEnable() {
        shouldSwapBack = false;
    }

    @Override
    protected void onDisable() {
        if (shouldSwapBack) {
            InvUtils.swapBack();
        }
    }

    @EventHandler
    private void onClientTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        FindItemResult result = InvUtils.findInHotbar(Items.EXPERIENCE_BOTTLE);
        if (!result.found()) return;

        RotationManager.INSTANCE.applyRotation(new Vector2f(mc.player.getYRot(), 90), 10, Priority.High, _ -> {
            InvUtils.swap(result.slot(), true);

            InteractionHand hand = result.getHand();
            mc.gameMode.useItem(mc.player, hand);
            if (swingHand.getValue()) {
                mc.player.swing(hand);
            } else {
                mc.getConnection().send(new ServerboundSwingPacket(hand));
            }

            if (switchMode.is(SwitchMode.Silent)) {
                InvUtils.swapBack();
            } else {
                shouldSwapBack = true;
            }
        });
    }

}
