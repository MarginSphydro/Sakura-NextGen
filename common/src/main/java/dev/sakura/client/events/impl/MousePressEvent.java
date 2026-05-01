package dev.sakura.client.events.impl;

import dev.sakura.client.events.Cancellable;

public class MousePressEvent extends Cancellable {

    private final int button;
    private final int action;
    private final int modifiers;

    public MousePressEvent(int button, int action, int modifiers) {
        this.button = button;
        this.action = action;
        this.modifiers = modifiers;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return action;
    }

    public int getModifiers() {
        return modifiers;
    }

}
