package dev.sakura.client.gui.panel.adapter;

import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;

public record ModuleViewModel(Module module, String displayName, String description, boolean enabled, Category category,
                              String searchText) {
    public static ModuleViewModel from(Module module) {
        String displayName = module.getTranslatedName();
        String description = module.getName();
        String categoryName = module.getCategory().getName();
        String searchText = (displayName + " " + description + " " + categoryName).toLowerCase();
        return new ModuleViewModel(module, displayName, description, module.isEnabled(), module.getCategory(), searchText);
    }
}
