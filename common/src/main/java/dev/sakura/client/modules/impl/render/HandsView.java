package dev.sakura.client.modules.impl.render;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.BoolSetting;
import dev.sakura.client.settings.impl.EnumSetting;
import dev.sakura.client.settings.impl.IntSetting;

public class HandsView extends Module {

    public static final HandsView INSTANCE = new HandsView();

    private HandsView() {
        super("Hands View", Category.RENDER);
    }

    public enum SwingMode {
        Vanilla,
        Flux
    }

    public final EnumSetting<SwingMode> swingMode = enumSetting("Swing Mode", SwingMode.Vanilla);
    public final BoolSetting onlyWeapon = boolSetting("Only Weapon", true, () -> swingMode.is(SwingMode.Flux));

    public final IntSetting swingSpeed = intSetting("Swing Speed", 6, 0, 20, 1);

    public final BoolSetting swingWhileUsing = boolSetting("Visual Swing On Use", true);
    public final BoolSetting onlyOnBlock = boolSetting("Only On Block", true, swingWhileUsing::getValue);

}
