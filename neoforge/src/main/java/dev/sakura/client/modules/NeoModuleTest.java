package dev.sakura.client.modules;

public class NeoModuleTest extends Module {

    public static final NeoModuleTest INSTANCE = new NeoModuleTest();

    private NeoModuleTest() {
        super("Neo Module Test", Category.COMBAT);
    }

}
