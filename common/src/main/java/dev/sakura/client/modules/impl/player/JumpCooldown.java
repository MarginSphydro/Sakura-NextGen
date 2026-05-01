package dev.sakura.client.modules.impl.player;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.IntSetting;

public class JumpCooldown extends Module {

    public static final JumpCooldown INSTANCE = new JumpCooldown();

    private JumpCooldown() {
        super("Jump Cooldown", Category.PLAYER);
    }

    public final IntSetting cooldown = intSetting("Cooldown", 0, 0, 9, 1);

}
