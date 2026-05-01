package dev.sakura.client.modules.impl.player;

import dev.sakura.client.events.bus.EventHandler;
import dev.sakura.client.events.impl.ClickEvent;
import dev.sakura.client.events.impl.KeyboardInputEvent;
import dev.sakura.client.events.impl.PacketEvent;
import dev.sakura.client.events.impl.TravelEvent;
import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.EnumSetting;
import dev.sakura.client.utils.network.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public class Stuck extends Module {

    public static final Stuck INSTANCE = new Stuck();

    private final EnumSetting<Mode> mode = enumSetting("Mode", Mode.NoPacket);

    private Stuck() {
        super("Stuck", Category.PLAYER);
    }

    private float lastYaw;
    private float lastPitch;

    private enum Mode {
        NoPacket,
        CancelMove
    }

    @Override
    protected void onDisable() {
        if (mode.is(Mode.NoPacket)) {
            if (mc.player != null && !mc.player.onGround()) {
                PacketUtils.sendSilently(new ServerboundMovePlayerPacket.PosRot(mc.player.getX() + 1337, mc.player.getY(), mc.player.getZ() + 1337, mc.player.getYRot() + 0.01f, mc.player.getXRot(), mc.player.onGround(), mc.player.horizontalCollision));
            }
        }
    }

    @EventHandler
    private void onKeyboardInput(KeyboardInputEvent event) {
        event.setForward(0);
        event.setStrafe(0);
    }

    @EventHandler
    private void onPacket(PacketEvent.Send e) {
        if (mode.is(Mode.NoPacket)) {
            if (e.getPacket() instanceof ServerboundMovePlayerPacket || (e.getPacket() instanceof ClientboundSetEntityMotionPacket setEntityMotionPacket && setEntityMotionPacket.id() == mc.player.getId())) {
                e.setCancelled(true);
            }
        }
        if (e.getPacket() instanceof ClientboundPlayerPositionPacket) {
            toggle();
        }
    }

    @EventHandler
    private void onTravel(TravelEvent event) {
        if (mode.is(Mode.CancelMove)) {
            if (mc.player.positionReminder < 19) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onInteract(ClickEvent event) {
        if (mode.is(Mode.NoPacket)) {
            if (mc.player.getYRot() != lastYaw || mc.player.getXRot() != lastPitch) {
                PacketUtils.sendSilently(new ServerboundMovePlayerPacket.Rot(mc.player.getYRot(), mc.player.getXRot(), mc.player.onGround(), mc.player.horizontalCollision));
            }
            lastPitch = mc.player.getXRot();
            lastYaw = mc.player.getYRot();
        }
    }

}
