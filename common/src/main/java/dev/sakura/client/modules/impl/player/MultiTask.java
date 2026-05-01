package dev.sakura.client.modules.impl.player;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;

public class MultiTask extends Module {

    public static final MultiTask INSTANCE = new MultiTask();

    private MultiTask() {
        super("Multi Task", Category.PLAYER);
    }

}
