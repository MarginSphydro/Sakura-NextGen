package dev.sakura.client.modules.impl.render;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;

public class PopChams extends Module {

    public static final PopChams INSTANCE = new PopChams();

    private PopChams() {
        super("Pop Chams", Category.RENDER);
    }

}
