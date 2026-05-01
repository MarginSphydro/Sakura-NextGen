package dev.sakura.client.settings.impl;

import dev.sakura.client.settings.Setting;

public class KeybindSetting extends Setting<Integer> {

    public KeybindSetting(String name, int defaultValue, Dependency dependency) {
        super(name, dependency, null);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

}


