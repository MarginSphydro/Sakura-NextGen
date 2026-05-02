package dev.sakura.neoforge.modules;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;

public class NeoModuleTest extends Module {

    public static final NeoModuleTest INSTANCE = new NeoModuleTest();

    private NeoModuleTest() {
        super("Neo Module Test", Category.COMBAT);
    }

}
