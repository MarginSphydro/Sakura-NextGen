package dev.sakura.client.gui.panel.adapter;

import dev.sakura.client.gui.panel.component.SettingRow;
import dev.sakura.client.gui.panel.component.setting.*;
import dev.sakura.client.settings.Setting;
import dev.sakura.client.settings.impl.*;

public class SettingViewFactory {

    private SettingViewFactory() {
    }

    public static SettingRow<?> create(Setting<?> setting) {
        return switch (setting) {
            case KeybindSetting keybindSetting -> new KeybindSettingRow(keybindSetting);
            case BoolSetting boolSetting -> new BoolSettingRow(boolSetting);
            case EnumSetting<?> enumSetting -> new EnumSettingRow(enumSetting);
            case IntSetting intSetting -> new IntSettingRow(intSetting);
            case DoubleSetting doubleSetting -> new DoubleSettingRow(doubleSetting);
            case ColorSetting colorSetting -> new ColorSettingRow(colorSetting);
            case StringSetting stringSetting -> new StringSettingRow(stringSetting);
            case ButtonSetting buttonSetting -> new ButtonSettingRow(buttonSetting);
            case null, default -> null;
        };
    }

}
