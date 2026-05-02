package dev.sakura.client.modules.impl.render;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.DoubleSetting;

public class AspectRatio extends Module {

    public static final AspectRatio INSTANCE = new AspectRatio();

    private AspectRatio() {
        super("Aspect Ratio", Category.RENDER);
    }

    public final DoubleSetting ratio = doubleSetting("Ratio", 1.78, 0.1, 8.0, 0.1);

}
