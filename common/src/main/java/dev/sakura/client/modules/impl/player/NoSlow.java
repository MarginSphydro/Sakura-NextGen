package dev.sakura.client.modules.impl.player;

import dev.sakura.client.events.bus.EventHandler;
import dev.sakura.client.events.impl.KeyboardInputEvent;
import dev.sakura.client.events.impl.SlowdownEvent;
import dev.sakura.client.events.impl.TickEvent;
import dev.sakura.client.managers.network.ClientboundPacketManager;
import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.BoolSetting;
import dev.sakura.client.settings.impl.EnumSetting;
import dev.sakura.client.utils.network.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.item.Items;

public class NoSlow extends Module {

    public static final NoSlow INSTANCE = new NoSlow();

    private NoSlow() {
        super("No Slow", Category.PLAYER);
    }

    private enum Mode {
        Vanilla,
        Jump,
        GrimFull,
        Grim1_2,
        Grim1_3
    }

    private enum State {
        Idle,
        Pending
    }

    private final EnumSetting<Mode> mode = enumSetting("Mode", Mode.Vanilla);
    private final BoolSetting food = boolSetting("Food", true);
    private final BoolSetting bow = boolSetting("Bow", true);
    private final BoolSetting crossbow = boolSetting("Crossbow", true);

    private int onGroundTick = 0;
    private State state = State.Idle;

    @Override
    protected void onEnable() {
        onGroundTick = 0;
        state = State.Idle;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (!mc.player.isUsingItem() && state != State.Idle) {
            state = State.Idle;
            ClientboundPacketManager.INSTANCE.flush();
            ClientboundPacketManager.INSTANCE.stopTracking();
        }
    }

    @EventHandler
    private void onSlowdown(SlowdownEvent event) {
        if (nullCheck()) return;

        if (mc.player.onGround()) {
            onGroundTick++;
        } else {
            onGroundTick = 0;
        }

        if (!food.getValue() && mc.player.getUseItem().has(DataComponents.FOOD)) return;
        if (!bow.getValue() && mc.player.getUseItem().is(Items.BOW)) return;
        if (!crossbow.getValue() && mc.player.getUseItem().is(Items.CROSSBOW)) return;

        switch (mode.getValue()) {
            case Vanilla -> cancel(event);
            case Jump -> jump(event);
            case GrimFull -> grim(event);
            case Grim1_2 -> grim50(event);
            case Grim1_3 -> grim33(event);
        }
    }

    @EventHandler
    private void onKeyboardInput(KeyboardInputEvent event) {
        if (mode.is(Mode.Jump) && mc.player.onGround() && mc.player.isUsingItem() && (event.getForward() != 0 || event.getStrafe() != 0)) {
            event.setJump(true);
        }
    }

    private void cancel(SlowdownEvent event) {
        event.setSlowdown(false);
    }

    private void jump(SlowdownEvent event) {
        if (onGroundTick == 1 && mc.player.getUseItemRemainingTicks() <= 30) {
            event.setSlowdown(false);
        }
    }

    private void grim50(SlowdownEvent event) {
        if (mc.player.getUseItemRemainingTicks() % 2 == 0 && mc.player.getUseItemRemainingTicks() <= 30) {
            event.setSlowdown(false);
        }
    }

    private void grim33(SlowdownEvent event) {
        if (mc.player.getUseItemRemainingTicks() % 3 == 0 && mc.player.getUseItemRemainingTicks() <= 30) {
            event.setSlowdown(false);
        }
    }

    private void grim(SlowdownEvent event) {
        event.setSlowdown(false);

        if (state == State.Idle) {
            state = State.Pending;
            ClientboundPacketManager.INSTANCE.startTracking();
            PacketUtils.sendSilently(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
        }
    }

}
