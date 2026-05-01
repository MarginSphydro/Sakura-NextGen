package dev.sakura.client.modules.impl.player;

import dev.sakura.client.events.bus.EventHandler;
import dev.sakura.client.events.impl.KeyboardInputEvent;
import dev.sakura.client.events.impl.SendPositionEvent;
import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.DoubleSetting;
import dev.sakura.client.settings.impl.EnumSetting;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public class NoFall extends Module {

    public static final NoFall INSTANCE = new NoFall();

    private NoFall() {
        super("NoFall", Category.PLAYER);
    }

    private enum Mode {
        GroundSpoof,
        Packet,
        GrimMotion
    }

    private final EnumSetting<Mode> mode = enumSetting("Mode", Mode.GroundSpoof);
    private final DoubleSetting fallDistance = doubleSetting("Fall Distance", 3, 3, 16, 1);

    private boolean flag;
    private boolean jump;

    @EventHandler
    private void onMotion(SendPositionEvent event) {
        if (nullCheck()) return;

        if (mc.player.fallDistance > fallDistance.getValue()) {
            flag = true;
        }

        if (flag && mc.player.onGround()) {
            switch (mode.getValue()) {
                case GroundSpoof -> event.setOnGround(false);
                case Packet -> mc.getConnection().send(new ServerboundMovePlayerPacket.StatusOnly(false, false));
                case GrimMotion -> {
                    event.setY(event.getY() + 0.1);
                    jump = true;
                }
            }
            flag = false;
        }
    }

    @EventHandler
    private void onMovementInputEvent(KeyboardInputEvent event) {
        if (jump) {
            event.setJump(true);
            jump = false;
        }
    }

}
