package dev.sakura.client.modules;

import dev.sakura.client.assets.i18n.SakuraTranslateComponent;
import dev.sakura.client.assets.i18n.TranslateComponent;

public enum Category {

    COMBAT("b", "combat"),
    PLAYER("5", "player"),
    RENDER("a", "render"),
    HUD("E", "hud");

    public final String icon;
    private final String name;
    private final TranslateComponent translateComponent;

    Category(String icon, String name) {
        this.icon = icon;
        this.name = name;
        translateComponent = SakuraTranslateComponent.create("categories", name);
    }

    public String getName() {
        return translateComponent.getTranslatedName();
    }

    @Override
    public String toString() {
        return name;
    }

}
