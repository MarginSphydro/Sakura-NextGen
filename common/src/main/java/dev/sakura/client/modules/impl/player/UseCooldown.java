package dev.sakura.client.modules.impl.player;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.IntSetting;

public class UseCooldown extends Module {

    public static final UseCooldown INSTANCE = new UseCooldown();

    private UseCooldown() {
        super("Use Cooldown", Category.PLAYER);
    }

    public final IntSetting cooldown = intSetting("Cooldown", 0, 0, 4, 1);

}
