package dev.sakura.client.settings.impl;

import dev.sakura.client.settings.Setting;

import java.util.function.Consumer;

public class BoolSetting extends Setting<Boolean> {

    public BoolSetting(String name, boolean defaultValue, Dependency dependency, Consumer<Boolean> onChanged) {
        super(name, dependency, onChanged);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

}