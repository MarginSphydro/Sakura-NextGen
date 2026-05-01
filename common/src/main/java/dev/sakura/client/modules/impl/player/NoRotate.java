package dev.sakura.client.modules.impl.player;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;

public class NoRotate extends Module {

    public static final NoRotate INSTANCE = new NoRotate();

    private NoRotate() {
        super("No Rotate", Category.PLAYER);
    }

}
