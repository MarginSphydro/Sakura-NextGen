package dev.sakura.client.modules.impl;

import com.mojang.blaze3d.platform.IconSet;
import dev.sakura.client.gui.hudeditor.HudEditorScreen;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.*;
import net.minecraft.SharedConstants;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.IOException;

public class ClientSetting extends Module {

    public static final ClientSetting INSTANCE = new ClientSetting();

    private ClientSetting() {
        super("Client Setting", null);
    }

    public enum ThemePreset {
        TonalSpot,
        Neutral,
        Vibrant,
        Expressive,
        Fidelity,
        Content,
        Rainbow,
        FruitSalad,
        Monochrome
    }

    public enum ThemeMode {
        Dark,
        Light
    }

    public final KeybindSetting guiKeybind = keybindSetting("Gui Keybind", GLFW.GLFW_KEY_RIGHT_SHIFT);

    private final ButtonSetting openHudEditor = buttonSetting("Open Hud Editor", () -> mc.setScreen(HudEditorScreen.INSTANCE));

    public final BoolSetting i18nFallback = boolSetting("I18n Fallback", true);

    public final BoolSetting fontAntiAliasing = boolSetting("Font Anti Aliasing", true);

    public final BoolSetting closeOnOutside = boolSetting("Close Gui On Outside", false);

    public final EnumSetting<ThemeMode> themeMode = enumSetting("Theme Mode", ThemeMode.Dark);

    public final EnumSetting<ThemePreset> themePreset = enumSetting("Theme Preset", ThemePreset.Expressive);

    private final BoolSetting customIcon = boolSetting("Custom Icon", true, _ -> {
        try {
            mc.getWindow().setIcon(mc.getVanillaPackResources(), SharedConstants.getCurrentVersion().stable() ? IconSet.RELEASE : IconSet.SNAPSHOT);
        } catch (IOException ignored) {
        }
    });

    private final BoolSetting customTitle = boolSetting("Custom Title", true, _ -> mc.updateTitle());

    private final BoolSetting useMainMenu = boolSetting("Use MainMenu", true);

    private final BoolSetting soundNotify = boolSetting("Sound Notify", true);

    private final BoolSetting chatNotify = boolSetting("Chat Notify", true);

    private final BoolSetting animatedChatPrefix = boolSetting("Animated Chat Prefix", true);

    private final ColorSetting chatPrefixColorStart = colorSetting("Chat Prefix Color Start", new Color(255, 175, 210), animatedChatPrefix::getValue);

    private final ColorSetting chatPrefixColorEnd = colorSetting("Chat Prefix Color End", new Color(150, 220, 255), animatedChatPrefix::getValue);

    private final DoubleSetting chatPrefixGradientSpeed = doubleSetting("Chat Prefix Gradient Speed", 0.5, 0.1, 1, 0.1, animatedChatPrefix::getValue);

    public ThemePreset getThemePreset() {
        return themePreset.getValue();
    }

    public ThemeMode getThemeMode() {
        return themeMode.getValue();
    }

    public boolean shouldUseMainMenu() {
        return useMainMenu.getValue();
    }

    public boolean customIcon() {
        return customIcon.getValue();
    }

    public boolean customTitle() {
        return customTitle.getValue();
    }

    public boolean shouldSoundNotify() {
        return soundNotify.getValue();
    }

    public boolean shouldChatNotify() {
        return chatNotify.getValue();
    }

    public boolean shouldAnimateChatPrefix() {
        return animatedChatPrefix.getValue();
    }

    public Color getChatPrefixColorStart() {
        return chatPrefixColorStart.getValue();
    }

    public Color getChatPrefixColorEnd() {
        return chatPrefixColorEnd.getValue();
    }

    public double getChatPrefixGradientSpeed() {
        return chatPrefixGradientSpeed.getValue();
    }

}
