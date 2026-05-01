package dev.sakura.client;

import dev.sakura.client.assets.i18n.I18NFileGenerator;
import dev.sakura.client.events.bus.EventBus;
import dev.sakura.client.managers.AddonManager;
import dev.sakura.client.managers.ConfigManager;
import dev.sakura.client.managers.ModuleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class SakuraClient {

    public static final String MODID = BuildConfig.MODID;
    public static final String VERSION = BuildConfig.VERSION;

    public static final Logger LOGGER = LogManager.getLogger("Sakura");

    public static int skipTicks;

    public static void init() {

        LOGGER.info("Welcome to Sakura, Meow~");

        EventBus.INSTANCE.registerLambdaFactory(SakuraClient.class.getPackageName(), (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        // 初始化 Managers
        ModuleManager.INSTANCE.initModules();
        AddonManager.INSTANCE.setupAddons();
        ConfigManager.INSTANCE.initConfig();

        // 生成空的 i18n 文件
        I18NFileGenerator.generate("Sakura/empty-i18n.json");

        // 添加一个退出游戏时候的钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConfigManager.INSTANCE.saveNow();
            SakuraClient.LOGGER.info("お兄ちゃん、私はあなたを一番愛しています~");
        }));

        SakuraClient.LOGGER.info("Sakura has loaded successfully, Meow~");

    }

}
