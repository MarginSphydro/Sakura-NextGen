package dev.sakura.client.modules.impl.player;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.IntSetting;

public class BreakCooldown extends Module {

    public static final BreakCooldown INSTANCE = new BreakCooldown();

    private BreakCooldown() {
        super("Break Cooldown", Category.PLAYER);
    }

    public final IntSetting cooldown = intSetting("Cooldown", 0, 0, 5, 1);

}
