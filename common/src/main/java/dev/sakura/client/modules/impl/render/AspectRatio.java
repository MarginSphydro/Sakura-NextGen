package dev.sakura.client.modules.impl.render;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.DoubleSetting;

public class AspectRatio extends Module {

    public static final AspectRatio INSTANCE = new AspectRatio();

    private AspectRatio() {
        super("AspectRatio", Category.RENDER);
    }

    public final DoubleSetting ratio = doubleSetting("Ratio", 1.78, 0.01, 5.0, 0.01);

}
