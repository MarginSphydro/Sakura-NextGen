package dev.sakura.client.modules.impl.player;

import dev.sakura.client.events.bus.EventHandler;
import dev.sakura.client.events.impl.TickEvent;
import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.BoolSetting;
import dev.sakura.client.settings.impl.DoubleSetting;
import dev.sakura.client.settings.impl.EnumSetting;

public class AutoSprint extends Module {

    public static final AutoSprint INSTANCE = new AutoSprint();

    private AutoSprint() {
        super("Auto Sprint", Category.PLAYER);
    }

    private enum Mode {
        Legit,
        Smart
    }

    private final EnumSetting<Mode> mode = enumSetting("Mode", Mode.Legit);

    private final BoolSetting stopWhileUsing = boolSetting("Stop While Using", true, () -> mode.is(Mode.Smart));

    public final BoolSetting keepSprint = boolSetting("Keep Sprint", false);
    public final DoubleSetting motion = doubleSetting("Motion", 1.0, 0.0, 1.0, 0.1, keepSprint::getValue);

    @Override
    protected void onDisable() {
        if (mode.is(Mode.Legit)) {
            mc.options.keySprint.setDown(false);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (mode.is(Mode.Legit)) {
            mc.options.keySprint.setDown(true);
            mc.options.toggleSprint().set(false);
        } else {
            mc.player.setSprinting(
                    mc.player.input.hasForwardImpulse()
                            && mc.player.getFoodData().getFoodLevel() > 6
                            && !mc.player.horizontalCollision
                            && (!mc.player.isUsingItem() || !stopWhileUsing.getValue())
            );
        }
    }

}